package alican.app.nomu.data.model.response

import alican.app.nomu.data.model.MaterialCategory
import kotlinx.serialization.Serializable


@Serializable
data class MaterialCategoryResponse(
    val resultInfo: ResultInfo,
    val data: List<MaterialCategory>
)