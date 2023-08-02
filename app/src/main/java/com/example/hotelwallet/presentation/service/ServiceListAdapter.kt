package com.example.hotelwallet.presentation.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemServiceBinding
import com.example.hotelwallet.domain.model.Services

class ServiceListAdapter(
    private val serviceList: List<Services>,
    private val listener: (Services) -> Unit
) : RecyclerView.Adapter<ServiceListAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = RowItemServiceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        with(holder) {
            with(serviceList[position]) {
                binding.txtServiceTitle.text = nom
                binding.imgService.load(image) {
                    placeholder(R.drawable.img_logo_default)
                }
                itemView.setOnClickListener {
                    listener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    inner class ServiceViewHolder(val binding: RowItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root)

}