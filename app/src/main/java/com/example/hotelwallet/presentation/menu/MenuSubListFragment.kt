package com.example.hotelwallet.presentation.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.databinding.FragmentMenuSubListBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.favorite.FavoriteViewModel
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.KEY_PRODUCT_ADD_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuSubListFragment : BaseFragment<FragmentMenuSubListBinding>(
    FragmentMenuSubListBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
) {
    private val menuViewModel by activityViewModels<MenuViewModel>()
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()

    private lateinit var menuSubListAdapter: MenuSubListAdapter
    private var menuSubList = mutableListOf<SubMenu>()

    private var menuId: Int? = null
    private var menuName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuId = arguments?.let {
            MenuSubListFragmentArgs.fromBundle(it).menuId
        }
        menuName = arguments?.let {
            MenuSubListFragmentArgs.fromBundle(it).menuName
        }

        ToolbarConfiguration(
            visibility = View.VISIBLE,
            btnBackVisibility = View.VISIBLE,
            title = menuName
        ).updateToolbarLayout()

        menuSubListAdapter = MenuSubListAdapter(menuSubList) { product, position, type->
            when(type){
                KEY_PRODUCT_DETAILS -> {
                    val bundle = Bundle()
                    bundle.putInt("menu_id", menuId?:0)
                    bundle.putInt("position", position?:0)
                    requireActivity().supportFragmentManager.let { fragmentManager ->
                        val menuPopUp = MenuSubDetailsDialogFragment()
                        menuPopUp.arguments = bundle
                        menuPopUp.show(fragmentManager, "")
                    }
                }
                KEY_PRODUCT_DELETE_FAVORITE -> {
                    favoriteViewModel.deleteFavorite(product)
                }
                KEY_PRODUCT_ADD_FAVORITE -> {
                    favoriteViewModel.insertFavorite(product)
                }
            }
        }

        binding.recyclerViewSubMenu.setHasFixedSize(true)
        binding.recyclerViewSubMenu.isNestedScrollingEnabled = false
        binding.recyclerViewSubMenu.adapter = menuSubListAdapter

        menuId?.let { id-> menuViewModel.getSubMenuList(id) }

        observeSubMenus()
        observeInsertFavorite()
        observeDeleteFavorite()
        setBottomNavigation(false)
    }

    private fun observeSubMenus() {
        lifecycleScope.launchWhenStarted {
            menuViewModel.stateSubMenuList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            menuSubList.clear()
                            menuSubList.addAll(this)
                            menuSubList.forEach {
                                Log.d("***Product :" , "$it")
                            }
                            menuSubListAdapter.notifyDataSetChanged()
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

    private fun observeInsertFavorite(){
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateInsertFavorite.observe(viewLifecycleOwner){
                when(it){
                    is  Resource.Loading -> setLoading(true)
                    else -> {
                        menuViewModel.getSubMenuList(menuId?:0)
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun observeDeleteFavorite(){
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateDeleteFavorite.observe(viewLifecycleOwner){
                when(it){
                    is  Resource.Loading -> setLoading(true)
                    else -> {
                        menuViewModel.getSubMenuList(menuId?:0)
                        setLoading(false)
                    }
                }
            }
        }
    }
}