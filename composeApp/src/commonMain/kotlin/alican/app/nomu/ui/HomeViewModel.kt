package alican.app.nomu.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import alican.app.nomu.data.model.RecipeDetail
import alican.app.nomu.data.model.RecipeRecommendation
import alican.app.nomu.data.network.GeminiService

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
                // Service'e lang parametresini ekledik
                val result = service.getRecipes(ingredients, location, lang)
                _uiState.value = UiState.SuccessList(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun getRecipeDetails(recipeName: String, lang: String) {
        _uiState.value = UiState.Loading
        scope.launch {
            try {
                val result = service.getRecipeDetail(recipeName, lang)
                _uiState.value = UiState.SuccessDetail(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Detay alınamadı")
            }
        }
    }

    fun resetToHome() {
        _uiState.value = UiState.Idle
    }
}