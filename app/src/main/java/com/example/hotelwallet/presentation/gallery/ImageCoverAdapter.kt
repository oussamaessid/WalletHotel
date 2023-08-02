package com.example.hotelwallet.presentation.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemImageCoverBinding
import com.example.hotelwallet.domain.model.Image

class ImageCoverAdapter(
    private val coverImageList: MutableList<Image>,
    private val listener: (Image) -> Unit
) : RecyclerView.Adapter<ImageCoverAdapter.CoverImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoverImageViewHolder {
        val binding = RowItemImageCoverBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CoverImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoverImageViewHolder, position: Int) {
        with(holder) {
            with(coverImageList[position]) {
                binding.imgCover.load(imagePath) {
                    placeholder(R.drawable.img_logo_default)
                }
                itemView.setOnClickListener {
                    listener(this)
                }
            }
        }
    }

    override fun getItemCount() = coverImageList.size

    inner class CoverImageViewHolder(val binding: RowItemImageCoverBinding) :
        RecyclerView.ViewHolder(binding.root)
}