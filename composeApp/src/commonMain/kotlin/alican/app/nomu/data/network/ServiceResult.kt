package alican.app.nomu.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

class ServiceResult {

    @SerialName("resultInfo")
    var status = ServiceStatus()
    var dataObject: JsonObject? = null
    var dataArray: JsonArray? = null
    var dataAny: Any? = null
}

@Serializable
class ServiceResultTest{
    lateinit var message: String
    @SerialName("status")
    lateinit var dtatus: String
}