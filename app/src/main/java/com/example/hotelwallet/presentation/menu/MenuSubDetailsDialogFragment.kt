package com.example.hotelwallet.presentation.menu

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.DialogFragmentMenuSubDetailsBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.presentation.misc.BaseDialogFragment
import com.example.hotelwallet.utility.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuSubDetailsDialogFragment : BaseDialogFragment<DialogFragmentMenuSubDetailsBinding>(
    DialogFragmentMenuSubDetailsBinding::inflate
), View.OnClickListener {
    private val menuViewModel by activityViewModels<MenuViewModel>()

    private var menuId: Int? = null
    private var position: Int? = null
    private lateinit var menuSubDetails: SubMenu
    private var quantity = 1
    private var priceValue = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuId = arguments?.let {
            MenuSubDetailsDialogFragmentArgs.fromBundle(it).menuId
        }

        position = arguments?.let {
            MenuSubDetailsDialogFragmentArgs.fromBundle(it).position
        }

        menuId?.let { id -> menuViewModel.getSubMenuList(id) }

        observeSubMenuDetails()
        observeInsertProduct()

        binding.imgClose.setOnClickListener(this)
        binding.txtMore.setOnClickListener(this)
        binding.txtLess.setOnClickListener(this)
        binding.btnAddToCard.setOnClickListener(this)
    }

    private fun setQuantity() {
        binding.editQuantity.setText("$quantity")
        binding.txtTotal.text =
            getString(R.string.txt_price_value).format(priceValue.toFloat() * quantity)
    }

    private fun observeSubMenuDetails() {
        lifecycleScope.launchWhenStarted {
            menuViewModel.stateSubMenuList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data[position ?: 0].apply {
                            menuSubDetails = this
                            binding.txtSubMenuTitle.text = name
                            binding.txtSubMenuDesc.text = description
                            binding.txtSubMenuCategory.text = category
                            binding.txtPrice.text =
                                getString(R.string.txt_price_menu).format(price.toFloat())
                            priceValue = price
                            binding.imgMenu.load(image) {
                                placeholder(R.drawable.img_logo_default)
                            }

                            setQuantity()
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

    private fun observeInsertProduct(){
        lifecycleScope.launchWhenStarted {
            menuViewModel.stateInsertProduct.observe(viewLifecycleOwner){
                when(it){
                    is  Resource.Loading -> setLoading(true)
                    else -> {
                        dialog?.dismiss()
                        setLoading(false)
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgClose -> dialog?.dismiss()
            R.id.txtMore -> {
                quantity++
                setQuantity()
            }

            R.id.txtLess -> {
                if (quantity > 1) {
                    quantity--
                    setQuantity()
                }
            }

            R.id.btnAddToCard -> {
                menuViewModel.insertProduct(menuSubDetails.copy(quantity = quantity))
                menuViewModel.getSubMenuList(menuId?:0)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}