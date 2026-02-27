package alican.app.nomu.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Material(
    val id: Int,
    val name: String
)