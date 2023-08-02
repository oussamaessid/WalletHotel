package com.example.hotelwallet.presentation.historic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemHistoricBinding
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.utility.getTimeAgo

class HistoricAdapter(
    private val menuList: List<Order>,
    private val listener: (Order) -> Unit
) : RecyclerView.Adapter<HistoricAdapter.HistoricViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricViewHolder {
        val binding = RowItemHistoricBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {
                binding.txtTitle.text =
                    itemView.context.getString(R.string.txt_order_value).format(createdAt)
                binding.txtPrice.text =
                    itemView.context.getString(R.string.txt_price_menu).format(totalPrice)
                binding.txtQuantity.text = itemView.context.getString(R.string.txt_quantity_value)
                    .format(platList.size + planList.size)
                binding.txtDate.text = itemView.context.getString(R.string.txt_date_value)
                    .format(createdAt.getTimeAgo(itemView.context))

                binding.txtCategory.text = if (platList.isEmpty()){
                    if (category == 2){
                        itemView.context.getString(R.string.txt_gym)
                    }else if(category == 3){
                        itemView.context.getString(R.string.txt_swimming_pool)
                    }else{
                        itemView.context.getString(R.string.txt_event)
                    }
                }else{
                    itemView.context.getString(R.string.txt_restaurant)
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

    inner class HistoricViewHolder(val binding: RowItemHistoricBinding) :
        RecyclerView.ViewHolder(binding.root)

}