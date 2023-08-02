package com.example.hotelwallet.utility

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.location.Location
import com.example.hotelwallet.utility.PreferenceHelper.get
import com.example.hotelwallet.utility.PreferenceHelper.set
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*


object LocaleManager {

    val nf: NumberFormat = DecimalFormat("##.###", DecimalFormatSymbols(Locale.US))

    var mSharedPreference: SharedPreferences? = null

    var myLocation: Location? = null

    fun setLocale(context: Context?): Context {
        return updateResources(
            context!!,
            getCurrentLanguage(context)!!
        )
    }

    fun setLocation(location: Location): Int {
        myLocation = location
        return 1
    }

    fun getLocation(): Location? {
        return myLocation
    }

    fun setNewLocale(context: Context, language: String) {

        persistLanguagePreference(context, language)
        updateResources(context, language)
    }

    fun getCurrentLanguage(context: Context?): String? {

        val mCurrentLanguage: String?

        if (mSharedPreference == null)
            mSharedPreference = PreferenceHelper.defaultPrefs(context!!)

        mCurrentLanguage = mSharedPreference!![KEY_LANGUAGE, KEY_ENGLISH]

        return mCurrentLanguage
    }

    fun getCurrentLanguage(): String? {

        var mCurrentLanguage = KEY_ENGLISH

        if (mSharedPreference != null) {
            mCurrentLanguage = mSharedPreference!![KEY_LANGUAGE, KEY_ENGLISH]!!
        }

        return mCurrentLanguage
    }

    fun persistLanguagePreference(context: Context, language: String) {
        if (mSharedPreference == null)
            mSharedPreference = PreferenceHelper.defaultPrefs(context)

        mSharedPreference!![KEY_LANGUAGE] = language

    }

    fun updateResources(context: Context, language: String): Context {

        var contextFun = context

        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        configuration.setLocale(locale)
        contextFun = context.createConfigurationContext(configuration)

        return contextFun
    }
}