package alican.app.nomu.data.model


import kotlinx.serialization.Serializable

@Serializable
data class ServiceRequest(
    val contents: List<Content>,
    val safetySettings: List<SafetySetting> = listOf(
        SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_NONE"),
        SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_NONE"),
        SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_NONE"),
        SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_NONE")
    )
)

@Serializable
data class SafetySetting(
    val category: String,
    val threshold: String
)

@Serializable
data class Content(
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)
/*
// --- Gemini API Response Models ---
@Serializable
data class ServiceResponse(
    val candidates: List<Candidate>? = null
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String? = null
)*/

// --- Uygulama İçi Modeller (Domain Models) ---

/*
@Serializable
data class RecipeRecommendation(
    val recipes: List<RecipeSummary>
)

@Serializable
data class RecipeSummary(
    val name: String,
    val time: String,
    val difficulty: String,
    val calories: String,    // Örn: "450 kcal"
    val protein: String,     // Örn: "25g"
    val carbs: String        // Örn: "15g"
)

@Serializable
data class RecipeDetail(
    val name: String,
    val ingredients: List<String>,
    val steps: List<String>
)*/