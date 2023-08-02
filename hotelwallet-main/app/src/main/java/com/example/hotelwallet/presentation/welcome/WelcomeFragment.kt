package com.example.hotelwallet.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentWelcomeBinding
import com.example.hotelwallet.domain.model.WelcomeSlide
import com.example.hotelwallet.presentation.misc.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
), View.OnClickListener {

    private lateinit var slideAdapter: WelcomeOnBoardingAdapter
    private var slideList = mutableListOf<WelcomeSlide>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSlideList()

        binding.btnNext.setOnClickListener(this)
        binding.btnSkip.setOnClickListener(this)
    }

    private fun setSlideList() {
        slideList.add(
            WelcomeSlide(
                R.drawable.bacground4,
                "Work at Home",
                "Work at home to more comfort. Make a great projects with this app"
            )
        )
        slideList.add(
            WelcomeSlide(
                R.drawable.bacground4,
                "Analyse Your Project",
                "Smart details for analysis. Do more with this app"
            )
        )
        slideList.add(
            WelcomeSlide(
                R.drawable.bacground4,
                "Achieve Your Goals",
                "Achieve your goals more easily. This app will help with that"
            )
        )

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if (position == slideList.size - 1) {
                    binding.onBoardingMotion.transitionToEnd()
                } else {
                    binding.onBoardingMotion.transitionToStart()
                }
            }

        })
        slideAdapter = WelcomeOnBoardingAdapter(slideList)
        binding.viewPager.adapter = slideAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnNext -> {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
            R.id.btnSkip -> {
                findNavController().navigate(R.id.action_welcomeFragment_to_scannerFragment)
            }
        }
    }
}