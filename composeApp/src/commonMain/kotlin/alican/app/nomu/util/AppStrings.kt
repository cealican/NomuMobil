package alican.app.nomu.util

data class AppStrings(
    val title: String,
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
        "Nomu", "Dolabında ne var?", "Konum", "Tarif Bul",
        "Nomu Şefi düşünüyor...", "Kalori", "Protein", "Karbonh."
    ),
    "en" to AppStrings(
        "Nomu", "What's in your fridge?", "Location", "Find Recipes",
        "Nomu Chef is thinking...", "Calories", "Protein", "Carbs"
    ),
    "es" to AppStrings(
        "Nomu", "¿Qué hay en tu nevera?", "Ubicación", "Buscar Recetas",
        "El chef Nomu está pensando...", "Calorías", "Proteína", "Carbohidratos"
    ),
    "fr" to AppStrings(
        "Nomu", "Qu'y a-t-il dans votre frigo ?", "Emplacement", "Trouver des Recettes",
        "Le chef Nomu réfléchit...", "Calories", "Protéines", "Glucides"
    ),
    "de" to AppStrings(
        "Nomu", "Was ist in deinem Kühlschrank?", "Standort", "Rezepte finden",
        "Nomu Chef denkt nach...", "Kalorien", "Eiweiß", "Kohlenhydrate"
    ),
    "ar" to AppStrings(
        "نومو", "ماذا يوجد في ثلاجتك؟", "الموقع", "ابحث عن وصفات",
        "الشيف نومو يفكر...", "سعرات", "بروتين", "كربوهيدرات"
    ),
    "zh" to AppStrings(
        "Nomu", "你的冰箱里有什么？", "地点", "查找食谱",
        "Nomu厨师正在思考...", "卡路里", "蛋白质", "碳水化合物"
    ),
    "hi" to AppStrings(
        "Nomu", "आपके फ्रिज में क्या है?", "स्थान", "व्यंजन खोजें",
        "Nomu शेफ सोच रहा है...", "कैलोरी", "प्रोटीन", "कार्ब्स"
    )
)

fun getStrings(lang: String): AppStrings = languages[lang] ?: languages["en"]!!