package com.luckyfrog.quickmart.core.app

import com.luckyfrog.quickmart.utils.helper.Constants
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import io.paperdb.Paper
import java.util.Locale

object AppPreferences {

    //region LanguageConfig
    //key
    const val APP_LANG = "AppLang"

    fun setLocale(lang: String) {
        Paper.book().write(APP_LANG, lang)
    }

    fun getLocale(): String {
        return when (Paper.book().read(APP_LANG, Constants.DEFAULT_SYSTEM_LOCALE_LANG)!!) {
            Constants.DEFAULT_SYSTEM_LOCALE_LANG -> {
                Locale.getDefault().language
            }

            Constants.INDONESIA_LOCALE_LANG -> {
                Constants.INDONESIA_LOCALE_LANG
            }

            Constants.ENGLISH_LOCALE_LANG -> {
                Constants.ENGLISH_LOCALE_LANG
            }

            else -> {
                Constants.ENGLISH_LOCALE_LANG
            }
        }
    }

    fun getSelectedLanguage(): String {
        return Paper.book().read(APP_LANG, Constants.DEFAULT_SYSTEM_LOCALE_LANG)!!
    }

    //endregion

    //region Theme

    const val APP_THEME = "AppTheme"
    const val ACCESS_TOKEN = "AccessToken"
    const val REFRESH_TOKEN = "RefreshToken"
    const val FIRST_TIME = "FirstTime"

    fun setTheme(theme: AppTheme) {
        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.Default)!!
    }


    fun setFirstTime(value: Boolean) {
        Paper.book().write(FIRST_TIME, value)
    }

    fun getFirstTime(): Boolean? {
        return Paper.book().read(FIRST_TIME, null)
    }
    //endregion
}