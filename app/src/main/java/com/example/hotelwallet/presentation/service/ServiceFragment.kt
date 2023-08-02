package com.example.hotelwallet.presentation.service

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentServiceBinding
import com.example.hotelwallet.domain.model.Services
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource

class ServiceFragment : BaseFragment<FragmentServiceBinding>(
    FragmentServiceBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.GONE,
        title = R.string.txt_title_service
    )
) {
    private val serviceViewModel by activityViewModels<ServicesViewModel>()

    private lateinit var serviceListAdapter: ServiceListAdapter
    private var serviceList = mutableListOf<Services>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeServices()

        serviceListAdapter = ServiceListAdapter(serviceList) { service ->
            val bundle = Bundle()
            bundle.putInt("service_id", service.id)
            bundle.putString("service_name", service.nom)
            findNavController().navigate(R.id.action_serviceFragment_to_menuFragment, bundle)
        }
        binding.recyclerViewService.setHasFixedSize(true)
        binding.recyclerViewService.isNestedScrollingEnabled = false
        binding.recyclerViewService.adapter = serviceListAdapter
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
                            serviceListAdapter.notifyDataSetChanged()
                            binding.txtNoItems.isVisible = this.isEmpty()
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
}