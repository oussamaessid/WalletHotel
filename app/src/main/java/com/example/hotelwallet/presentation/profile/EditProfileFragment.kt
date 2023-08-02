package com.example.hotelwallet.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentEditProfileBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.domain.model.User
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_edit_profile,
    )
) {

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private lateinit var currentProfile: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)

        observeProfile()
        observeUpdateProfile()

        binding.btnSave.setOnClickListener {
            with(binding) {
                //TODO ws update profile
            }
        }
    }

    private fun observeProfile() {
        lifecycleScope.launchWhenStarted {
            profileViewModel.stateProfile.collect {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            currentProfile = this
                            binding.editLastName.setText(lastName)
                            binding.editFirstName.setText(firstName)
                            binding.editEmail.setText(email)
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

    private fun observeUpdateProfile() {
        lifecycleScope.launchWhenStarted {
            profileViewModel.stateUpdateProfile.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            //TODO ws update profile
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
}