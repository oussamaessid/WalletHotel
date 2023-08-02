package com.example.hotelwallet.utility

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IdRes
import com.example.hotelwallet.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout

fun isValidEmail(target: String?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun EditText.onTextChanged(textInputLayout: TextInputLayout) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty())
                textInputLayout.error = ""
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
    val itemView: BottomNavigationItemView = bottomNavigationView.findViewById(itemId)
    if (itemView.childCount == 3) {
        itemView.removeViewAt(2);
    }
}

fun showBadge(
    context: Context?,
    bottomNavigationView: BottomNavigationView,
    @IdRes itemId: Int,
    value: String?
) {
    val itemView: BottomNavigationItemView = bottomNavigationView.findViewById(itemId)
    val badge: View = LayoutInflater.from(context)
        .inflate(R.layout.count_notification_layout, bottomNavigationView, false)
    val text = badge.findViewById<TextView>(R.id.txtCount)
    text.text = value
    itemView.addView(badge)
}
