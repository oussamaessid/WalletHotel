package com.example.hotelwallet.presentation.language

import android.os.Bundle
import android.view.View
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentLanguageBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.KEY_Arabic
import com.example.hotelwallet.utility.KEY_ENGLISH
import com.zeugmasolutions.localehelper.Locales
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(
    FragmentLanguageBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_language
    )
), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)

        appViewModel.getCurrentLang()

        appViewModel.stateLang.observe(viewLifecycleOwner) { language ->
            when (language) {
                KEY_ENGLISH -> {
                    binding.imgCheckEn.setImageResource(R.drawable.circle_blue)
                    binding.imgCheckAr.setImageResource(R.drawable.circle_grey)
                }
                KEY_Arabic -> {
                    binding.imgCheckEn.setImageResource(R.drawable.circle_grey)
                    binding.imgCheckAr.setImageResource(R.drawable.circle_blue)
                }
            }
        }

        binding.linearLangEn.setOnClickListener(this)
        binding.linearLangAr.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.linearLangEn -> {
                setLanguage(Locales.English)
                appViewModel.saveLanguage(Locales.English.language)
            }
            R.id.linearLangAr -> {
                setLanguage(Locales.Arabic)
                appViewModel.saveLanguage(Locales.Arabic.language)
            }
        }
    }
}