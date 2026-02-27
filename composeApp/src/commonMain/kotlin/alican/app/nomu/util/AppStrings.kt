package alican.app.nomu.util

data class AppStrings(
    val title: String,
    val categoryLabel: String,
    val materialsLabel: String,
    val locationLabel: String,
    val findButton: String,
    val loading: String,
    val calories: String,
    val protein: String,
    val carbs: String
)

/*
    Language(1, "tr", "Türkçe 🇹🇷"),
    Language(2, "en", "English 🇺🇸"),
    Language(3, "es", "Español 🇪🇸"),
    Language(4, "fr", "Français 🇫🇷"),
    Language(5, "de", "Deutsch 🇩🇪"),
    Language(6, "ar", "العربية 🇸🇦"),
    Language(7, "zh", "中文 🇨🇳"),
    Language(8, "hi", "हिन्दी 🇮🇳")
*/
val languages = mapOf(
    1 to AppStrings(
        "Nomu", "Kategori Seç","Dolabında ne var?", "Hangi bölgenin yiyeceği olsun?", "Tarif Bul",
        "Nomu Şefi düşünüyor...", "Kalori", "Protein", "Karbonh."
    ),
    2 to AppStrings(
        "Nomu", "Kategori Seç", "What's in your fridge?", "Hangi bölgenin yiyeceği olsun?", "Find Recipes",
        "Nomu Chef is thinking...", "Calories", "Protein", "Carbs"
    ),
    3 to AppStrings(
        "Nomu", "Kategori Seç", "¿Qué hay en tu nevera?", "Hangi bölgenin yiyeceği olsun?", "Buscar Recetas",
        "El chef Nomu está pensando...", "Calorías", "Proteína", "Carbohidratos"
    ),
    4 to AppStrings(
        "Nomu", "Kategori Seç", "Qu'y a-t-il dans votre frigo ?", "Hangi bölgenin yiyeceği olsun?", "Trouver des Recettes",
        "Le chef Nomu réfléchit...", "Calories", "Protéines", "Glucides"
    ),
    5 to AppStrings(
        "Nomu", "Kategori Seç", "Was ist in deinem Kühlschrank?", "Hangi bölgenin yiyeceği olsun?", "Rezepte finden",
        "Nomu Chef denkt nach...", "Kalorien", "Eiweiß", "Kohlenhydrate"
    ),
    6 to AppStrings(
        "نومو", "Kategori Seç", "ماذا يوجد في ثلاجتك؟", "Hangi bölgenin yiyeceği olsun?", "ابحث عن وصفات",
        "الشيف نومو يفكر...", "سعرات", "بروتين", "كربوهيدرات"
    ),
    7 to AppStrings(
        "Nomu", "Kategori Seç", "你的冰箱里有什么？", "Hangi bölgenin yiyeceği olsun?", "查找食谱",
        "Nomu厨师正在思考...", "卡路里", "蛋白质", "碳水化合物"
    ),
    8 to AppStrings(
        "Nomu", "Kategori Seç", "आपके फ्रिज में क्या है?", "Hangi bölgenin yiyeceği olsun?", "व्यंजन खोजें",
        "Nomu शेफ सोच रहा है...", "कैलोरी", "प्रोटीन", "कार्ब्स"
    )
)

fun getStrings(langId: Int): AppStrings = languages[langId] ?: languages[2]!!