package alican.app.nomu.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class ServiceResult {
    @SerialName("resultInfo")
    var status = ServiceStatus()
    @SerialName("data")
    val data: JsonElement? = null
}

// Ortak JSON yapılandırması (Ktor ile aynı olmalı)
val commonJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * ServiceResult içindeki 'data' alanını istenen tipe dönüştürür.
 * T: Dönüştürülecek hedef model (Örn: List<Category> veya UserInfo)
 */
inline fun <reified T> ServiceResult.getDataAs(): T? {
    return try {
        data?.let { commonJson.decodeFromJsonElement<T>(it) }
    } catch (e: Exception) {
        println("Cast Hatası: ${e.message}")
        null
    }
}