package com.example.hotelwallet.domain.model

import android.view.View

class ToolbarConfiguration(
    var visibility: Int = View.GONE,
    var btnBackVisibility:  Int = View.GONE,
    var logoVisibility:  Int = View.GONE,
    var title: Any? = null, // This can be Id of string ressources or simple string
    var rightImageConfig: Any? = null,
    var rightImageClick: (() -> Unit)? = null
)