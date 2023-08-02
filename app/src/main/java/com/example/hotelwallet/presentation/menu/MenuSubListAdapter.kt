package com.example.hotelwallet.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemSubMenuBinding
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.utility.KEY_PRODUCT_ADD_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DELETE_FAVORITE
import com.example.hotelwallet.utility.KEY_PRODUCT_DETAILS

class MenuSubListAdapter(
    private val menuList: List<SubMenu>,
    private val listener: (SubMenu, Int, Int) -> Unit
) : RecyclerView.Adapter<MenuSubListAdapter.SubMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubMenuViewHolder {
        val binding = RowItemSubMenuBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SubMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubMenuViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.txtSubMenuTitle.text = name
                binding.txtSubMenuDesc.text = description
                binding.txtSubMenuPrice.text =
                    holder.itemView.context.getString(R.string.txt_price_value).format(price)
                binding.imgMenu.load(image) {
                    placeholder(R.drawable.img_logo_default)
                }
                binding.imgSubMenuFavorite.setImageResource(
                    if (isFavorite) {
                        R.drawable.img_favorite_selected
                    } else {
                        R.drawable.img_favorite_default
                    }
                )
                itemView.setOnClickListener {
                    listener(this, position, KEY_PRODUCT_DETAILS)
                }
                binding.imgSubMenuFavorite.setOnClickListener {
                    if (isFavorite) {
                        listener(this, position, KEY_PRODUCT_DELETE_FAVORITE)
                    } else {
                        listener(this, position, KEY_PRODUCT_ADD_FAVORITE)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class SubMenuViewHolder(val binding: RowItemSubMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

}