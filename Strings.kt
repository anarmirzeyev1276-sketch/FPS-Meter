package com.realfps.meter

import java.util.Locale

/**
 * 41 dil. Hər dil üçün 17 mətn, bu sıra ilə:
 * 0 permTitle, 1 permDesc, 2 grant, 3 usageDesc, 4 selectApp, 5 notSelected, 6 search,
 * 7 start, 8 stop, 9 active, 10 modeReal, 11 modeLimited, 12 setupTitle, 13 setupDesc,
 * 14 copy, 15 language, 16 copied
 */
object S {

    val langs: List<Pair<String, String>> = listOf(
        "en" to "English", "az" to "Azərbaycanca", "tr" to "Türkçe", "ru" to "Русский",
        "es" to "Español", "fr" to "Français", "de" to "Deutsch", "pt" to "Português",
        "it" to "Italiano", "ar" to "العربية", "hi" to "हिन्दी", "zh" to "中文",
        "ja" to "日本語", "ko" to "한국어", "id" to "Bahasa Indonesia", "fa" to "فارسی",
        "uk" to "Українська", "pl" to "Polski", "nl" to "Nederlands", "vi" to "Tiếng Việt",
        "th" to "ไทย", "bn" to "বাংলা", "ur" to "اردو", "sv" to "Svenska",
        "cs" to "Čeština", "ro" to "Română", "el" to "Ελληνικά", "he" to "עברית",
        "hu" to "Magyar", "da" to "Dansk", "fi" to "Suomi", "no" to "Norsk",
        "bg" to "Български", "sr" to "Srpski", "hr" to "Hrvatski", "sk" to "Slovenčina",
        "ms" to "Bahasa Melayu", "fil" to "Filipino", "ka" to "ქართული", "kk" to "Қазақша",
        "uz" to "O'zbekcha"
    )

    private val data: Map<String, Array<String>> = mapOf(
        "en" to arrayOf(
            "Overlay permission needed",
            "Allow display over other apps so the FPS box can appear on top of your game.",
            "Grant permission",
            "Allow usage access so the app can detect when your selected app opens.",
            "Select an app", "No app selected", "Search apps",
            "START", "STOP", "Active — open your selected app",
            "Real FPS: ON", "Limited mode: display refresh rate only",
            "Enable real FPS (one time)",
            "Run this command once via ADB (wireless debugging works), or use a rooted device:",
            "Copy command", "Language", "Copied"
        ),
        "az" to arrayOf(
            "Üstündə göstərmə icazəsi lazımdır",
            "FPS xanasının oyununuzun üstündə görünməsi üçün digər tətbiqlərin üzərində göstərməyə icazə verin.",
            "İcazə ver",
            "Seçdiyiniz tətbiqin nə vaxt açıldığını bilmək üçün istifadə girişinə icazə verin.",
            "Tətbiq seçin", "Tətbiq seçilməyib", "Tətbiq axtar",
            "BAŞLAT", "DAYANDIR", "Aktiv — seçdiyiniz tətbiqi açın",
            "Real FPS: AÇIQ", "Məhdud rejim: yalnız ekran yenilənmə tezliyi",
            "Real FPS-i aktiv et (bir dəfə)",
            "Bu əmri ADB ilə bir dəfə icra edin (simsiz debaq işləyir) və ya root cihaz istifadə edin:",
            "Əmri kopyala", "Dil", "Kopyalandı"
        ),
        "tr" to arrayOf(
            "Üstte gösterme izni gerekli",
            "FPS kutusunun oyununuzun üzerinde görünmesi için diğer uygulamaların üzerinde göstermeye izin verin.",
            "İzin ver",
            "Seçtiğiniz uygulamanın ne zaman açıldığını algılamak için kullanım erişimine izin verin.",
            "Uygulama seç", "Uygulama seçilmedi", "Uygulama ara",
            "BAŞLAT", "DURDUR", "Aktif — seçtiğiniz uygulamayı açın",
            "Gerçek FPS: AÇIK", "Sınırlı mod: yalnızca ekran yenileme hızı",
            "Gerçek FPS'yi etkinleştir (tek seferlik)",
            "Bu komutu ADB ile bir kez çalıştırın (kablosuz hata ayıklama olur) veya root'lu cihaz kullanın:",
            "Komutu kopyala", "Dil", "Kopyalandı"
        ),
        "ru" to arrayOf(
            "Требуется разрешение на отображение поверх других окон",
            "Разрешите показ поверх других приложений, чтобы окно FPS отображалось над игрой.",
            "Разрешить",
            "Разрешите доступ к данным об использовании, чтобы определять запуск выбранного приложения.",
            "Выберите приложение", "Приложение не выбрано", "Поиск приложений",
            "СТАРТ", "СТОП", "Активно — откройте выбранное приложение",
            "Реальный FPS: ВКЛ", "Ограниченный режим: только частота обновления экрана",
            "Включить реальный FPS (один раз)",
            "Выполните эту команду один раз через ADB (подойдёт отладка по Wi‑Fi) или используйте root-устройство:",
            "Копировать команду", "Язык", "Скопировано"
        ),
        "es" to arrayOf(
            "Se necesita permiso de superposición",
            "Permite mostrar sobre otras apps para que el cuadro de FPS aparezca sobre tu juego.",
            "Conceder permiso",
            "Permite el acceso de uso para detectar cuándo se abre la app seleccionada.",
            "Selecciona una app", "Ninguna app seleccionada", "Buscar apps",
            "INICIAR", "DETENER", "Activo: abre la app seleccionada",
            "FPS real: ACTIVADO", "Modo limitado: solo frecuencia de actualización de pantalla",
            "Activar FPS real (una vez)",
            "Ejecuta este comando una vez con ADB (sirve la depuración inalámbrica) o usa un dispositivo con root:",
            "Copiar comando", "Idioma", "Copiado"
        ),
        "fr" to arrayOf(
            "Autorisation de superposition requise",
            "Autorisez l'affichage par-dessus d'autres applis pour que la zone FPS apparaisse sur votre jeu.",
            "Autoriser",
            "Autorisez l'accès à l'utilisation pour détecter l'ouverture de l'appli sélectionnée.",
            "Choisir une appli", "Aucune appli sélectionnée", "Rechercher des applis",
            "DÉMARRER", "ARRÊTER", "Actif — ouvrez l'appli sélectionnée",
            "FPS réel : ACTIVÉ", "Mode limité : fréquence de rafraîchissement uniquement",
            "Activer le FPS réel (une fois)",
            "Exécutez cette commande une fois via ADB (le débogage sans fil convient) ou utilisez un appareil rooté :",
            "Copier la commande", "Langue", "Copié"
        ),
        "de" to arrayOf(
            "Überlagerungsberechtigung erforderlich",
            "Erlaube die Anzeige über anderen Apps, damit das FPS-Feld über deinem Spiel erscheint.",
            "Berechtigung erteilen",
            "Erlaube den Nutzungszugriff, damit erkannt wird, wann die gewählte App geöffnet wird.",
            "App auswählen", "Keine App ausgewählt", "Apps suchen",
            "START", "STOPP", "Aktiv – öffne die gewählte App",
            "Echte FPS: AN", "Eingeschränkter Modus: nur Bildwiederholrate",
            "Echte FPS aktivieren (einmalig)",
            "Führe diesen Befehl einmal per ADB aus (drahtloses Debugging geht) oder nutze ein Gerät mit Root:",
            "Befehl kopieren", "Sprache", "Kopiert"
        ),
        "pt" to arrayOf(
            "Permissão de sobreposição necessária",
            "Permita exibir sobre outros apps para que a caixa de FPS apareça sobre o seu jogo.",
            "Conceder permissão",
            "Permita o acesso de uso para detectar quando o app selecionado for aberto.",
            "Selecione um app", "Nenhum app selecionado", "Buscar apps",
            "INICIAR", "PARAR", "Ativo — abra o app selecionado",
            "FPS real: LIGADO", "Modo limitado: apenas taxa de atualização da tela",
            "Ativar FPS real (uma vez)",
            "Execute este comando uma vez via ADB (a depuração sem fio funciona) ou use um aparelho com root:",
            "Copiar comando", "Idioma", "Copiado"
        ),
        "it" to arrayOf(
            "Serve il permesso di sovrapposizione",
            "Consenti la visualizzazione sopra altre app così il riquadro FPS appare sul tuo gioco.",
            "Concedi permesso",
            "Consenti l'accesso all'utilizzo per rilevare quando si apre l'app selezionata.",
            "Seleziona un'app", "Nessuna app selezionata", "Cerca app",
            "AVVIA", "FERMA", "Attivo — apri l'app selezionata",
            "FPS reali: ATTIVI", "Modalità limitata: solo frequenza di aggiornamento dello schermo",
            "Attiva gli FPS reali (una volta)",
            "Esegui questo comando una volta via ADB (va bene il debug wireless) o usa un dispositivo con root:",
            "Copia comando", "Lingua", "Copiato"
        ),
        "ar" to arrayOf(
            "مطلوب إذن العرض فوق التطبيقات",
            "اسمح بالعرض فوق التطبيقات الأخرى ليظهر مربع FPS فوق لعبتك.",
            "منح الإذن",
            "اسمح بالوصول إلى بيانات الاستخدام لاكتشاف وقت فتح التطبيق المحدد.",
            "اختر تطبيقًا", "لم يتم اختيار تطبيق", "ابحث عن تطبيق",
            "ابدأ", "إيقاف", "نشط — افتح التطبيق المحدد",
            "FPS الحقيقي: مفعّل", "وضع محدود: معدل تحديث الشاشة فقط",
            "تفعيل FPS الحقيقي (مرة واحدة)",
            "نفّذ هذا الأمر مرة واحدة عبر ADB (يعمل التصحيح اللاسلكي) أو استخدم جهازًا مروّتًا:",
            "نسخ الأمر", "اللغة", "تم النسخ"
        ),
        "hi" to arrayOf(
            "ओवरले अनुमति आवश्यक है",
            "FPS बॉक्स आपके गेम के ऊपर दिखाने के लिए अन्य ऐप्स के ऊपर दिखाने की अनुमति दें।",
            "अनुमति दें",
            "चुने गए ऐप के खुलने का पता लगाने के लिए उपयोग एक्सेस की अनुमति दें।",
            "ऐप चुनें", "कोई ऐप नहीं चुना गया", "ऐप खोजें",
            "शुरू करें", "रोकें", "सक्रिय — चुना हुआ ऐप खोलें",
            "असली FPS: चालू", "सीमित मोड: केवल डिस्प्ले रिफ्रेश रेट",
            "असली FPS चालू करें (एक बार)",
            "यह कमांड ADB से एक बार चलाएँ (वायरलेस डीबगिंग चलेगी) या रूट डिवाइस उपयोग करें:",
            "कमांड कॉपी करें", "भाषा", "कॉपी हो गया"
        ),
        "zh" to arrayOf(
            "需要悬浮窗权限",
            "请允许显示在其他应用上层，这样 FPS 框才能显示在游戏上方。",
            "授予权限",
            "请允许使用情况访问权限，以便检测所选应用何时打开。",
            "选择应用", "未选择应用", "搜索应用",
            "开始", "停止", "运行中 — 请打开所选应用",
            "真实 FPS：已开启", "受限模式：仅显示屏幕刷新率",
            "启用真实 FPS（仅需一次）",
            "通过 ADB 运行一次此命令（无线调试也可），或使用已 Root 的设备：",
            "复制命令", "语言", "已复制"
        ),
        "ja" to arrayOf(
            "オーバーレイ権限が必要です",
            "FPSボックスをゲームの上に表示するため、他のアプリの上に重ねて表示する権限を許可してください。",
            "権限を許可",
            "選択したアプリの起動を検出するため、使用状況へのアクセスを許可してください。",
            "アプリを選択", "アプリが未選択です", "アプリを検索",
            "開始", "停止", "動作中 — 選択したアプリを開いてください",
            "リアルFPS：オン", "制限モード：画面のリフレッシュレートのみ",
            "リアルFPSを有効化（1回のみ）",
            "このコマンドをADBで1回実行してください（ワイヤレスデバッグ可）。またはroot化した端末を使用：",
            "コマンドをコピー", "言語", "コピーしました"
        ),
        "ko" to arrayOf(
            "오버레이 권한이 필요합니다",
            "FPS 상자가 게임 위에 표시되도록 다른 앱 위에 표시 권한을 허용해 주세요.",
            "권한 허용",
            "선택한 앱이 실행되는 시점을 감지하도록 사용 정보 접근을 허용해 주세요.",
            "앱 선택", "선택된 앱 없음", "앱 검색",
            "시작", "중지", "작동 중 — 선택한 앱을 여세요",
            "실제 FPS: 켜짐", "제한 모드: 화면 주사율만 표시",
            "실제 FPS 활성화 (한 번만)",
            "이 명령어를 ADB로 한 번 실행하세요(무선 디버깅 가능). 또는 루팅된 기기를 사용하세요:",
            "명령어 복사", "언어", "복사됨"
        ),
        "id" to arrayOf(
            "Izin overlay diperlukan",
            "Izinkan tampil di atas aplikasi lain agar kotak FPS muncul di atas game Anda.",
            "Beri izin",
            "Izinkan akses penggunaan agar aplikasi dapat mendeteksi saat aplikasi pilihan dibuka.",
            "Pilih aplikasi", "Belum ada aplikasi dipilih", "Cari aplikasi",
            "MULAI", "HENTIKAN", "Aktif — buka aplikasi yang dipilih",
            "FPS asli: AKTIF", "Mode terbatas: hanya refresh rate layar",
            "Aktifkan FPS asli (sekali saja)",
            "Jalankan perintah ini sekali lewat ADB (debugging nirkabel bisa) atau gunakan perangkat root:",
            "Salin perintah", "Bahasa", "Disalin"
        ),
        "fa" to arrayOf(
            "مجوز نمایش روی برنامه‌ها لازم است",
            "اجازه نمایش روی برنامه‌های دیگر را بدهید تا کادر FPS بالای بازی شما نمایش داده شود.",
            "اعطای مجوز",
            "دسترسی به استفاده را مجاز کنید تا باز شدن برنامه انتخابی شناسایی شود.",
            "انتخاب برنامه", "برنامه‌ای انتخاب نشده", "جستجوی برنامه",
            "شروع", "توقف", "فعال — برنامه انتخابی را باز کنید",
            "FPS واقعی: روشن", "حالت محدود: فقط نرخ تازه‌سازی صفحه",
            "فعال‌سازی FPS واقعی (یک بار)",
            "این دستور را یک بار با ADB اجرا کنید (دیباگ بی‌سیم هم کار می‌کند) یا از دستگاه روت‌شده استفاده کنید:",
            "کپی دستور", "زبان", "کپی شد"
        ),
        "uk" to arrayOf(
            "Потрібен дозвіл на відображення поверх інших вікон",
            "Дозвольте показ поверх інших програм, щоб вікно FPS відображалося над грою.",
            "Надати дозвіл",
            "Дозвольте доступ до даних про використання, щоб визначати запуск вибраної програми.",
            "Виберіть програму", "Програму не вибрано", "Пошук програм",
            "СТАРТ", "СТОП", "Активно — відкрийте вибрану програму",
            "Реальний FPS: УВІМК.", "Обмежений режим: лише частота оновлення екрана",
            "Увімкнути реальний FPS (один раз)",
            "Виконайте цю команду один раз через ADB (підійде бездротове налагодження) або скористайтеся root-пристроєм:",
            "Копіювати команду", "Мова", "Скопійовано"
        ),
        "pl" to arrayOf(
            "Wymagane uprawnienie do wyświetlania nad innymi aplikacjami",
            "Zezwól na wyświetlanie nad innymi aplikacjami, aby pole FPS pojawiało się nad grą.",
            "Przyznaj uprawnienie",
            "Zezwól na dostęp do danych o użyciu, aby wykrywać otwarcie wybranej aplikacji.",
            "Wybierz aplikację", "Nie wybrano aplikacji", "Szukaj aplikacji",
            "START", "STOP", "Aktywne — otwórz wybraną aplikację",
            "Prawdziwe FPS: WŁ.", "Tryb ograniczony: tylko częstotliwość odświeżania ekranu",
            "Włącz prawdziwe FPS (jednorazowo)",
            "Uruchom to polecenie raz przez ADB (działa debugowanie bezprzewodowe) lub użyj urządzenia z rootem:",
            "Kopiuj polecenie", "Język", "Skopiowano"
        ),
        "nl" to arrayOf(
            "Overlaymachtiging vereist",
            "Sta weergave boven andere apps toe zodat het FPS-vak boven je game verschijnt.",
            "Toestemming geven",
            "Sta gebruikstoegang toe zodat de app kan detecteren wanneer je gekozen app opent.",
            "Kies een app", "Geen app gekozen", "Apps zoeken",
            "START", "STOP", "Actief — open je gekozen app",
            "Echte FPS: AAN", "Beperkte modus: alleen schermverversingssnelheid",
            "Echte FPS inschakelen (eenmalig)",
            "Voer deze opdracht eenmalig uit via ADB (draadloos debuggen werkt) of gebruik een geroot apparaat:",
            "Opdracht kopiëren", "Taal", "Gekopieerd"
        ),
        "vi" to arrayOf(
            "Cần quyền hiển thị trên ứng dụng khác",
            "Cho phép hiển thị trên ứng dụng khác để khung FPS xuất hiện trên game của bạn.",
            "Cấp quyền",
            "Cho phép truy cập dữ liệu sử dụng để phát hiện khi ứng dụng đã chọn được mở.",
            "Chọn ứng dụng", "Chưa chọn ứng dụng", "Tìm ứng dụng",
            "BẮT ĐẦU", "DỪNG", "Đang hoạt động — hãy mở ứng dụng đã chọn",
            "FPS thực: BẬT", "Chế độ giới hạn: chỉ tần số quét màn hình",
            "Bật FPS thực (một lần)",
            "Chạy lệnh này một lần qua ADB (gỡ lỗi không dây được) hoặc dùng thiết bị đã root:",
            "Sao chép lệnh", "Ngôn ngữ", "Đã sao chép"
        ),
        "th" to arrayOf(
            "ต้องใช้สิทธิ์แสดงทับแอปอื่น",
            "อนุญาตให้แสดงทับแอปอื่นเพื่อให้กล่อง FPS ปรากฏเหนือเกมของคุณ",
            "ให้สิทธิ์",
            "อนุญาตการเข้าถึงข้อมูลการใช้งานเพื่อตรวจจับเมื่อแอปที่เลือกเปิดขึ้น",
            "เลือกแอป", "ยังไม่ได้เลือกแอป", "ค้นหาแอป",
            "เริ่ม", "หยุด", "กำลังทำงาน — เปิดแอปที่เลือก",
            "FPS จริง: เปิด", "โหมดจำกัด: แสดงเฉพาะอัตรารีเฟรชหน้าจอ",
            "เปิดใช้ FPS จริง (ครั้งเดียว)",
            "รันคำสั่งนี้ครั้งเดียวผ่าน ADB (ดีบักไร้สายใช้ได้) หรือใช้อุปกรณ์ที่ root:",
            "คัดลอกคำสั่ง", "ภาษา", "คัดลอกแล้ว"
        ),
        "bn" to arrayOf(
            "ওভারলে অনুমতি প্রয়োজন",
            "আপনার গেমের উপরে FPS বক্স দেখাতে অন্য অ্যাপের উপরে প্রদর্শনের অনুমতি দিন।",
            "অনুমতি দিন",
            "নির্বাচিত অ্যাপ কখন খোলে তা শনাক্ত করতে ব্যবহার অ্যাক্সেসের অনুমতি দিন।",
            "অ্যাপ বেছে নিন", "কোনো অ্যাপ নির্বাচিত নয়", "অ্যাপ খুঁজুন",
            "শুরু", "থামান", "সক্রিয় — নির্বাচিত অ্যাপ খুলুন",
            "আসল FPS: চালু", "সীমিত মোড: শুধু ডিসপ্লে রিফ্রেশ রেট",
            "আসল FPS চালু করুন (একবার)",
            "এই কমান্ডটি ADB দিয়ে একবার চালান (ওয়্যারলেস ডিবাগিং চলবে) অথবা রুট করা ডিভাইস ব্যবহার করুন:",
            "কমান্ড কপি করুন", "ভাষা", "কপি হয়েছে"
        ),
        "ur" to arrayOf(
            "اوورلے کی اجازت درکار ہے",
            "FPS باکس کو آپ کے گیم کے اوپر دکھانے کے لیے دیگر ایپس کے اوپر دکھانے کی اجازت دیں۔",
            "اجازت دیں",
            "منتخب ایپ کے کھلنے کا پتا لگانے کے لیے استعمال تک رسائی کی اجازت دیں۔",
            "ایپ منتخب کریں", "کوئی ایپ منتخب نہیں", "ایپس تلاش کریں",
            "شروع کریں", "روکیں", "فعال — منتخب ایپ کھولیں",
            "اصل FPS: آن", "محدود موڈ: صرف ڈسپلے ریفریش ریٹ",
            "اصل FPS فعال کریں (ایک بار)",
            "یہ کمانڈ ADB کے ذریعے ایک بار چلائیں (وائرلیس ڈیبگنگ بھی چلے گی) یا روٹ شدہ ڈیوائس استعمال کریں:",
            "کمانڈ کاپی کریں", "زبان", "کاپی ہو گیا"
        ),
        "sv" to arrayOf(
            "Behörighet för överlagring krävs",
            "Tillåt visning över andra appar så att FPS-rutan visas ovanpå ditt spel.",
            "Ge behörighet",
            "Tillåt användningsåtkomst så att appen kan upptäcka när din valda app öppnas.",
            "Välj en app", "Ingen app vald", "Sök appar",
            "STARTA", "STOPPA", "Aktiv – öppna din valda app",
            "Riktig FPS: PÅ", "Begränsat läge: endast skärmens uppdateringsfrekvens",
            "Aktivera riktig FPS (engångs)",
            "Kör det här kommandot en gång via ADB (trådlös felsökning fungerar) eller använd en rootad enhet:",
            "Kopiera kommando", "Språk", "Kopierat"
        ),
        "cs" to arrayOf(
            "Je vyžadováno oprávnění k překrytí",
            "Povolte zobrazení přes ostatní aplikace, aby se okénko FPS zobrazilo nad vaší hrou.",
            "Udělit oprávnění",
            "Povolte přístup k využití, aby aplikace poznala, kdy se vybraná aplikace otevře.",
            "Vyberte aplikaci", "Není vybrána žádná aplikace", "Hledat aplikace",
            "START", "STOP", "Aktivní – otevřete vybranou aplikaci",
            "Skutečné FPS: ZAP", "Omezený režim: pouze obnovovací frekvence displeje",
            "Zapnout skutečné FPS (jednorázově)",
            "Spusťte tento příkaz jednou přes ADB (funguje bezdrátové ladění) nebo použijte zařízení s rootem:",
            "Kopírovat příkaz", "Jazyk", "Zkopírováno"
        ),
        "ro" to arrayOf(
            "Este necesară permisiunea de suprapunere",
            "Permite afișarea peste alte aplicații pentru ca fereastra FPS să apară deasupra jocului tău.",
            "Acordă permisiunea",
            "Permite accesul la utilizare pentru a detecta când se deschide aplicația selectată.",
            "Alege o aplicație", "Nicio aplicație selectată", "Caută aplicații",
            "PORNEȘTE", "OPREȘTE", "Activ — deschide aplicația selectată",
            "FPS real: PORNIT", "Mod limitat: doar rata de reîmprospătare a ecranului",
            "Activează FPS real (o singură dată)",
            "Rulează această comandă o dată prin ADB (funcționează depanarea wireless) sau folosește un dispozitiv cu root:",
            "Copiază comanda", "Limbă", "Copiat"
        ),
        "el" to arrayOf(
            "Απαιτείται άδεια επικάλυψης",
            "Επιτρέψτε την εμφάνιση πάνω από άλλες εφαρμογές ώστε το πλαίσιο FPS να εμφανίζεται πάνω από το παιχνίδι σας.",
            "Παροχή άδειας",
            "Επιτρέψτε την πρόσβαση χρήσης ώστε η εφαρμογή να εντοπίζει πότε ανοίγει η επιλεγμένη εφαρμογή.",
            "Επιλέξτε εφαρμογή", "Δεν έχει επιλεγεί εφαρμογή", "Αναζήτηση εφαρμογών",
            "ΕΝΑΡΞΗ", "ΔΙΑΚΟΠΗ", "Ενεργό — ανοίξτε την επιλεγμένη εφαρμογή",
            "Πραγματικά FPS: ΕΝΕΡΓΟ", "Περιορισμένη λειτουργία: μόνο ρυθμός ανανέωσης οθόνης",
            "Ενεργοποίηση πραγματικών FPS (μία φορά)",
            "Εκτελέστε αυτή την εντολή μία φορά μέσω ADB (λειτουργεί το ασύρματο debugging) ή χρησιμοποιήστε συσκευή με root:",
            "Αντιγραφή εντολής", "Γλώσσα", "Αντιγράφηκε"
        ),
        "he" to arrayOf(
            "נדרשת הרשאת שכבת-על",
            "אפשרו הצגה מעל אפליקציות אחרות כדי שתיבת ה-FPS תופיע מעל המשחק שלכם.",
            "מתן הרשאה",
            "אפשרו גישה לנתוני שימוש כדי לזהות מתי האפליקציה שנבחרה נפתחת.",
            "בחרו אפליקציה", "לא נבחרה אפליקציה", "חיפוש אפליקציות",
            "התחל", "עצור", "פעיל — פתחו את האפליקציה שנבחרה",
            "FPS אמיתי: פועל", "מצב מוגבל: רק קצב רענון המסך",
            "הפעלת FPS אמיתי (פעם אחת)",
            "הריצו פקודה זו פעם אחת דרך ADB (ניפוי באגים אלחוטי מתאים) או השתמשו במכשיר עם רוט:",
            "העתק פקודה", "שפה", "הועתק"
        ),
        "hu" to arrayOf(
            "Átfedési engedély szükséges",
            "Engedélyezd a más alkalmazások feletti megjelenítést, hogy az FPS-doboz a játékod fölött látszódjon.",
            "Engedély megadása",
            "Engedélyezd a használati hozzáférést, hogy az app észlelje a kiválasztott alkalmazás megnyitását.",
            "Válassz alkalmazást", "Nincs kiválasztott alkalmazás", "Alkalmazások keresése",
            "INDÍTÁS", "LEÁLLÍTÁS", "Aktív – nyisd meg a kiválasztott alkalmazást",
            "Valódi FPS: BE", "Korlátozott mód: csak a kijelző frissítési gyakorisága",
            "Valódi FPS bekapcsolása (egyszeri)",
            "Futtasd ezt a parancsot egyszer ADB-n keresztül (a vezeték nélküli hibakeresés is jó), vagy használj rootolt eszközt:",
            "Parancs másolása", "Nyelv", "Másolva"
        ),
        "da" to arrayOf(
            "Tilladelse til overlejring kræves",
            "Tillad visning over andre apps, så FPS-boksen vises oven på dit spil.",
            "Giv tilladelse",
            "Tillad brugsadgang, så appen kan registrere, når din valgte app åbnes.",
            "Vælg en app", "Ingen app valgt", "Søg efter apps",
            "START", "STOP", "Aktiv — åbn din valgte app",
            "Ægte FPS: TIL", "Begrænset tilstand: kun skærmens opdateringsfrekvens",
            "Aktivér ægte FPS (engangs)",
            "Kør denne kommando én gang via ADB (trådløs fejlfinding virker) eller brug en rootet enhed:",
            "Kopiér kommando", "Sprog", "Kopieret"
        ),
        "fi" to arrayOf(
            "Peittokerroksen lupa vaaditaan",
            "Salli näyttäminen muiden sovellusten päällä, jotta FPS-ruutu näkyy pelisi päällä.",
            "Myönnä lupa",
            "Salli käyttötietojen käyttö, jotta sovellus tunnistaa valitun sovelluksen avautumisen.",
            "Valitse sovellus", "Sovellusta ei ole valittu", "Hae sovelluksia",
            "KÄYNNISTÄ", "PYSÄYTÄ", "Aktiivinen — avaa valittu sovellus",
            "Todellinen FPS: PÄÄLLÄ", "Rajoitettu tila: vain näytön virkistystaajuus",
            "Ota todellinen FPS käyttöön (kerran)",
            "Suorita tämä komento kerran ADB:n kautta (langaton vianetsintä käy) tai käytä rootattua laitetta:",
            "Kopioi komento", "Kieli", "Kopioitu"
        ),
        "no" to arrayOf(
            "Tillatelse for overlegg kreves",
            "Tillat visning over andre apper slik at FPS-boksen vises oppå spillet ditt.",
            "Gi tillatelse",
            "Tillat tilgang til bruksdata slik at appen kan oppdage når den valgte appen åpnes.",
            "Velg en app", "Ingen app valgt", "Søk etter apper",
            "START", "STOPP", "Aktiv — åpne den valgte appen",
            "Ekte FPS: PÅ", "Begrenset modus: kun skjermens oppdateringsfrekvens",
            "Aktiver ekte FPS (én gang)",
            "Kjør denne kommandoen én gang via ADB (trådløs feilsøking fungerer) eller bruk en rootet enhet:",
            "Kopier kommando", "Språk", "Kopiert"
        ),
        "bg" to arrayOf(
            "Необходимо е разрешение за показване върху други приложения",
            "Разрешете показване върху други приложения, за да се вижда FPS полето над играта ви.",
            "Дай разрешение",
            "Разрешете достъп до използването, за да се засича отварянето на избраното приложение.",
            "Изберете приложение", "Не е избрано приложение", "Търсене на приложения",
            "СТАРТ", "СТОП", "Активно — отворете избраното приложение",
            "Реален FPS: ВКЛ.", "Ограничен режим: само честота на опресняване на екрана",
            "Включване на реален FPS (еднократно)",
            "Изпълнете тази команда веднъж през ADB (работи и безжично отстраняване на грешки) или ползвайте root устройство:",
            "Копиране на командата", "Език", "Копирано"
        ),
        "sr" to arrayOf(
            "Potrebna je dozvola za prikaz preko drugih aplikacija",
            "Dozvolite prikaz preko drugih aplikacija da bi se FPS okvir video iznad vaše igre.",
            "Dodeli dozvolu",
            "Dozvolite pristup korišćenju da bi aplikacija otkrila kada se izabrana aplikacija otvori.",
            "Izaberite aplikaciju", "Nijedna aplikacija nije izabrana", "Pretraži aplikacije",
            "POKRENI", "ZAUSTAVI", "Aktivno — otvorite izabranu aplikaciju",
            "Pravi FPS: UKLJUČEN", "Ograničen režim: samo učestalost osvežavanja ekrana",
            "Uključi pravi FPS (jednom)",
            "Pokrenite ovu komandu jednom preko ADB-a (radi i bežično debagovanje) ili koristite uređaj sa rootom:",
            "Kopiraj komandu", "Jezik", "Kopirano"
        ),
        "hr" to arrayOf(
            "Potrebno je dopuštenje za prikaz preko drugih aplikacija",
            "Dopustite prikaz preko drugih aplikacija kako bi se FPS okvir vidio iznad vaše igre.",
            "Dodijeli dopuštenje",
            "Dopustite pristup korištenju kako bi aplikacija otkrila kada se odabrana aplikacija otvori.",
            "Odaberite aplikaciju", "Nije odabrana nijedna aplikacija", "Traži aplikacije",
            "POKRENI", "ZAUSTAVI", "Aktivno — otvorite odabranu aplikaciju",
            "Pravi FPS: UKLJUČEN", "Ograničeni način: samo brzina osvježavanja zaslona",
            "Uključi pravi FPS (jednom)",
            "Pokrenite ovu naredbu jednom preko ADB-a (radi i bežično otklanjanje pogrešaka) ili koristite uređaj s rootom:",
            "Kopiraj naredbu", "Jezik", "Kopirano"
        ),
        "sk" to arrayOf(
            "Vyžaduje sa povolenie na prekrytie",
            "Povoľte zobrazenie cez ostatné aplikácie, aby sa okno FPS zobrazilo nad vašou hrou.",
            "Udeliť povolenie",
            "Povoľte prístup k využitiu, aby aplikácia zistila, kedy sa vybraná aplikácia otvorí.",
            "Vyberte aplikáciu", "Nie je vybratá žiadna aplikácia", "Hľadať aplikácie",
            "ŠTART", "STOP", "Aktívne — otvorte vybranú aplikáciu",
            "Skutočné FPS: ZAP.", "Obmedzený režim: iba obnovovacia frekvencia displeja",
            "Zapnúť skutočné FPS (jednorazovo)",
            "Spustite tento príkaz raz cez ADB (funguje bezdrôtové ladenie) alebo použite zariadenie s rootom:",
            "Kopírovať príkaz", "Jazyk", "Skopírované"
        ),
        "ms" to arrayOf(
            "Kebenaran tindanan diperlukan",
            "Benarkan paparan di atas apl lain supaya kotak FPS muncul di atas permainan anda.",
            "Beri kebenaran",
            "Benarkan akses penggunaan supaya apl dapat mengesan apabila apl pilihan anda dibuka.",
            "Pilih apl", "Tiada apl dipilih", "Cari apl",
            "MULA", "BERHENTI", "Aktif — buka apl yang dipilih",
            "FPS sebenar: HIDUP", "Mod terhad: kadar muat semula paparan sahaja",
            "Dayakan FPS sebenar (sekali sahaja)",
            "Jalankan arahan ini sekali melalui ADB (penyahpepijatan wayarles boleh) atau guna peranti root:",
            "Salin arahan", "Bahasa", "Disalin"
        ),
        "fil" to arrayOf(
            "Kailangan ang pahintulot sa overlay",
            "Payagan ang pagpapakita sa ibabaw ng ibang app para lumabas ang FPS box sa ibabaw ng iyong laro.",
            "Ibigay ang pahintulot",
            "Payagan ang usage access para matukoy kung kailan nabuksan ang napiling app.",
            "Pumili ng app", "Walang napiling app", "Maghanap ng app",
            "SIMULAN", "ITIGIL", "Aktibo — buksan ang napiling app",
            "Totoong FPS: NAKA-ON", "Limitadong mode: refresh rate lang ng display",
            "I-enable ang totoong FPS (isang beses)",
            "Patakbuhin ang command na ito nang isang beses gamit ang ADB (gumagana ang wireless debugging) o gumamit ng naka-root na device:",
            "Kopyahin ang command", "Wika", "Nakopya"
        ),
        "ka" to arrayOf(
            "საჭიროა გადაფარვის ნებართვა",
            "დაუშვით სხვა აპების ზემოთ ჩვენება, რათა FPS-ის ველი თქვენს თამაშზე გამოჩნდეს.",
            "ნებართვის მიცემა",
            "დაუშვით გამოყენების წვდომა, რათა აპმა აღმოაჩინოს არჩეული აპის გახსნა.",
            "აირჩიეთ აპი", "აპი არ არის არჩეული", "აპების ძიება",
            "დაწყება", "გაჩერება", "აქტიურია — გახსენით არჩეული აპი",
            "რეალური FPS: ჩართულია", "შეზღუდული რეჟიმი: მხოლოდ ეკრანის განახლების სიხშირე",
            "რეალური FPS-ის ჩართვა (ერთხელ)",
            "გაუშვით ეს ბრძანება ერთხელ ADB-ით (უსადენო გამართვაც გამოდგება) ან გამოიყენეთ root-ირებული მოწყობილობა:",
            "ბრძანების კოპირება", "ენა", "დაკოპირდა"
        ),
        "kk" to arrayOf(
            "Беттің үстінен көрсету рұқсаты қажет",
            "FPS терезесі ойыныңыздың үстінде көрінуі үшін басқа қолданбалардың үстінен көрсетуге рұқсат беріңіз.",
            "Рұқсат беру",
            "Таңдалған қолданба қашан ашылатынын анықтау үшін пайдалану деректеріне қатынауға рұқсат беріңіз.",
            "Қолданбаны таңдаңыз", "Қолданба таңдалмаған", "Қолданбаларды іздеу",
            "БАСТАУ", "ТОҚТАТУ", "Белсенді — таңдалған қолданбаны ашыңыз",
            "Нақты FPS: ҚОСУЛЫ", "Шектеулі режим: тек экранның жаңарту жиілігі",
            "Нақты FPS қосу (бір рет)",
            "Бұл пәрменді ADB арқылы бір рет орындаңыз (сымсыз жөндеу де жарайды) немесе root құрылғыны пайдаланыңыз:",
            "Пәрменді көшіру", "Тіл", "Көшірілді"
        ),
        "uz" to arrayOf(
            "Ustiga chiqarish ruxsati kerak",
            "FPS oynasi o'yiningiz ustida ko'rinishi uchun boshqa ilovalar ustida ko'rsatishga ruxsat bering.",
            "Ruxsat berish",
            "Tanlangan ilova qachon ochilishini aniqlash uchun foydalanish ma'lumotlariga ruxsat bering.",
            "Ilovani tanlang", "Ilova tanlanmagan", "Ilovalarni qidirish",
            "BOSHLASH", "TO'XTATISH", "Faol — tanlangan ilovani oching",
            "Haqiqiy FPS: YOQILGAN", "Cheklangan rejim: faqat ekran yangilanish chastotasi",
            "Haqiqiy FPSni yoqish (bir marta)",
            "Bu buyruqni ADB orqali bir marta bajaring (simsiz debag ham mos) yoki root qurilmadan foydalaning:",
            "Buyruqni nusxalash", "Til", "Nusxalandi"
        )
    )

    fun t(lang: String, i: Int): String {
        val en = data.getValue("en")
        val arr = data[lang] ?: en
        return arr.getOrNull(i) ?: en[i]
    }

    fun systemLang(): String {
        val c = when (val l = Locale.getDefault().language) {
            "iw" -> "he"
            "in" -> "id"
            "tl" -> "fil"
            "nb", "nn" -> "no"
            else -> l
        }
        return if (data.containsKey(c)) c else "en"
    }

    fun isRtl(lang: String): Boolean = lang == "ar" || lang == "fa" || lang == "he" || lang == "ur"
}
