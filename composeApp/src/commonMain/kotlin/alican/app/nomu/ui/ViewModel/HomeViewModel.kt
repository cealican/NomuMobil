package alican.app.nomu.ui.ViewModel

import alican.app.nomu.data.model.Material
import alican.app.nomu.data.model.MaterialCategory
import alican.app.nomu.data.model.Zone
import alican.app.nomu.data.network.Service
import alican.app.nomu.util.SettingsManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    var categoriesWithMaterials by mutableStateOf<List<MaterialCategory>>(emptyList())

    var zoneSuggestions by mutableStateOf<List<Zone>>(emptyList())
    var materialSuggestions by mutableStateOf<List<String>>(emptyList())

    // Kullanıcı Seçimleri
    var selectedZone by mutableStateOf<Zone?>(null)
    var typedZone by mutableStateOf("")
    //val selectedMaterials = mutableStateListOf<String>()
    var selectedMaterials by mutableStateOf<MutableList<Material>>(mutableStateListOf())
        //private set

    fun toggleMaterial(material: Material) {
        if (selectedMaterials.contains(material)) {
            removeMaterial(material)
        } else {
            addMaterial(material)
        }
    }

    init {
        getAllMaterialsWithCategory()
    }

    fun onZoneChange(query: String) {
        typedZone = query
        if (zoneSuggestions.firstOrNull{it.name == query} == null) {
            if (query.length >= 2) {
                viewModelScope.launch {
                    zoneSuggestions = service.fetchZones(query)
                }
            }
        }
    }

    /*
    fun onMaterialChange(query: String) {
        if (query.length >= 3) {
            viewModelScope.launch {
                materialSuggestions = service.fetchMaterialSuggestions(query)
            }
        }
    }*/

    fun addMaterial(material: Material) {
        if (!selectedMaterials.contains(material)) {
            selectedMaterials.add(material)
        }
    }

    fun removeMaterial(material: Material) {
        selectedMaterials.remove(material)
    }

    fun clearMateriallist() {
        selectedMaterials.clear()
        categoriesWithMaterials = emptyList()
    }

    fun getAllMaterialsWithCategory() {

        viewModelScope.launch {
            service.fetchMaterialsWithCategories()?.let { categoriesWithMaterials = it }
        }
    }
}