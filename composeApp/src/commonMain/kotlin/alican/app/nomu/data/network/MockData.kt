package alican.app.nomu.data.network

import alican.app.nomu.data.model.RecipeDetail
import alican.app.nomu.data.model.RecipeRecommendation
import alican.app.nomu.data.model.RecipeSummary

// GeminiService.kt

// Test amaçlı sahte veri fonksiyonu
suspend fun getMockRecipes(lang: String): RecipeRecommendation {
    kotlinx.coroutines.delay(1000) // Gerçekçi olması için 1 sn gecikme

    return if (lang == "en") {
        RecipeRecommendation(
            recipes = listOf(
                RecipeSummary(
                    "Stuffed Eggplant (Karniyarik)",
                    "45 min",
                    "Medium",
                    "320 kcal",
                    "12g",
                    "18g"
                ),
                RecipeSummary("Turkish Musakka", "40 min", "Medium", "410 kcal", "25g", "12g"),
                RecipeSummary("Eggplant with Meatballs", "30 min", "Easy", "380 kcal", "22g", "10g")
            )
        )
    } else {
        RecipeRecommendation(
            recipes = listOf(
                RecipeSummary("Karnıyarık", "45 dk", "Orta", "320 kcal", "12g", "18g"),
                RecipeSummary("Patlıcan Musakka", "40 dk", "Orta", "410 kcal", "25g", "12g"),
                RecipeSummary("Köfteli Patlıcan Dizme", "30 dk", "Kolay", "380 kcal", "22g", "10g")
            )
        )
    }
}

// Detay sayfası için sahte veri
suspend fun getMockRecipeDetail(recipeName: String, lang: String): RecipeDetail {
    kotlinx.coroutines.delay(800)
    return if (lang == "en") {
        RecipeDetail(
            name = recipeName,
            ingredients = listOf("2 pcs Eggplant", "200g Minced Meat", "1 Onion", "2 Tomatoes"),
            steps = listOf("Slice the eggplants", "Fry the meat with onions", "Fill the eggplants", "Bake for 20 mins")
        )
    } else {
        RecipeDetail(
            name = recipeName,
            ingredients = listOf("2 adet patlıcan", "200g Kıyma", "1 Soğan", "2 Domates"),
            steps = listOf("Patlıcanları dilimleyin", "eti soğanla birlikte kavurun", "patlıcanların içini doldurun", "20 dakika pişirin")
        )
    }
}