package alican.app.nomu.ui.ViewModel

import alican.app.nomu.data.model.Category
import alican.app.nomu.data.network.Service
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {
    // UI State'leri
    var categories by mutableStateOf<List<Category>>(emptyList())
    var locationSuggestions by mutableStateOf<List<String>>(emptyList())
    var ingredientSuggestions by mutableStateOf<List<String>>(emptyList())

    // Kullanıcı Seçimleri
    var selectedCategory by mutableStateOf<Category?>(null)
    var selectedLocation by mutableStateOf("")
    val selectedIngredients = mutableStateListOf<String>()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            service.fetchCategories()?.let { categories = it }
        }
    }

    fun onLocationChange(query: String) {
        selectedLocation = query
        if (query.length >= 3) {
            viewModelScope.launch {
                locationSuggestions = service.fetchLocationSuggestions(query)
            }
        }
    }


    fun onIngredientChange(query: String) {
        if (query.length >= 3) {
            viewModelScope.launch {
                ingredientSuggestions = service.fetchIngredientSuggestions(query)
            }
        }
    }


    fun addIngredient(name: String) {
        if (!selectedIngredients.contains(name)) {
            selectedIngredients.add(name)
        }
    }

    fun removeIngredient(name: String) {
        selectedIngredients.remove(name)
    }
}

/*
sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class SuccessList(val data: RecipeRecommendation) : UiState()
    data class SuccessDetail(val data: RecipeDetail) : UiState()
    data class Error(val message: String) : UiState()
}

class HomeViewModel {
    private val service = GeminiService()
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun findRecipes(ingredients: String, location: String, lang: String) {
        _uiState.value = UiState.Loading
        scope.launch {
            try {
                val result = if (isMock) getMockRecipes(lang) else service.getRecipes(ingredients, location, lang)

                _uiState.value = UiState.SuccessList(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun getRecipeDetails(recipeName: String, location: String,  lang: String) {
        _uiState.value = UiState.Loading
        scope.launch {
            try {
                val result = if (isMock) getMockRecipeDetail(recipeName, lang) else service.getRecipeDetail(recipeName, location, lang)
                _uiState.value = UiState.SuccessDetail(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Detay alınamadı")
            }
        }
    }

    fun resetToHome() {
        _uiState.value = UiState.Idle
    }
}*/