package com.example.hotelwallet.presentation.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.AlertDialogFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AlertDialogFragment] factory method to
 * create an instance of this fragment.
 */
class AlertDialogFragment : DialogFragment() {
    var titleId: Any? = null
    var descriptionId: Any? = R.string.txt_dynamic_text
    var subDescription: String? = null
    var positiveBtnName: Int = R.string.txt_btn_ok
    var negativeBtnName: Int? = null
    var isDialogCancelable: Boolean = false

    var positiveClickListener: ((View) -> Unit)? = null
    var negativeClickListener: ((View) -> Unit)? = null

    private var _binding: AlertDialogFragmentBinding? = null

    private val binding get() = requireNotNull(_binding)

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return AlertDialogFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        isCancelable = isDialogCancelable
        titleId?.let {
            binding.textTitleAlertDialog.visibility = View.VISIBLE
            when (it) {
                is Int -> binding.textTitleAlertDialog.text = getString(it)
                is String -> binding.textTitleAlertDialog.text = it
            }
        }

        descriptionId?.let {
            when (it) {
                is Int -> binding.textDescriptionAlertDialog.text = getString(it)
                is String -> binding.textDescriptionAlertDialog.text = it
            }
        }

        // binding.textDescriptionAlertDialog.text = getString(descriptionId, subDescription)
        binding.textPositiveBtnAlertDialog.text = getString(positiveBtnName)
        negativeBtnName?.let {
            binding.textNegativeBtnAlertDialog.text = getString(it)
            binding.textNegativeBtnAlertDialog.visibility = View.VISIBLE
        } ?: run {
            binding.textNegativeBtnAlertDialog.visibility = View.GONE
        }

        binding.textPositiveBtnAlertDialog.setOnClickListener {
            dismiss()
            positiveClickListener?.invoke(it)
        }
        binding.textNegativeBtnAlertDialog.setOnClickListener {
            dismiss()
            negativeClickListener?.invoke(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}