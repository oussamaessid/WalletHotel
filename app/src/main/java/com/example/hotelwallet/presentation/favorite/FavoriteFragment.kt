package com.example.hotelwallet.presentation.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentFavoriteBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS
import com.example.hotelwallet.utility.Resource

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.GONE,
        title = R.string.txt_title_favorite
    )
) {

    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()

    private lateinit var favoriteAdapter: FavoriteAdapter
    private var productList = mutableListOf<SubMenu>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter(productList) { product, type ->
            when (type) {
                KEY_PRODUCT_DETAILS -> {

                }
                KEY_PRODUCT_DELETE_FAVORITE -> {
                    favoriteViewModel.deleteFavorite(product)
                }
            }
        }

        binding.recyclerViewFavorite.setHasFixedSize(true)
        binding.recyclerViewFavorite.isNestedScrollingEnabled = false
        binding.recyclerViewFavorite.adapter = favoriteAdapter

        favoriteViewModel.getFavoriteList()
        observeFavorite()
        observeDeleteFromFavorite()

        setBottomNavigation(true)
    }

    private fun observeFavorite() {
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateFavoriteList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            productList.clear()
                            this?.let { list ->
                                productList.addAll(list)
                                favoriteAdapter.notifyDataSetChanged()
                            }
                            binding.txtNoItems.isVisible = this.isNullOrEmpty()
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

    private fun observeDeleteFromFavorite() {
        lifecycleScope.launchWhenStarted {
            favoriteViewModel.stateDeleteFavorite.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    else -> {
                        favoriteViewModel.getFavoriteList()
                        setLoading(false)
                    }
                }
            }
        }
    }
}