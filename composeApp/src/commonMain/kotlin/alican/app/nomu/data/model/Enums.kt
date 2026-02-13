package alican.app.nomu.data.model

import kotlinx.serialization.SerialName


object Enums {
    enum class OperationStatus(val kod: Int) {
        Info(1),
        Warning(2),
        YesNo(3);
    }

    enum class ServiceMethods(val value: String) {
        options("OPTIONS"),
        get("GET"),
        head("HEAD"),
        post("POST"),
        put("PUT"),
        patch("PATCH"),
        delete("DELETE"),
        trace("TRACE"),
        connect("CONNECT");
    }

    enum class ServiceStatusCodes(val value: Int, val description: String) {
        success(200, "success"),
        loginFailed(201, "loginFailed"),
        invalidToken(202, "invalidToken"),
        changePassword(203, "changePassword"),
        unknownError(204, "unknownError"),
        noDataFound(205, "noDataFound"),
        missingParameter(206, "missingParameter"),

        invalidTokenHttp(401, "invalidToken"),
        notVerified(210, "notVerified"),
        dublicateEmail(150, "dublicateEmail"),
        dublicatePhone(151, "dublicatePhone"),
        outOfArea(212, "outOfArea"),
        invalidVerificationCode(211, "invalidVerificationCode"),
        orderCompleted(100, "orderCompleted"),
        cartUpdated(216, "cartUpdated");
    }

    enum class ContentType(val value: String) {
        ApplicationJson("application/json"),
        xWwwFormUrlEncoded("application/x-www-form-urlencoded"),
        form_data("application/form-data");
    }

    enum class ServiceResult(val value: String) {
        NoInternetConnection("NoInternetConnection"),
        UnknownError("UnknownError"),
        ServiceError("ServiceError"),
        InvalidUser("ServiceError");
    }

    enum class ResultCodes {
        OK, YES, NO;
    }

// Örnek olması için bırakıldı
    enum class InterviewStatus(private val stringValue: String, private val intValue: Int) {
        @SerialName("0")
        NEGATIVE("Olumsuz", 0),
        @SerialName("1")
        FOLLOW("Takip Edilecek", 1),
        @SerialName("2")
        POSSITIVE("Olumlu", 2),
        @SerialName("3")
        INVALID("", 3);

        override fun toString(): String {
            return stringValue
        }

        fun toInt(): Int {
            return intValue
        }

        companion object {
            fun getAllItemsDescription(): ArrayList<String> {
                val al = ArrayList<String>()
                al.add(NEGATIVE.stringValue)
                al.add(FOLLOW.stringValue)
                al.add(POSSITIVE.stringValue)
                return al
            }
        }
    }
}