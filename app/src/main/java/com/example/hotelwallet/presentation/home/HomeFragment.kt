package com.example.hotelwallet.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentHomeBinding
import com.example.hotelwallet.domain.model.Services
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.presentation.profile.ProfileViewModel
import com.example.hotelwallet.presentation.service.ServiceListAdapter
import com.example.hotelwallet.presentation.service.ServicesViewModel
import com.example.hotelwallet.utility.Resource
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        logoVisibility = View.VISIBLE
    )
) {

    private val serviceViewModel by activityViewModels<ServicesViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    private lateinit var serviceAdapter: ServiceListAdapter
    private var serviceList = mutableListOf<Services>()
    private val imageList = ArrayList<SlideModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(true)
        observeProfile()

        serviceAdapter = ServiceListAdapter(serviceList) { service ->
            val bundle = Bundle()
            bundle.putInt("service_id", service.id)
            bundle.putString("service_name", service.nom)
            when(service.id){
                2,3,4-> findNavController().navigate(R.id.action_homeFragment_to_gymFragment, bundle)
                else -> findNavController().navigate(R.id.action_homeFragment_to_menuFragment, bundle)
            }
        }

        binding.recyclerViewService.setHasFixedSize(true)
        binding.recyclerViewService.isNestedScrollingEnabled = false
        binding.recyclerViewService.adapter = serviceAdapter
        serviceViewModel.getServicesByHotelId(1)
        observeServices()

        imageList.clear()
        imageList.add(SlideModel(R.drawable.img_hotel1, "Amir Palace"))
        imageList.add(SlideModel(R.drawable.img_hotel2, "Amir Palace"))
        imageList.add(SlideModel(R.drawable.img_hotel3, "Amir Palace"))
        imageList.add(SlideModel(R.drawable.img_hotel4, "Amir Palace"))
        imageList.add(SlideModel(R.drawable.img_hotel5, "Amir Palace"))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                filterList(query)
                val bundle = Bundle()
                bundle.putString("query", query)
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                filterList(newText)
                return false
            }
        })

    }

    private fun observeServices() {
        lifecycleScope.launchWhenStarted {
            serviceViewModel.stateServices.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        setLoading(true)
                    }
                    is Resource.Success -> {
                        it.data.apply {
                            serviceList.clear()
                            serviceList.addAll(this)
                            serviceAdapter.notifyDataSetChanged()
                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                    }
                }
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
                            binding.txtUserName.text = getString(R.string.txt_hi).format(lastName)
                            binding.txtBalance.text =
                                getString(R.string.txt_your_balance).format(balance)
                            ToolbarConfiguration(
                                visibility = View.VISIBLE,
                                logoVisibility = View.VISIBLE,
                                rightImageConfig = photo,
                                rightImageClick = { findNavController().navigate(R.id.action_homeFragment_to_profileFragment) }
                            ).updateToolbarLayout()
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

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Services>()
            for (i in serviceList) {
                if (i.nom.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
//                serviceAdapter.setFilteredList(filteredList)
            }
        }
    }

}