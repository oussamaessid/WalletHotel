package com.example.hotelwallet.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemMenuBinding
import com.example.hotelwallet.domain.model.Menu

class MenuListAdapter(
    private val menuList: List<Menu>,
    private val listener: (Menu) -> Unit
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = RowItemMenuBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.txtMenuTitle.text = nom
                binding.imgMenu.load(description) {
                    placeholder(R.drawable.img_logo_default)
                }
                itemView.setOnClickListener {
                    listener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MenuViewHolder(val binding: RowItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

}