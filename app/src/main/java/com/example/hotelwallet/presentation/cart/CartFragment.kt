package com.example.hotelwallet.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentCartBinding
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.menu.MenuViewModel
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_CART
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(
    FragmentCartBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.GONE,
        title = R.string.txt_title_cart
    )
) {

    private val cartViewModel by activityViewModels<CartViewModel>()
    private val menuViewModel by activityViewModels<MenuViewModel>()

    private lateinit var cartAdapter: CartAdapter
    private var productList = mutableListOf<SubMenu>()
    var total = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(productList) {product, type->
            when(type) {
                KEY_PRODUCT_DETAILS -> {

                }
                KEY_PRODUCT_DELETE_CART -> {
                    menuViewModel.deleteProduct(product)
                }
            }
        }

        binding.recyclerViewCart.setHasFixedSize(true)
        binding.recyclerViewCart.isNestedScrollingEnabled = false
        binding.recyclerViewCart.adapter = cartAdapter

        cartViewModel.getProductList()
        observeCart()
        observeDeleteFromCart()
        observeAddOrder()
        setBottomNavigation(true)

        binding.btnConfirm.setOnClickListener{
            cartViewModel.addProductToOrder(Order(createdAt = Date().time, totalPrice = total, category = 1, platList = productList))
        }
    }

    private fun observeCart() {
        lifecycleScope.launchWhenStarted {
            cartViewModel.stateProductList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            productList.clear()
                            productList.addAll(this)
                            cartAdapter.notifyDataSetChanged()
                            binding.txtNoItems.isVisible = this.isEmpty()

                            productList?.forEach {product->
                                total += product.quantity * (product.price.toFloat())
                            }
                            binding.txtTotal.text = getString(R.string.txt_price_value).format(total)
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

    private fun observeDeleteFromCart(){
        lifecycleScope.launchWhenStarted {
            menuViewModel.stateDeleteProduct.observe(viewLifecycleOwner){
                when(it){
                    is  Resource.Loading -> setLoading(true)
                    else -> {
                        cartViewModel.getProductList()
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun observeAddOrder(){
        lifecycleScope.launchWhenStarted {
            cartViewModel.stateAddOrder.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading -> setLoading(true)
                    else -> {
                        cartViewModel.getProductList()
                        setLoading(false)
                    }
                }
            }
        }
    }

}