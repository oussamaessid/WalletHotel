package com.example.hotelwallet.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentLoginBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.isValidEmail

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
), View.OnClickListener {

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            authenticationViewModel.stateLogin.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        appViewModel.getToken()
                        Log.d("***User :", "${it.data}")
                        findNavController().navigate(
                            R.id.action_loginFragment_to_homeFragment, null,
                            NavOptions.Builder()
                                .setPopUpTo(findNavController().graph.startDestinationId, true)
                                .build()
                        )
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setErrorAlert(errorMsg = it.message)
                        setLoading(false)
                    }
                }
            }

        }

        binding.btnConnected.setOnClickListener(this)
        binding.txtCreateAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnConnected -> {
                if (!isValidEmail(binding.editEmail.text?.trim().toString())) {
                    binding.texInputEmail.error = getString(R.string.txt_error_email)
                } else if (binding.editPassword.text?.trim().toString().isEmpty()) {
                    binding.texInputPassword.error = getString(R.string.txt_error_password)
                } else {
                    authenticationViewModel.getLogin(
                        email = binding.editEmail.text.toString(),
                        password = binding.editPassword.text.toString()
                    )
                }
            }
            R.id.txtCreateAccount -> {findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)}
        }
    }
}


