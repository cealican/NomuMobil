package alican.app.nomu.data.network

import alican.app.nomu.data.model.Enums
import kotlinx.serialization.json.JsonObject

class ServiceStatus {
    // TODO servis temel dönüşü bu şekilde değişecek
    var code = 0
    var value = ""

    constructor() {}

    constructor(code: Int, value: String) {
        this.code = code
        this.value = value
    }

   // @Throws(JSONException::class)
    fun fromJson(json: JsonObject) {
TODO()
        /*
        if (json.has("code")) {
            code = json.getInt("code")
        }

        if (json.has("value")) {
            value = json.getString("value")
        }*/
    }

    fun isSuccess() : Boolean {
        return code == Enums.ServiceStatusCodes.success.value
    }

    fun isInvalidToken() : Boolean {
        return code == Enums.ServiceStatusCodes.invalidToken.value || code == Enums.ServiceStatusCodes.loginFailed.value
    }

    fun hasData() : Boolean {
        return code != Enums.ServiceStatusCodes.noDataFound.value
    }
}
