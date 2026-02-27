package alican.app.nomu.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val id: Int,
    val code: String,
    val name: String
)