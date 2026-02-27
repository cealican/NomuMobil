package alican.app.nomu.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResultInfo(
    val Code: Int,
    val Message: String,
    val ErrorCode: String,
    val ErrorMessage: String
)
