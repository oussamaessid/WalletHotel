package com.example.hotelwallet.presentation.gym

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemGymBinding
import com.example.hotelwallet.domain.model.Plan

class GymAdapter(
    private val popularList: List<Plan>,
    private val listener: (Plan, Int) -> Unit
) : RecyclerView.Adapter<GymAdapter.GymViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val binding = RowItemGymBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GymViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        with(holder) {
            with(popularList[position]) {
                binding.imgGym.load(image) {
                    placeholder(R.drawable.img_logo_default)
                }

                binding.txtTitle.text = name
                binding.txtDescription.text = description
                binding.txtPrice.text =
                    itemView.context.getString(R.string.txt_session).format(
                        itemView.context.getString(R.string.txt_price_value).format(price)
                    )

                itemView.setOnClickListener {
                    listener(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    inner class GymViewHolder(val binding: RowItemGymBinding) :
        RecyclerView.ViewHolder(binding.root)

}