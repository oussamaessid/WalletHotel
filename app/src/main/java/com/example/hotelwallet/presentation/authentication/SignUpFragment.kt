package com.example.hotelwallet.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentSignUpBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.isValidEmail
import com.example.hotelwallet.utility.onTextChanged
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
), View.OnClickListener {

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editLastName.onTextChanged(binding.txtInputLastName)
        binding.editFirstName.onTextChanged(binding.txtInputFirstName)
        binding.editEmail.onTextChanged(binding.txtInputEmail)
        binding.editPassword.onTextChanged(binding.txtInputPassword)
        binding.editConfirmPassword.onTextChanged(binding.txtInputConfirmPassword)

        observeRegister()

        binding.btnBack.setOnClickListener(this)
        binding.btnRegistration.setOnClickListener(this)
        binding.txtHaveAccount.setOnClickListener(this)
    }

    private fun observeRegister(){
        authenticationViewModel.stateRegister.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->setLoading(true)
                is Resource.Success ->{
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    setLoading(false)
                }
                is Resource.Error ->{
                    setErrorAlert(errorMsg =  it.message)
                    setLoading(false)
                }
                else -> Unit
            }
        }
    }

    private fun FragmentSignUpBinding.checkForm():Boolean{
        when {
            editLastName.text?.trim().toString().isEmpty() -> {
                txtInputLastName.error = getString(R.string.txt_error_last_name)
                return false
            }
            editFirstName.text?.trim().toString().isEmpty() -> {
                txtInputFirstName.error = getString(R.string.txt_error_first_name)
                return false
            }
            !isValidEmail(editEmail.text?.trim().toString()) -> {
                txtInputEmail.error = getString(R.string.txt_error_email)
                return false
            }
            editPassword.text?.trim().toString().isEmpty() -> {
                txtInputPassword.error = getString(R.string.txt_error_password)
                return false
            }
            editConfirmPassword.text?.trim().toString().isEmpty() ||
                    editConfirmPassword.text?.trim().toString() != binding.editPassword.text?.trim().toString() -> {
                txtInputConfirmPassword.error = getString(R.string.txt_error_confirm_password)
                return false
            }
            else -> return true
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgBack -> onBack()
            R.id.btnRegistration -> {
                binding.apply {
                    if (checkForm()) {
                        authenticationViewModel.register(
                            name = editFirstName.text.toString(),
                            prenom = editLastName.text.toString(),
                            email = editEmail.text.toString(),
                            password = editPassword.text.toString()
                        )
                    }
                }
            }
            R.id.txtHaveAccount-> {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }

}