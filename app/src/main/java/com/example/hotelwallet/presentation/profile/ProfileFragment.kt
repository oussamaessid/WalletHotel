package com.example.hotelwallet.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentProfileBinding
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.getMonthAndYearFromDate


class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_profile,
        rightImageConfig = R.drawable.img_edit
    )
) {

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)

        ToolbarConfiguration(
            visibility = View.VISIBLE,
            btnBackVisibility = View.VISIBLE,
            title = R.string.txt_title_profile,
            rightImageConfig = R.drawable.img_edit,
            rightImageClick = { findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment) }
        ).updateToolbarLayout()

        observeProfile()
    }

    private fun observeProfile() {
        lifecycleScope.launchWhenStarted {
            profileViewModel.stateProfile.collect {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            binding.txtLastName.text = lastName
                            binding.txtFirstName.text = firstName
                            binding.txtEmail.text = email
                            binding.txtMemberSince.text = getString(R.string.txt_member_since).format(createdAt.getMonthAndYearFromDate())
                            binding.imgUser.load(photo) {
                                placeholder(R.drawable.img_user_default)
                                transformations(CircleCropTransformation())
                            }
                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setErrorAlert(errorMsg =  it.message)
                        setLoading(false)
                    }
                    else -> Unit
                }
            }
        }
    }
}