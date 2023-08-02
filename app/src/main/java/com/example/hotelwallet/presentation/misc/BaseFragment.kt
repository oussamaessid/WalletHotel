package com.example.hotelwallet.presentation.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hotelwallet.R
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import java.util.*

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
    private val toolbarConfiguration: ToolbarConfiguration?
) : Fragment() {
    val appViewModel by activityViewModels<AppViewModel>()
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
        (activity as MainActivity).apply {
            toolbarConfiguration?.initToolbarLayout()
        }
        return binding.root
    }

    fun ToolbarConfiguration.updateToolbarLayout(){
        initToolbarLayout()
    }

    private fun ToolbarConfiguration.initToolbarLayout() {
        try {
            val toolbarLayout =
                binding.root.findViewById<ConstraintLayout>(R.id.toolBar)
            val btnBackToPrevious =
                binding.root.findViewById<ImageView>(R.id.imgBack)
            val iconToolbarWithLogo =
                binding.root.findViewById<ImageView>(R.id.imgLogo)
            val textToolbarTitle =
                binding.root.findViewById<TextView>(R.id.txtTitle)
            val rightToolbarImage =
                binding.root.findViewById<ImageView>(R.id.imgRight)
            btnBackToPrevious?.setOnClickListener {
                requireActivity().onBackPressed()
            }
            rightToolbarImage.setOnClickListener{
                rightImageClick?.invoke()
            }

            toolbarLayout?.visibility = visibility
            iconToolbarWithLogo?.visibility = logoVisibility
            btnBackToPrevious?.visibility = btnBackVisibility

            title?.let { title ->
                textToolbarTitle.isVisible = true
                when (title) {
                    is String -> textToolbarTitle.text = title
                    is Int -> textToolbarTitle.text =
                        resources.getString(title)
                }
            } ?: also {
                textToolbarTitle.isVisible = false
                textToolbarTitle.text = ""
            }

            rightImageConfig?.let { imgResource ->
                rightToolbarImage.isVisible = true
                when (imgResource) {
                    is String -> {
                        rightToolbarImage.load(imgResource) {
                            placeholder(R.drawable.img_user_default)
                            transformations(CircleCropTransformation())
                        }
                    }
                    is Int -> rightToolbarImage?.setImageResource(imgResource)
                    else -> Unit
                }
            } ?: also {
                rightToolbarImage.isVisible = false
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }

    fun setLoading(loading: Boolean) {
        (activity as MainActivity).setLoading(loading)
    }

    fun setLanguage(lang: Locale){
        (activity as MainActivity).updateLocale(lang)
    }

    fun setBottomNavigation(navigation: Boolean) {
        (activity as MainActivity).setBottomNavigation(navigation)
    }

    fun setErrorAlert(
        errorMsg: String?,
        titleMsg: Any? = null,
        positiveBtn: Int = R.string.txt_btn_ok,
        negativeBtn: Int? = null,
        positiveClick: ((View) -> Unit)?= null,
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

    fun onBack() {
        requireActivity().onBackPressed()
        
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}