package com.example.hotelwallet.presentation.gym

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentGymBinding
import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.menu.MenuListFragmentArgs
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource

class GymFragment : BaseFragment<FragmentGymBinding>(
    FragmentGymBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
) {

    private var gymList = mutableListOf<Plan>()
    private lateinit var gymAdapter: GymAdapter
    private val gymViewModel by activityViewModels<GymViewModel>()
    private var serviceId: Int? = null
    private var serviceName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)

        serviceId = arguments?.let {
            GymFragmentArgs.fromBundle(it).serviceId
        }

        serviceName = arguments?.let {
            MenuListFragmentArgs.fromBundle(it).serviceName
        }

        ToolbarConfiguration(
            visibility = View.VISIBLE,
            btnBackVisibility = View.VISIBLE,
            title = serviceName
        ).updateToolbarLayout()

        gymAdapter = GymAdapter(gymList) { product, pos ->
            val bundle = Bundle()
            bundle.putInt("service_id", serviceId?:0)
            bundle.putInt("position", pos)
            bundle.putString("gym_name", product.name)
            findNavController().navigate(R.id.action_gymFragment_to_gymDetailsFragment, bundle)
        }

        binding.recyclerViewGym.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewGym.setHasFixedSize(true)
        binding.recyclerViewGym.isNestedScrollingEnabled = false
        binding.recyclerViewGym.adapter = gymAdapter

        gymViewModel.getGymList(serviceId ?: 0)

        observeGymList()
    }

    private fun observeGymList() {
        lifecycleScope.launchWhenStarted {
            gymViewModel.stateGyms.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            gymList.clear()
                            gymList.addAll(this)
                            gymAdapter.notifyDataSetChanged()
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

