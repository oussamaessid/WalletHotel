package com.example.hotelwallet.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemCartBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_CART
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS

class CartAdapter(
    private val menuList: List<SubMenu>,
    private val listener: (SubMenu, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = RowItemCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.txtTitle.text = name
                binding.imgProduct.load(image) {
                    placeholder(R.drawable.img_logo_default)
                    transformations(CircleCropTransformation())
                }
                binding.txtQuantity.text =
                    "${itemView.context.getString(R.string.txt_quantity_value).format(quantity)} " +
                            "(${
                                itemView.context.getString(R.string.txt_price_value)
                                    .format(quantity * price.toFloat())
                            })"
                binding.txtPrice.text = itemView.context.getString(R.string.txt_price_menu)
                    .format(price.toFloat())
                itemView.setOnClickListener {
                    listener(this, KEY_PRODUCT_DETAILS)
                }
                binding.imgDelete.setOnClickListener {
                    listener(this, KEY_PRODUCT_DELETE_CART)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class CartViewHolder(val binding: RowItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

}