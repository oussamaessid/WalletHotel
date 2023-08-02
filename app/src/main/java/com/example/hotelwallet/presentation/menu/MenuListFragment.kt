package com.example.hotelwallet.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentMenuListBinding
import com.example.hotelwallet.domain.model.Menu
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuListFragment : BaseFragment<FragmentMenuListBinding>(
    FragmentMenuListBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
) {
    private val favoriteViewModel by activityViewModels<MenuViewModel>()

    private lateinit var menuListAdapter: MenuListAdapter
    private var menuList = mutableListOf<Menu>()

    private var serviceId: Int? = null
    private var serviceName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serviceId = arguments?.let {
            MenuListFragmentArgs.fromBundle(it).serviceId
        }
        serviceName = arguments?.let {
            MenuListFragmentArgs.fromBundle(it).serviceName
        }

        ToolbarConfiguration(
            visibility = View.VISIBLE,
            btnBackVisibility = View.VISIBLE,
            title = serviceName
        ).updateToolbarLayout()

        menuListAdapter = MenuListAdapter(menuList) {
            val bundle = Bundle()
            bundle.putInt("menu_id", it.id)
            bundle.putString("menu_name", it.nom)
            findNavController().navigate(R.id.action_menuFragment_to_menuSubListFragment, bundle)
        }

        binding.recyclerViewMenu.setHasFixedSize(true)
        binding.recyclerViewMenu.isNestedScrollingEnabled = false
        binding.recyclerViewMenu.adapter = menuListAdapter

        serviceId?.let { id -> favoriteViewModel.getMenuList(id) }
        observeMenus()

        setBottomNavigation(true)
    }

    private fun observeMenus() {
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateMenuList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            menuList.clear()
                            menuList.addAll(this)
                            menuListAdapter.notifyDataSetChanged()
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