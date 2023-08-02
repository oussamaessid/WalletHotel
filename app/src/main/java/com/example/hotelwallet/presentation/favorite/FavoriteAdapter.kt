package com.example.hotelwallet.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemCartBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS

class FavoriteAdapter(
    private val menuList: List<SubMenu>,
    private val listener: (SubMenu, Int) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = RowItemCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.txtTitle.text = name
                binding.imgProduct.load(image) {
                    placeholder(R.drawable.img_logo_default)
                    transformations(CircleCropTransformation())
                }
                binding.txtQuantity.visibility = View.GONE
                binding.txtPrice.text = itemView.context.getString(R.string.txt_price_menu).format(price.toFloat())
                itemView.setOnClickListener {
                    listener(this, KEY_PRODUCT_DETAILS)
                }
                binding.imgDelete.setOnClickListener {
                    listener(this, KEY_PRODUCT_DELETE_FAVORITE)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class FavoriteViewHolder(val binding: RowItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

}