package com.example.hotelwallet

import android.content.Context
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HotelApplication : LocaleAwareApplication(){
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}