package alican.app.nomu.data.network

import alican.app.nomu.data.model.Enums
import alican.app.nomu.data.model.MaterialCategory
import alican.app.nomu.util.token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class Service {

    class Url {
        companion object {
            val baseUrl = "https://elimdengelir.net/nomu/"
            val getMaterialsWithCategoryUrl = baseUrl + "api/getAllMaterials.php"
        }
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 20000
            socketTimeoutMillis = 60000
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
    private suspend fun makeGetServiceCall(headerList: Map<String, String>, url: String) : ServiceResult {
        var serviceResult = ServiceResult()

        try {
            val httpResponse: HttpResponse = client.get {
                url(url)

                headerList.forEach { item ->
                    headers.append(item.key, item.value)
                }
            }

            if (httpResponse.status.isSuccess()) {
                val responseStr: String = httpResponse.body()
                val jsonInstance = Json { ignoreUnknownKeys = true }
                serviceResult = jsonInstance.decodeFromString<ServiceResult>(responseStr)

            } else {
                serviceResult.status.code = Enums.ServiceResult.UnknownError.hashCode()
                serviceResult.status.value = "Servis Hatası" // TODO context.getString(R.string.ServiceErrorMessage)
            }
        } catch (e: IOException) {
            e.printStackTrace()

            serviceResult.status.code = Enums.ServiceResult.UnknownError.hashCode()
            serviceResult.status.value = "Servis Hatası" // TODO context.getString(R.string.ServiceErrorMessage)
        }
        return serviceResult
    }

    suspend fun fetchMaterialsWithCategories(langId: Int): List<MaterialCategory>? {
        val headers:  Map<String, String> = mapOf(
            "token" to token,
            "langId" to langId.toString()
        )

        val response: ServiceResult = makeGetServiceCall(headers, Url.getMaterialsWithCategoryUrl)

        var categoryList: List<MaterialCategory>? = null
        try {
            val json = Json{}
            categoryList = response.data?.let {
                json.decodeFromJsonElement<List<MaterialCategory>>(it)
            }
        } catch (e: Exception) {
            println("JSON Parse Hatası: ${e.message}")
        }

        return categoryList
    }

    fun fetchLocationSuggestions(query: String): List<String> {
        // TODO("Not yet implemented")
        return listOf("aaa2", "bbb2", "ccc2")
    }

/*
    fun fetchMaterialSuggestions(query: String): List<String> {
        // TODO("Not yet implemented")

        return listOf("aaa3", "bbbb3", "cccc3","aaa4", "bbbb34", "cccc34","aaa35", "bbbb35", "cccc35","aaa36", "bbbb36", "cccc36","aaa37", "bbbb37", "cccc37")
    }*/
}