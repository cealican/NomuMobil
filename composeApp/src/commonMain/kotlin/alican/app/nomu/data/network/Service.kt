package alican.app.nomu.data.network

import alican.app.nomu.data.model.Category
import alican.app.nomu.data.model.CategoryResponse
import alican.app.nomu.data.model.Content
import alican.app.nomu.data.model.Enums
import alican.app.nomu.data.model.Part
import alican.app.nomu.data.model.ServiceRequest
import alican.app.nomu.util.token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json

class Service {

    class Url {
        companion object {
            val baseUrl = "https://elimdengelir.net/nomu/"
            val categoryUrl = baseUrl + "api/getCategories.php"
        }
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60000  // 60 saniye
            connectTimeoutMillis = 20000  // 20 saniye:
            socketTimeoutMillis = 60000   // 60 saniye
        }
    }
    private suspend fun makeGetServiceCall(headerList: Map<String, String>, url: String){
        val serviceResult = ServiceResult()

        try {
            val httpResponse: HttpResponse = client.get {
                url(url)

                headerList.forEach { item ->
                    headers.append(item.key, item.value)
                }
            }

            if (httpResponse.status.isSuccess()) {
                val response: String = httpResponse.body()
                val responset: ServiceResultTest = httpResponse.body()
                /*val response: ServiceResult = httpResponse.body()

                val data = response.dataObject
                val dataArr = response.dataArray
                val dataA = response.dataAny*/
                val test = ""

                /*response.body()?.let { resultBody ->
                    val result = resultBody.string()
                    if (result.isNotBlank()) {
                        val rj = JSONObject(result)

                        if (rj.has("status")) {
                            serviceResult.status.fromJson(rj.getJSONObject("status"))
                        }
                        if(serviceResult.status.isInvalidToken() && url != Url.Login) {
                            reloginAndRecallCurrent(rb)
                        } else {
                            try {
                                serviceResult.dataObject = rj.getJSONObject("data")
                            } catch (e: JSONException) {
                                try {
                                    serviceResult.dataArray = rj.getJSONArray("data")
                                }
                                catch (e: JSONException)
                                {
                                    serviceResult.dataAny = rj.get("data")
                                }
                            }
                        }
                    }
                }*/
            } else {
                serviceResult.status.code = Enums.ServiceResult.UnknownError.hashCode()
                serviceResult.status.value = "Servis Hatası" // TODO context.getString(R.string.ServiceErrorMessage)
            }
        } catch (e: IOException) {
            e.printStackTrace()

            serviceResult.status.code = Enums.ServiceResult.UnknownError.hashCode()
            serviceResult.status.value = "Servis Hatası" // TODO context.getString(R.string.ServiceErrorMessage)
        }
    }
    /*
    private suspend fun makeGetServiceCall(headerList: Map<String, String>, url: String) {
        /*val requestBody = ServiceRequest(
            contents = listOf(Content(parts = listOf(Part(text = body))))
        )*/
        val serviceResult = ServiceResult()

        return try {
            val httpResponse: HttpResponse = client.get {
                url(url)

                headerList.forEach { item ->
                    headers.append(item.key, item.value)
                }
            }

            if (!httpResponse.status.isSuccess()) {
/*                val errorText = httpResponse.bodyAsText()
                println("Service_HTTP_ERROR: ${httpResponse.status} - $errorText")
                throw Exception("Servis hatası: ${httpResponse.status}")*/

                serviceResult.status.code = Enums.ServiceResult.UnknownError.hashCode()
                serviceResult.status.value = "Servis Hatası" //context.getString(R.string.ServiceErrorMessage)
            } else {
                httpResponse.body?.let { resultBody ->
                    val result = resultBody.string()
                    if (result.isNotBlank()) {
                        val rj = JSONObject(result)

                        if (rj.has("status")) {
                            serviceResult.status.fromJson(rj.getJSONObject("status"))
                        }

                        try {
                            serviceResult.dataObject = rj.getJSONObject("data")
                        } catch (e: JSONException) {
                            try {
                                serviceResult.dataArray = rj.getJSONArray("data")
                            } catch (e: JSONException) {
                                serviceResult.dataAny = rj.get("data")
                            }
                        }
                    }
                }
            }

            /*
            val response: ServiceResponse = httpResponse.body()

            if (response.candidates.isNullOrEmpty()) {
                val fullResponse = httpResponse.bodyAsText()
                println("Service_REJECTION_REASON: $fullResponse")
                throw Exception("Logcat'e bakın.")
            }

            response.candidates.first().content.parts.first().text
                ?: throw Exception("Part text null")*/


        } catch (e: Exception) {
            println("Service_ERROR: ${e.message}")
            throw e
        }
    }*/

    suspend fun fetchCategories(): List<Category>? {
        val headers:  Map<String, String> = mapOf(
            "token" to token, // String
            "searchText" to "",  // String todo servisten parametre kalkacak
            "langId" to "2"// Int langlar alınacak.
        )
        var response = "" // TODO kalkacak
        //makeGetServiceCall(headers, "https://dog.ceo/api/breeds/image/random")
        //val response: String = makeGetServiceCall(headers, "https://dog.ceo/api/breeds/image/random")
        //val response: String = makeGetServiceCall(headers, Url.categoryUrl)
        makeGetServiceCall(headers, Url.categoryUrl)

        val json = Json { ignoreUnknownKeys = true }
        var categories: List<Category>? = null
        try {
            val apiResponse = json.decodeFromString<CategoryResponse>(response)

            if (apiResponse.resultInfo.Code == 0) {
                categories = apiResponse.data

                categories.forEach { println("Kategori: ${it.name}") }
            } else {
                println("Servis Hatası: ${apiResponse.resultInfo.Message}")
            }
        } catch (e: Exception) {
            println("JSON Parse Hatası: ${e.message}")
        }

        return categories
        //return listOf("a1", "b1", "c1")
    }

    fun fetchLocationSuggestions(query: String): List<String> {
        // TODO("Not yet implemented")
        return listOf("aaa2", "bbb2", "ccc2")
    }

    fun fetchIngredientSuggestions(query: String): List<String> {
        // TODO("Not yet implemented")

        return listOf("aaa3", "bbbb3", "cccc3","aaa4", "bbbb34", "cccc34","aaa35", "bbbb35", "cccc35","aaa36", "bbbb36", "cccc36","aaa37", "bbbb37", "cccc37")
    }
}