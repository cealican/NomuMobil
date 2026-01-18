package alican.app.nomu.util

data class AppStrings(
    val title: String,
    val ingredientsLabel: String,
    val locationLabel: String,
    val findButton: String,
    val loading: String
)

val trStrings = AppStrings(
    title = "Nomu",
    ingredientsLabel = "Malzemeler (Örn: Tavuk, Mantar)",
    locationLabel = "Konum/Ülke",
    findButton = "Tarif Bul",
    loading = "Nomu Şefi Düşünüyor..."
)

val enStrings = AppStrings(
    title = "Nomu",
    ingredientsLabel = "Ingredients (e.g. Chicken, Mushroom)",
    locationLabel = "Location/Country",
    findButton = "Find Recipe",
    loading = "Nomu Chef is Thinking..."
)

fun getStrings(lang: String) = if (lang == "en") enStrings else trStrings