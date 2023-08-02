package com.example.hotelwallet.presentation.misc

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.ActivityMainBinding
import com.example.hotelwallet.presentation.cart.CartViewModel
import com.example.hotelwallet.utility.Resource
import com.example.hotelwallet.utility.TAG_ALERT_DIALOG_ERROR
import com.example.hotelwallet.utility.removeBadge
import com.example.hotelwallet.utility.showBadge
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : LocaleAwareCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cartViewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cartViewModel.getProductList()
        observeCart()
    }

    fun setBottomNavigation(isNavigation: Boolean) {
        if (isNavigation) {
            binding.bottomNavigationView.visibility = View.VISIBLE
            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            val navController = Navigation.findNavController(this, R.id.mainNavHostContainer)

            NavigationUI.setupWithNavController(bottomNavigation, navController)
        } else {
            binding.bottomNavigationView.visibility = View.GONE

        }
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingView.root.visibility = View.VISIBLE
            binding.loadingView.animationLoading.playAnimation()
        } else {
            binding.loadingView.root.visibility = View.GONE
            binding.loadingView.animationLoading.cancelAnimation()
        }
    }

    fun setErrorAlert(
        errorMsg: String?,
        titleMsg: Any? = null,
        positiveBtn: Int,
        negativeBtn: Int? = null,
        positiveClick: ((View) -> Unit)? = null,
        negativeClick: ((View) -> Unit)? = null
    ) {
        this.supportFragmentManager.let { fragmentManager ->
            AlertDialogFragment().apply {
                titleId = titleMsg
                descriptionId = errorMsg
                positiveBtnName = positiveBtn
                negativeBtnName = negativeBtn
                positiveClickListener = positiveClick
                negativeClickListener = negativeClick
            }.show(fragmentManager, TAG_ALERT_DIALOG_ERROR)
        }
    }

    private fun observeCart() {
        lifecycleScope.launchWhenStarted {
            cartViewModel.stateProductList.observe(this@MainActivity) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            if (this.isNotEmpty()) {
                                var quantity = 0
                                this.forEach { product ->
                                    quantity += product.quantity
                                }
                                showBadge(
                                    applicationContext,
                                    binding.bottomNavigationView,
                                    R.id.cartFragment,
                                    "$quantity"
                                )
                            } else {
                                removeBadge(binding.bottomNavigationView, R.id.cartFragment)
                            }
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