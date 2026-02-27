package alican.app.nomu.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MaterialCategory(
    val id: Int,
    val name: String,
    val materialList: MutableList<Material> = mutableListOf()
)
