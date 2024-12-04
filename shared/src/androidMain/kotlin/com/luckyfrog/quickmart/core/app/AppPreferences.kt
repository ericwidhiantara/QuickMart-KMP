package com.luckyfrog.quickmart.core.app

import com.luckyfrog.quickmart.utils.resource.theme.AppTheme

object AppPreferences {

    //region LanguageConfig
    //key
    const val APP_LANG = "AppLang"

    fun setLocale(lang: String) {
//        Paper.book().write(APP_LANG, lang)
    }

    //endregion

    //region Theme

    const val APP_THEME = "AppTheme"
    const val ACCESS_TOKEN = "AccessToken"
    const val REFRESH_TOKEN = "RefreshToken"
    const val FIRST_TIME = "FirstTime"

    fun setTheme(theme: AppTheme) {
//        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return AppTheme.Default
//        return Paper.book().read(APP_THEME, AppTheme.Default)!!
    }


    fun setFirstTime(value: Boolean): Boolean {
        return false
//        Paper.book().write(FIRST_TIME, value)
    }

    fun getFirstTime(): Boolean? {
        return false
//        return Paper.book().read(FIRST_TIME, null)
    }
    //endregion
}