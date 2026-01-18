package alican.app.nomu.data.network

import alican.app.nomu.data.model.Content
import alican.app.nomu.data.model.GeminiRequest
import alican.app.nomu.data.model.GeminiResponse
import alican.app.nomu.data.model.Part
import alican.app.nomu.data.model.RecipeDetail
import alican.app.nomu.data.model.RecipeRecommendation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GeminiService {
    private val apiKey = "AIzaSyAFgIa0OivQYAQam1Lw8UxD2vPM02I_cwU"

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

    /*private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        // Zaman aşımı sorunlarını önlemek için:
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
        }
    }*/
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        // Zaman aşımı (Timeout) ayarlarını profesyonel seviyeye çekiyoruz
        install(HttpTimeout) {
            requestTimeoutMillis = 60000  // 60 saniye: Gemini'nin detaylı tarif hazırlaması için yeterli süre
            connectTimeoutMillis = 20000  // 20 saniye: Sunucuya bağlanma süresi
            socketTimeoutMillis = 60000   // 60 saniye: Veri akışı sırasında beklenecek maksimum süre
        }
    }

    suspend fun getRecipes(ingredients: String, location: String, lang: String): RecipeRecommendation {
        // Dil kontrolüne göre sistem talimatını belirliyoruz
        val languageInstruction = if (lang == "en") {
            "You MUST respond in ENGLISH language only."
        } else {
            "Yanıtları sadece TÜRKÇE dilinde ver."
        }



        val prompt = """
            ROLE: You are an expert global chef.

            STRICT RULE 1: First, translate all provided INGREDIENTS and LOCATION into English.
            STRICT RULE 2: Based on the translated information, suggest 3 dishes.
            STRICT RULE 3: You MUST respond in ENGLISH language only. All values in the JSON must be in English.
            STRICT RULE: $languageInstruction
            
            TASK: Suggest 3 popular dishes based on the following:
            INGREDIENTS: $ingredients
            LOCATION: $location
        
            OUTPUT FORMAT: Provide ONLY a raw JSON object. No preamble, no markdown blocks, no extra text.

            JSON SCHEMA: 
            {
              "recipes": [
                {
                  "name": "Name of the dish (In English)",
                  "time": "Estimated time",
                  "difficulty": "Easy/Medium/Hard"
                }
              ]
            }
        """.trimIndent()

       /* val prompt = """
        ROLE: You are an expert global chef.
        STRICT RULE: $languageInstruction
        
        TASK: Suggest 3 popular dishes based on the following:
        INGREDIENTS: $ingredients
        LOCATION: $location
        
        OUTPUT FORMAT: Provide ONLY a raw JSON object. No preamble, no markdown blocks, no extra text.
        
        JSON SCHEMA: 
        {
          "recipes": [
            {
              "name": "Name of the dish",
              "time": "Estimated time",
              "difficulty": "Easy/Medium/Hard"
            }
          ]
        }
    """.trimIndent()*/

        val responseText = makeGeminiCall(prompt)
    /*
        // Prompt'u daha otoriter ve net hale getirdik
        val prompt = """
        Sen bir şefsin. Yanıtları ${if(lang == "en") "English" else "Turkish"} dilinde ver.
        GÖREV: Kullanıcıya yemek önerisi yap.
        MALZEMELER: $ingredients
        LOKASYON: $location
        FORMAT: Sadece JSON döndür. Başka hiçbir açıklama metni ekleme.
        JSON ŞABLONU: {"recipes": [{"name": "Yemek Adı", "time": "30 dk", "difficulty": "Kolay"}]}
    """.trimIndent()

        val responseText = makeGeminiCall(prompt)
*/
        // Markdown bloklarını temizlemek için daha güçlü bir temizleyici
        val cleanJson = responseText
            .replace(Regex("```json"), "")
            .replace(Regex("```"), "")
            .trim()

        return Json { ignoreUnknownKeys = true }.decodeFromString(cleanJson)
    }

    suspend fun getRecipeDetail(recipeName: String, lang: String): RecipeDetail {

        val languageInstruction = if (lang == "en") {
            "You MUST respond in ENGLISH language only."
        } else {
            "Yanıtları sadece TÜRKÇE dilinde ver."
        }

        val prompt = """
            
            ROLE: You are an expert global chef.

            STRICT RULE 3: You MUST respond in ENGLISH language only. All values in the JSON must be in English.
            STRICT RULE: $languageInstruction
            
            $recipeName yemeği için tarifi şu JSON formatında ver: {\"name\": \"$recipeName\", \"ingredients\": [\"madde1\"], \"steps\": [\"adım1\"]}""".trimIndent()

        val responseText = makeGeminiCall(prompt)
        val cleanJson = cleanJson(responseText)
        return Json { ignoreUnknownKeys = true }.decodeFromString(cleanJson)
    }

    private suspend fun makeGeminiCall(promptText: String): String {
        val requestBody = GeminiRequest(
            contents = listOf(Content(parts = listOf(Part(text = promptText))))
        )

        return try {
            val httpResponse: io.ktor.client.statement.HttpResponse = client.post {
                url(baseUrl)
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }

            val response: GeminiResponse = httpResponse.body()

            // EĞER CANDIDATE NULL İSE NEDENİNİ LOGLA
            if (response.candidates.isNullOrEmpty()) {
                val fullResponse = httpResponse.bodyAsText() // Ham yanıtın tamamını al
                println("GEMINI_REJECTION_REASON: $fullResponse")
                throw Exception("Gemini içeriği reddetti veya boş döndü. Logcat'e bakın.")
            }

            response.candidates.first().content.parts.first().text
                ?: throw Exception("Part text null")

        } catch (e: Exception) {
            println("GEMINI_ERROR: ${e.message}")
            throw e
        }
    }

    private fun cleanJson(input: String): String {
        return input.replace("```json", "").replace("```", "").trim()
    }
}