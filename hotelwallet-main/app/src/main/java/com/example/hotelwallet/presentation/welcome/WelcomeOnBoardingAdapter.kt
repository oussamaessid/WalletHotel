package com.example.hotelwallet.presentation.welcome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import com.example.hotelwallet.databinding.RowItemWelcomeSlideBinding
import com.example.hotelwallet.domain.model.WelcomeSlide


class WelcomeOnBoardingAdapter(private val slideList: MutableList<WelcomeSlide>) : PagerAdapter() {

    override fun getCount(): Int = slideList.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            RowItemWelcomeSlideBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
        with(slideList[position]) {
            with(slideList[position]) {
                binding.imgSlide.load(image)
            }
            binding.txtTitleSlide.text = title
            binding.txtDesSlide.text = description
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}




