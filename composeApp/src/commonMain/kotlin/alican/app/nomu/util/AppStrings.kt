package alican.app.nomu.util

data class AppStrings(
    val title: String,
    val categoryLabel: String,
    val ingredientsLabel: String,
    val locationLabel: String,
    val findButton: String,
    val loading: String,
    val calories: String,
    val protein: String,
    val carbs: String
)
val languages = mapOf(
    "tr" to AppStrings(
        "Nomu", "Kategori Seç","Dolabında ne var?", "Hangi bölgenin yiyeceği olsun?", "Tarif Bul",
        "Nomu Şefi düşünüyor...", "Kalori", "Protein", "Karbonh."
    ),
    "en" to AppStrings(
        "Nomu", "Kategori Seç", "What's in your fridge?", "Hangi bölgenin yiyeceği olsun?", "Find Recipes",
        "Nomu Chef is thinking...", "Calories", "Protein", "Carbs"
    ),
    "es" to AppStrings(
        "Nomu", "Kategori Seç", "¿Qué hay en tu nevera?", "Hangi bölgenin yiyeceği olsun?", "Buscar Recetas",
        "El chef Nomu está pensando...", "Calorías", "Proteína", "Carbohidratos"
    ),
    "fr" to AppStrings(
        "Nomu", "Kategori Seç", "Qu'y a-t-il dans votre frigo ?", "Hangi bölgenin yiyeceği olsun?", "Trouver des Recettes",
        "Le chef Nomu réfléchit...", "Calories", "Protéines", "Glucides"
    ),
    "de" to AppStrings(
        "Nomu", "Kategori Seç", "Was ist in deinem Kühlschrank?", "Hangi bölgenin yiyeceği olsun?", "Rezepte finden",
        "Nomu Chef denkt nach...", "Kalorien", "Eiweiß", "Kohlenhydrate"
    ),
    "ar" to AppStrings(
        "نومو", "Kategori Seç", "ماذا يوجد في ثلاجتك؟", "Hangi bölgenin yiyeceği olsun?", "ابحث عن وصفات",
        "الشيف نومو يفكر...", "سعرات", "بروتين", "كربوهيدرات"
    ),
    "zh" to AppStrings(
        "Nomu", "Kategori Seç", "你的冰箱里有什么？", "Hangi bölgenin yiyeceği olsun?", "查找食谱",
        "Nomu厨师正在思考...", "卡路里", "蛋白质", "碳水化合物"
    ),
    "hi" to AppStrings(
        "Nomu", "Kategori Seç", "आपके फ्रिज में क्या है?", "Hangi bölgenin yiyeceği olsun?", "व्यंजन खोजें",
        "Nomu शेफ सोच रहा है...", "कैलोरी", "प्रोटीन", "कार्ब्स"
    )
)

fun getStrings(lang: String): AppStrings = languages[lang] ?: languages["en"]!!