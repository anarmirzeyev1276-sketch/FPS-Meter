package com.realfps.meter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import android.view.Choreographer
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import java.util.concurrent.TimeUnit

class FpsService : Service() {

    companion object {
        @Volatile var running = false
        @Volatile var realOk = false
        const val EXTRA_PKG = "pkg"
        private const val CHANNEL = "fps_meter"
    }

    private val main = Handler(Looper.getMainLooper())
    private lateinit var worker: HandlerThread
    private lateinit var work: Handler
    @Volatile private var alive = false
    @Volatile private var target: String? = null

    // ---------- overlay ----------
    private lateinit var wm: WindowManager
    private lateinit var lp: WindowManager.LayoutParams
    private var box: LinearLayout? = null
    private var numView: TextView? = null
    private var unitView: TextView? = null

    // ---------- limited mode (display refresh rate) ----------
    @Volatile private var frames = 0
    private var hzLast = 0L
    private val frameCb = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            frames++
            if (box != null) Choreographer.getInstance().postFrameCallback(this)
        }
    }

    // ---------- real FPS (SurfaceFlinger) ----------
    private var method = 0          // 0 = unknown, 1 = direct (DUMP), 2 = root, 3 = unavailable
    private var rootTried = false
    private var probeAt = 0L
    private var layer: String? = null
    private var layerFor: String? = null
    private var layerAt = 0L

    // ---------- foreground app detection ----------
    private var evFrom = 0L
    private var top: String? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        running = true
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        worker = HandlerThread("fps-worker").also { it.start() }
        work = Handler(worker.looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val lang = getSharedPreferences("fps", MODE_PRIVATE).getString("lang", null) ?: S.systemLang()
        startAsForeground(lang)
        val p = intent?.getStringExtra(EXTRA_PKG)
        if (p == null) {
            stopSelf()
            return START_NOT_STICKY
        }
        target = p
        if (!alive) {
            alive = true
            work.post(tick)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        alive = false
        running = false
        work.removeCallbacksAndMessages(null)
        worker.quitSafely()
        hide()
        super.onDestroy()
    }

    private fun startAsForeground(lang: String) {
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(
            NotificationChannel(CHANNEL, "Real FPS Meter", NotificationManager.IMPORTANCE_LOW)
        )
        val pi = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )
        val n = Notification.Builder(this, CHANNEL)
            .setContentTitle("Real FPS Meter")
            .setContentText(S.t(lang, 9))
            .setSmallIcon(android.R.drawable.ic_menu_view)
            .setContentIntent(pi)
            .setOngoing(true)
            .build()
        if (Build.VERSION.SDK_INT >= 34) {
            startForeground(1, n, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(1, n)
        }
    }

    // =====================================================================
    //  Main loop (1 saniyədə bir dəfə, arxa thread-də)
    // =====================================================================
    private val tick = object : Runnable {
        override fun run() {
            if (!alive) return
            try {
                val pkg = target
                if (pkg != null && foreground() == pkg) {
                    val real = realFps(pkg)
                    if (real != null) {
                        val v = if (real < 0f) -1 else Math.round(real)
                        main.post { render(v, true) }
                    } else {
                        val v = hz()
                        main.post { render(v, false) }
                    }
                } else {
                    hzLast = 0L
                    main.post { hide() }
                }
            } catch (_: Throwable) {
            }
            if (alive) work.postDelayed(this, 1000)
        }
    }

    // =====================================================================
    //  Qabaqda olan proqramı tapmaq (Usage Access)
    // =====================================================================
    private fun foreground(): String? {
        val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val now = System.currentTimeMillis()
        if (evFrom == 0L) evFrom = now - 2 * 60 * 60 * 1000L
        val ev = usm.queryEvents(evFrom, now + 1000)
        val e = UsageEvents.Event()
        while (ev.hasNextEvent()) {
            ev.getNextEvent(e)
            if (e.eventType == 1) top = e.packageName   // ACTIVITY_RESUMED
        }
        evFrom = now - 1500
        return top
    }

    // =====================================================================
    //  Real FPS: dumpsys SurfaceFlinger --latency
    //  null = real FPS mümkün deyil, mənfi = layer hələ tapılmayıb
    // =====================================================================
    private fun realFps(pkg: String): Float? {
        val now = SystemClock.elapsedRealtime()
        if (method == 0 || (method == 3 && now - probeAt > 30_000)) {
            probe()
            probeAt = now
        }
        if (method == 3) return null

        if (layerFor != pkg) {
            layerFor = pkg
            layer = null
        }
        var l = layer
        if (l == null || now - layerAt > 8_000) {
            l = pickLayer(pkg)
            layer = l
            layerAt = now
        }
        if (l == null) return -1f

        var f = latency(l) ?: return null
        if (f < 1f && now - layerAt > 3_000) {
            layerAt = now
            val n = pickLayer(pkg)
            if (n != null) {
                layer = n
                f = latency(n) ?: f
            }
        }
        return f
    }

    private fun probe() {
        var out = exec(listOf("dumpsys", "SurfaceFlinger", "--list"), false)
        if (out != null && out.contains('#')) {
            method = 1; realOk = true; return
        }
        if (!rootTried) {
            rootTried = true
            out = exec(listOf("dumpsys", "SurfaceFlinger", "--list"), true)
            if (out != null && out.contains('#')) {
                method = 2; realOk = true; return
            }
        }
        method = 3
        realOk = false
    }

    private fun pickLayer(pkg: String): String? {
        val out = exec(listOf("dumpsys", "SurfaceFlinger", "--list")) ?: return null
        val cands = out.lines().map { it.trim() }
            .filter {
                it.contains(pkg) && !it.startsWith("Background for") &&
                    !it.startsWith("Bounds for") && !it.startsWith("Task=")
            }
            .distinct().take(6)
        var best: String? = null
        var bestF = -1f
        for (c in cands) {
            val f = latency(c) ?: continue
            val score = f + (if (c.startsWith("SurfaceView")) 0.01f else 0f)
            if (score > bestF) {
                best = c
                bestF = score
            }
        }
        return best
    }

    /** Son 1 saniyədə ekrana çıxan kadrların sayı (SurfaceFlinger actualPresentTime). */
    private fun latency(layerName: String): Float? {
        val out = exec(listOf("dumpsys", "SurfaceFlinger", "--latency", layerName)) ?: return null
        val lines = out.lines()
        if (lines.isEmpty() || lines[0].trim().toLongOrNull() == null) return null
        val ts = ArrayList<Long>()
        for (i in 1 until lines.size) {
            val p = lines[i].trim().split(Regex("\\s+"))
            if (p.size < 3) continue
            val t = p[1].toLongOrNull() ?: continue
            if (t <= 0L || t == Long.MAX_VALUE) continue
            ts.add(t)
        }
        if (ts.isEmpty()) return 0f
        ts.sort()
        val now = System.nanoTime()
        val win = ts.filter { it > now - 1_000_000_000L && it <= now + 100_000_000L }
        if (win.size < 2) return win.size.toFloat()
        val span = win.last() - win.first()
        if (span <= 0L) return 0f
        return minOf(1000f, (win.size - 1) * 1_000_000_000f / span)
    }

    private fun exec(a: List<String>, root: Boolean = method == 2): String? {
        return try {
            val cmd = if (root) {
                listOf("su", "-c", a.joinToString(" ") { "'" + it.replace("'", "'\\''") + "'" })
            } else a
            val p = ProcessBuilder(cmd).redirectErrorStream(true).start()
            val txt = p.inputStream.bufferedReader().readText()
            if (!p.waitFor(3, TimeUnit.SECONDS)) p.destroy()
            txt
        } catch (e: Throwable) {
            null
        }
    }

    // =====================================================================
    //  Məhdud rejim: ekranın yenilənmə tezliyi (Choreographer)
    // =====================================================================
    private fun hz(): Int {
        val now = SystemClock.elapsedRealtimeNanos()
        val f = frames
        frames = 0
        val last = hzLast
        hzLast = now
        if (last == 0L || f == 0) return -1
        return Math.round(f * 1e9f / (now - last))
    }

    // =====================================================================
    //  Overlay
    // =====================================================================
    private fun render(v: Int, real: Boolean) {
        if (!alive) return
        if (box == null) buildOverlay()
        numView?.text = if (v < 0) "--" else v.toString()
        unitView?.text = if (real) "FPS" else "Hz"
        numView?.setTextColor(
            when {
                !real || v < 0 || v >= 50 -> Color.parseColor("#00E676")
                v >= 25 -> Color.parseColor("#FFC107")
                else -> Color.parseColor("#FF5252")
            }
        )
    }

    private fun buildOverlay() {
        val d = resources.displayMetrics.density
        val prefs = getSharedPreferences("fps", MODE_PRIVATE)

        val num = TextView(this).apply {
            text = "--"
            textSize = 22f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            setTextColor(Color.parseColor("#00E676"))
            minWidth = (34 * d).toInt()
            gravity = Gravity.END
        }
        val unit = TextView(this).apply {
            text = "FPS"
            textSize = 10f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#6FA874"))
            setPadding((5 * d).toInt(), 0, 0, 0)
        }
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_LTR
            setPadding((12 * d).toInt(), (6 * d).toInt(), (12 * d).toInt(), (6 * d).toInt())
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#E6030603"))
                cornerRadius = 12 * d
                setStroke((1.5f * d).toInt(), Color.parseColor("#00E676"))
            }
            addView(num)
            addView(unit)
        }

        lp = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = prefs.getInt("ox", (12 * d).toInt())
            y = prefs.getInt("oy", (48 * d).toInt())
        }

        // sürüşdürmək olur, mövqe yadda qalır
        row.setOnTouchListener(object : View.OnTouchListener {
            var sx = 0
            var sy = 0
            var tx = 0f
            var ty = 0f
            override fun onTouch(v: View, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sx = lp.x; sy = lp.y; tx = e.rawX; ty = e.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        lp.x = sx + (e.rawX - tx).toInt()
                        lp.y = sy + (e.rawY - ty).toInt()
                        try { wm.updateViewLayout(v, lp) } catch (_: Exception) {}
                    }
                    MotionEvent.ACTION_UP -> prefs.edit().putInt("ox", lp.x).putInt("oy", lp.y).apply()
                }
                return true
            }
        })

        try {
            wm.addView(row, lp)
            box = row
            numView = num
            unitView = unit
            Choreographer.getInstance().removeFrameCallback(frameCb)
            Choreographer.getInstance().postFrameCallback(frameCb)
        } catch (e: Exception) {
            // overlay icazəsi alınıb
            stopSelf()
        }
    }

    private fun hide() {
        box?.let {
            try { wm.removeView(it) } catch (_: Exception) {}
        }
        box = null
        numView = null
        unitView = null
    }
}
