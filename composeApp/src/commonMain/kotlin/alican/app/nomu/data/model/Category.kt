package alican.app.nomu.data.model

import kotlinx.serialization.Serializable
@Serializable
data class Category(
    val id: String,
    val name: String
)

@Serializable
data class ResultInfo(
    val Code: Int,
    val Message: String,
    val ErrorCode: String,
    val ErrorMessage: String
)

@Serializable
data class CategoryResponse(
    val resultInfo: ResultInfo,
    val data: List<Category>
)