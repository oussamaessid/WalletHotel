package com.example.hotelwallet.presentation.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : DialogFragment() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be call")
        }
        return binding.root
    }

    fun setLoading(loading: Boolean) {
        (activity as MainActivity).setLoading(loading)
    }

    fun setErrorAlert(
        errorMsg: String?,
        titleMsg: Any? = null,
        positiveBtn: Int,
        negativeBtn: Int? = null,
        positiveClick: ((View) -> Unit)? = null,
        negativeClick: ((View) -> Unit)? = null
    ) {
        (activity as MainActivity).setErrorAlert(
            errorMsg,
            titleMsg,
            positiveBtn,
            negativeBtn,
            positiveClick,
            negativeClick
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}