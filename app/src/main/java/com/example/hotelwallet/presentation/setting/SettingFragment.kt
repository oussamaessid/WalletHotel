package com.example.hotelwallet.presentation.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentSettingBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.presentation.authentication.AuthenticationViewModel
import com.example.hotelwallet.utility.Resource

class SettingFragment : BaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.GONE,
        title = R.string.txt_title_settings
    )
), View.OnClickListener {

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(true)
        observeLogout()

        binding.linearProfile.setOnClickListener(this)
        binding.linearLanguage.setOnClickListener(this)
        binding.linearHistorical.setOnClickListener(this)
        binding.linearQuestion.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun observeLogout() {
        lifecycleScope.launchWhenStarted {
            authenticationViewModel.stateLogout.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.let { isLogout ->
                            if (isLogout) {
                                appViewModel.getToken()
                                findNavController().navigate(
                                    R.id.action_settingFragment_to_loginFragment)
                            }
                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setErrorAlert(errorMsg = it.message)
                        setLoading(false)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.linearProfile -> {
                findNavController().navigate(R.id.action_settingFragment_to_profileFragment)
            }
            R.id.linearLanguage -> {
                findNavController().navigate(R.id.action_settingFragment_to_languageFragment)
            }
            R.id.linearHistorical -> {
                findNavController().navigate(R.id.action_settingFragment_to_historicFragment)
            }
            R.id.linearQuestion -> {
                findNavController().navigate(R.id.action_settingFragment_to_chatFragment)
            }
            R.id.btnLogout -> {
                setErrorAlert(
                    errorMsg = getString(R.string.txt_msg_logout),
                    titleMsg = getString(R.string.txt_msg_title_logout),
                    positiveBtn = R.string.txt_btn_yes,
                    negativeBtn = R.string.txt_btn_no,
                    positiveClick = {
                        authenticationViewModel.logout()
                    })
            }
        }
    }

}
