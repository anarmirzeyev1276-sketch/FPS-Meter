package com.realfps.meter

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.Window
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

private val BG = Color.parseColor("#000000")
private val CARD = Color.parseColor("#0A110A")
private val FIELD = Color.parseColor("#101A10")
private val LINE = Color.parseColor("#1B2E1D")
private val GREEN = Color.parseColor("#00E676")
private val GREEN_D = Color.parseColor("#00A152")
private val TEXT = Color.parseColor("#EAF5EB")
private val DIM = Color.parseColor("#7C9A80")
private val AMBER = Color.parseColor("#FFC107")

private class AppItem(val label: String, val pkg: String)

class MainActivity : Activity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var root: ScrollView
    private var lang = "en"
    private val ui = Handler(Looper.getMainLooper())
    private val cmd = "adb shell pm grant com.realfps.meter android.permission.DUMP"

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        prefs = getSharedPreferences("fps", MODE_PRIVATE)
        lang = prefs.getString("lang", null) ?: S.systemLang()
        window.statusBarColor = BG
        window.navigationBarColor = BG
        root = ScrollView(this).apply {
            setBackgroundColor(BG)
            isFillViewport = true
        }
        setContentView(root)
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 7)
        }
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    // =====================================================================
    //  Helpers
    // =====================================================================
    private fun t(i: Int) = S.t(lang, i)

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    private fun dir() = if (S.isRtl(lang)) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

    private fun rr(fill: Int, radius: Int, stroke: Int = 0, strokeColor: Int = 0): GradientDrawable =
        GradientDrawable().apply {
            setColor(fill)
            cornerRadius = dp(radius).toFloat()
            if (stroke > 0) setStroke(dp(stroke), strokeColor)
        }

    private fun LinearLayout.put(v: View, topDp: Int = 0) {
        addView(v, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(topDp) })
    }

    private fun txt(s: CharSequence, size: Float, color: Int, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = s
            textSize = size
            setTextColor(color)
            if (bold) typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            setLineSpacing(0f, 1.15f)
        }

    private fun card(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        background = rr(CARD, 16, 1, LINE)
        setPadding(dp(16), dp(16), dp(16), dp(16))
    }

    private fun button(label: String, filled: Boolean, onClick: () -> Unit): TextView =
        TextView(this).apply {
            text = label
            gravity = Gravity.CENTER
            textSize = 16f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            setTextColor(if (filled) Color.BLACK else GREEN)
            background = if (filled) rr(GREEN, 14) else rr(Color.TRANSPARENT, 14, 1, GREEN_D)
            setPadding(dp(16), dp(15), dp(16), dp(15))
            setOnClickListener { onClick() }
        }

    @Suppress("DEPRECATION")
    private fun hasUsage(): Boolean {
        val ops = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val m = ops.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        return m == AppOpsManager.MODE_ALLOWED
    }

    private fun startFps(pkg: String) {
        startForegroundService(Intent(this, FpsService::class.java).putExtra(FpsService.EXTRA_PKG, pkg))
    }

    // =====================================================================
    //  Screen
    // =====================================================================
    private fun render() {
        root.layoutDirection = dir()
        val col = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(28), dp(18), dp(28))
        }
        col.put(header())
        if (!Settings.canDrawOverlays(this)) {
            col.put(permissionCard(), 28)
        } else {
            col.put(appCard(), 24)
            col.put(statusCard(), 12)
            if (!hasUsage()) col.put(usageCard(), 12)
            col.put(startButton(), 20)
            if (FpsService.running) {
                col.put(txt(t(9), 14f, GREEN).apply { gravity = Gravity.CENTER }, 14)
            }
        }
        root.removeAllViews()
        root.addView(col)
    }

    private fun header(): View {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val logo = ImageView(this).apply {
            setImageResource(R.drawable.logo)
            scaleType = ImageView.ScaleType.CENTER_CROP
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(v: View, o: Outline) {
                    o.setRoundRect(0, 0, v.width, v.height, dp(14).toFloat())
                }
            }
            clipToOutline = true
        }
        row.addView(logo, LinearLayout.LayoutParams(dp(52), dp(52)))
        val title = txt("Real FPS Meter", 21f, TEXT, true).apply {
            setPadding(dp(12), 0, dp(12), 0)
        }
        row.addView(title, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        val chip = txt(lang.uppercase(), 13f, GREEN, true).apply {
            gravity = Gravity.CENTER
            background = rr(Color.TRANSPARENT, 20, 1, GREEN_D)
            setPadding(dp(14), dp(8), dp(14), dp(8))
            setOnClickListener { pickLang() }
        }
        row.addView(chip)
        return row
    }

    private fun permissionCard(): View {
        val c = card()
        c.put(txt(t(0), 18f, TEXT, true))
        c.put(txt(t(1), 14f, DIM), 8)
        c.put(button(t(2), true) {
            startActivity(
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            )
        }, 18)
        return c
    }

    private fun appCard(): View {
        val c = card()
        c.put(txt(t(4), 13f, DIM))
        val pkg = prefs.getString("pkg", null)
        var label = t(5)
        var icon: Drawable? = null
        if (pkg != null) {
            try {
                val pm = packageManager
                label = pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString()
                icon = pm.getApplicationIcon(pkg)
            } catch (e: Exception) {
                prefs.edit().remove("pkg").apply()
            }
        }
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = rr(FIELD, 12)
            setPadding(dp(12), dp(12), dp(12), dp(12))
            setOnClickListener { pickApp() }
        }
        val ic: View = if (icon != null) {
            ImageView(this).apply { setImageDrawable(icon) }
        } else {
            TextView(this).apply {
                text = "+"
                gravity = Gravity.CENTER
                textSize = 22f
                setTextColor(GREEN)
                background = rr(Color.TRANSPARENT, 12, 1, GREEN_D)
            }
        }
        row.addView(ic, LinearLayout.LayoutParams(dp(44), dp(44)))
        val name = txt(label, 17f, if (icon != null) TEXT else DIM, icon != null).apply {
            setPadding(dp(12), 0, dp(12), 0)
        }
        row.addView(name, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        row.addView(txt(if (S.isRtl(lang)) "‹" else "›", 26f, GREEN))
        c.put(row, 10)
        return c
    }

    private fun statusCard(): View {
        val real = FpsService.realOk ||
            checkSelfPermission("android.permission.DUMP") == PackageManager.PERMISSION_GRANTED
        val c = card()
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val dot = View(this).apply { background = rr(if (real) GREEN else AMBER, 5) }
        row.addView(dot, LinearLayout.LayoutParams(dp(10), dp(10)))
        row.addView(
            txt(if (real) t(10) else t(11), 14f, TEXT, true).apply { setPadding(dp(10), 0, 0, 0) }
        )
        c.put(row)
        if (!real) {
            c.put(txt(t(12), 15f, TEXT, true), 16)
            c.put(txt(t(13), 13f, DIM), 6)
            val box = txt(cmd, 12f, GREEN).apply {
                typeface = Typeface.MONOSPACE
                background = rr(Color.parseColor("#050A05"), 10, 1, LINE)
                setPadding(dp(12), dp(12), dp(12), dp(12))
                layoutDirection = View.LAYOUT_DIRECTION_LTR
                textDirection = View.TEXT_DIRECTION_LTR
                gravity = Gravity.START
                setTextIsSelectable(true)
            }
            c.put(box, 10)
            c.put(button(t(14), false) {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setPrimaryClip(ClipData.newPlainText("cmd", cmd))
                Toast.makeText(this, t(16), Toast.LENGTH_SHORT).show()
            }, 10)
        }
        return c
    }

    private fun usageCard(): View {
        val c = card()
        c.put(txt(t(3), 14f, DIM))
        c.put(button(t(2), false) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }, 14)
        return c
    }

    private fun startButton(): View {
        val running = FpsService.running
        val b = button(if (running) t(8) else t(7), !running) {
            if (FpsService.running) {
                stopService(Intent(this, FpsService::class.java))
                ui.postDelayed({ render() }, 250)
            } else {
                val pkg = prefs.getString("pkg", null)
                if (pkg == null) {
                    pickApp()
                } else if (!hasUsage()) {
                    startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                } else {
                    startFps(pkg)
                    ui.postDelayed({ render() }, 300)
                }
            }
        }
        b.textSize = 18f
        b.setPadding(dp(16), dp(18), dp(16), dp(18))
        if (running) {
            b.setTextColor(Color.parseColor("#FF6B6B"))
            b.background = rr(Color.TRANSPARENT, 14, 1, Color.parseColor("#7A2E2E"))
        }
        return b
    }

    // =====================================================================
    //  Dialogs
    // =====================================================================
    private fun sheet(): Pair<Dialog, LinearLayout> {
        val d = Dialog(this)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = rr(Color.parseColor("#070C07"), 20, 1, LINE)
            setPadding(dp(14), dp(14), dp(14), dp(14))
            layoutDirection = dir()
        }
        d.setContentView(box)
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return Pair(d, box)
    }

    private fun showSized(d: Dialog) {
        d.show()
        val m = resources.displayMetrics
        d.window?.setLayout((m.widthPixels * 0.92).toInt(), (m.heightPixels * 0.78).toInt())
    }

    private fun pickApp() {
        val (d, box) = sheet()
        val search = EditText(this).apply {
            hint = t(6)
            setHintTextColor(DIM)
            setTextColor(TEXT)
            textSize = 15f
            setSingleLine(true)
            background = rr(FIELD, 12, 1, LINE)
            setPadding(dp(14), dp(12), dp(14), dp(12))
        }
        box.addView(search, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        val list = ListView(this).apply {
            divider = null
            selector = ColorDrawable(Color.parseColor("#2200E676"))
        }
        box.addView(list, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ).apply { topMargin = dp(10) })

        val all = ArrayList<AppItem>()
        val shown = ArrayList<AppItem>()
        val icons = HashMap<String, Drawable>()

        val adapter = object : BaseAdapter() {
            override fun getCount() = shown.size
            override fun getItem(i: Int) = shown[i]
            override fun getItemId(i: Int) = i.toLong()
            override fun getView(i: Int, v: View?, p: ViewGroup?): View {
                val item = shown[i]
                val row = LinearLayout(this@MainActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    setPadding(dp(8), dp(8), dp(8), dp(8))
                }
                val iv = ImageView(this@MainActivity)
                iv.setImageDrawable(icons.getOrPut(item.pkg) {
                    try {
                        packageManager.getApplicationIcon(item.pkg)
                    } catch (e: Exception) {
                        ColorDrawable(Color.DKGRAY)
                    }
                })
                row.addView(iv, LinearLayout.LayoutParams(dp(40), dp(40)))
                val names = LinearLayout(this@MainActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(dp(12), 0, dp(12), 0)
                }
                names.addView(txt(item.label, 15f, TEXT, true))
                names.addView(txt(item.pkg, 11f, DIM))
                row.addView(names, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
                return row
            }
        }
        list.adapter = adapter

        fun filter(q: String) {
            val s = q.trim().lowercase()
            shown.clear()
            shown.addAll(all.filter { s.isEmpty() || it.label.lowercase().contains(s) || it.pkg.contains(s) })
            adapter.notifyDataSetChanged()
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable?) { filter(e.toString()) }
            override fun beforeTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {}
            override fun onTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {}
        })

        list.setOnItemClickListener { _, _, pos, _ ->
            val a = shown[pos]
            prefs.edit().putString("pkg", a.pkg).apply()
            if (FpsService.running) startFps(a.pkg)
            d.dismiss()
            render()
        }

        Thread {
            val pm = packageManager
            val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            val items = pm.queryIntentActivities(intent, 0)
                .map { AppItem(it.loadLabel(pm).toString(), it.activityInfo.packageName) }
                .filter { it.pkg != packageName }
                .distinctBy { it.pkg }
                .sortedBy { it.label.lowercase() }
            runOnUiThread {
                all.addAll(items)
                filter(search.text.toString())
            }
        }.start()

        showSized(d)
    }

    private fun pickLang() {
        val (d, box) = sheet()
        box.addView(txt(t(15), 16f, TEXT, true).apply { setPadding(dp(8), dp(4), dp(8), dp(10)) })
        val list = ListView(this).apply {
            divider = null
            selector = ColorDrawable(Color.parseColor("#2200E676"))
        }
        box.addView(list, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))
        list.adapter = object : BaseAdapter() {
            override fun getCount() = S.langs.size
            override fun getItem(i: Int) = S.langs[i]
            override fun getItemId(i: Int) = i.toLong()
            override fun getView(i: Int, v: View?, p: ViewGroup?): View {
                val (code, name) = S.langs[i]
                val on = code == lang
                return txt(name, 16f, if (on) GREEN else TEXT, on).apply {
                    setPadding(dp(12), dp(13), dp(12), dp(13))
                }
            }
        }
        list.setOnItemClickListener { _, _, pos, _ ->
            lang = S.langs[pos].first
            prefs.edit().putString("lang", lang).apply()
            d.dismiss()
            render()
        }
        showSized(d)
    }
}
