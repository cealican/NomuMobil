package alican.app.nomu.util

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class SettingsManager {
    private val settings: Settings = Settings()

    companion object {
        private const val SELECTED_LANGUAGE = "selected_language"
    }

    fun setLanguage(langCode: String) {
        settings.putString(SELECTED_LANGUAGE, langCode)
    }

    fun getLanguage(): String {
        return settings.getString(SELECTED_LANGUAGE, "tr") // Varsayılan Türkçe
    }
}