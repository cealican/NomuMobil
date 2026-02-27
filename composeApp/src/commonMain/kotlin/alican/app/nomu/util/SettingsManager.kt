package alican.app.nomu.util

import com.russhwolf.settings.Settings

class SettingsManager {
    private val settings: Settings = Settings()

    companion object {
        private const val SELECTED_LANGUAGE = "selected_language"
    }

    fun setLanguage(langId: Int) {
        settings.putInt(SELECTED_LANGUAGE, langId)
        selectedLanguageId = langId
    }
    fun getLanguage(): Int {
        return settings.getInt(SELECTED_LANGUAGE, 2) // Varsayılan Türkçe
    }
    /*fun setLanguage(langCode: String) {
        settings.putString(SELECTED_LANGUAGE, langCode)
        selectedLanguage = langCode
    }

    fun getLanguage(): String {
        return settings.getString(SELECTED_LANGUAGE, "en") // Varsayılan Türkçe
    }*/
}