package com.example.hotelwallet.presentation.misc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelwallet.databinding.ActivityMainBinding
import com.example.hotelwallet.utility.TAG_ALERT_DIALOG_ERROR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingView.root.visibility = View.VISIBLE
            binding.loadingView.animationLoading.playAnimation()
        } else {
            binding.loadingView.root.visibility = View.GONE
            binding.loadingView.animationLoading.cancelAnimation()
        }
    }

    fun setErrorAlert(
        show: Boolean,
        errorMsg: String?,
        titleMsg: Any? = null,
        recreateActivity: Boolean = false,
        finishActivity: Boolean = false
    ) {
        if (show) {
            this.supportFragmentManager.let { fragmentManager ->
                AlertDialogFragment().apply {
                    titleId = titleMsg
                    descriptionId = errorMsg
                    positiveClickListener = {
                        dismiss()
                        when {
                            finishActivity -> finish()
                            recreateActivity -> recreate()
                        }
                    }
                }.show(fragmentManager, TAG_ALERT_DIALOG_ERROR)
            }
        }
    }
}