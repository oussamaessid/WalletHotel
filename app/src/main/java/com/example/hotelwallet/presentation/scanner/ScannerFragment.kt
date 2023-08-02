package com.example.hotelwallet.presentation.scanner


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentScannerBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : BaseFragment<FragmentScannerBinding>(
    FragmentScannerBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
) {

    private lateinit var result: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScan.setOnClickListener {
            findNavController().navigate(R.id.action_scannerFragment_to_resultQrCodeFragment)
        }

        getMealInformationFromIntent()

        if(result !="null"){
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                binding.txtResult.isVisible = true
                binding.txtResult.text = result.toString()
                binding.btnScan.text = getString(R.string.txt_scan_to_login)
                binding.btnScan.setOnClickListener {
                    findNavController().navigate(R.id.action_scannerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun getMealInformationFromIntent() {
        val args = this.arguments
        result = args?.get("RESULT").toString()
    }
}