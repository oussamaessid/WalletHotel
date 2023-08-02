package com.example.hotelwallet.presentation.scanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding: FragmentScannerBinding get() = requireNotNull(_binding)

    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)


        binding.btnScan.setOnClickListener {
                findNavController().navigate(R.id.action_scannerFragment_to_resultQrCodeFragment)
        }

        val intent = Intent ()
        val result = intent.getStringExtra(RESULT)



        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                binding.result.text = result.toString()
            }
        }

        return binding.root
    }

}