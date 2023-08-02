package com.example.hotelwallet.presentation.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentWelcomeBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.language.LanguageViewModel
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.zeugmasolutions.localehelper.Locales
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
), View.OnClickListener {

    private val languageViewModel by activityViewModels<LanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launchWhenStarted {
            appViewModel.stateLang.observe(viewLifecycleOwner) { lang ->
                Log.d("***Lang :", "$lang")
                if (!lang.isNullOrEmpty()) {
                    findNavController().popBackStack(R.id.welcomeFragment, true)
                    appViewModel.stateToken.observe(viewLifecycleOwner) { token ->
                        Log.d("***Token :", "$token")
                        if (!token.isNullOrEmpty()) {
                            findNavController().navigate(R.id.homeFragment)
                        } else {
                            findNavController().navigate(R.id.loginFragment)
                        }
                    }
                }
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)
        binding.btnEnglish.setOnClickListener(this)
        binding.btnArabic.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_english -> {
                setLanguage(Locales.English)
                languageViewModel.saveLanguage(Locales.English.language)
                findNavController().navigate(R.id.action_welcomeFragment_to_welcomeOnBoardingFragment)
            }
            R.id.btn_arabic -> {
                setLanguage(Locales.Arabic)
                languageViewModel.saveLanguage(Locales.Arabic.language)
                findNavController().navigate(R.id.action_welcomeFragment_to_welcomeOnBoardingFragment)
            }
        }
    }
}