package com.example.hotelwallet.presentation.gym

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentGymDetailsBinding
import com.example.hotelwallet.domain.model.Image
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.Plan
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.cart.CartViewModel
import com.example.hotelwallet.presentation.gallery.ImageCoverAdapter
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource
import java.util.*

class GymDetailsFragment : BaseFragment<FragmentGymDetailsBinding>(
    FragmentGymDetailsBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration()
), View.OnClickListener {
    private val gymViewModel by activityViewModels<GymViewModel>()
    private var position: Int? = null
    private var serviceId: Int? = null
    private var gymName: String? = null
    private var priceValue = "0"
    private var imageList = mutableListOf<Image>()
    private lateinit var imageAdapter: ImageCoverAdapter
    private lateinit var planDetails: Plan

    private val cartViewModel by activityViewModels<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation(false)

        serviceId = arguments?.let {
            GymDetailsFragmentArgs.fromBundle(it).serviceId
        }

        position = arguments?.let {
            GymDetailsFragmentArgs.fromBundle(it).position
        }

        gymName = arguments?.let {
            GymDetailsFragmentArgs.fromBundle(it).gymName
        }

        ToolbarConfiguration(
            visibility = View.VISIBLE,
            btnBackVisibility = View.VISIBLE,
            title = gymName
        ).updateToolbarLayout()

        gymViewModel.getGymList(serviceId ?: 0)

        imageAdapter = ImageCoverAdapter(imageList) { image ->
            //TODO
        }

        binding.recyclerViewGallery.setHasFixedSize(true)
        binding.recyclerViewGallery.isNestedScrollingEnabled = false
        binding.recyclerViewGallery.adapter = imageAdapter

        binding.priceGroup.setOnCheckedChangeListener { _, _ -> setPrice() }
        observeGymDetails()
        observeAddOrder()

        binding.btnAddToCard.setOnClickListener(this)
    }

    private fun setPrice() {
        binding.txtTotal.text = when (binding.priceGroup.checkedRadioButtonId) {
            R.id.radioTwoDay -> getString(R.string.txt_price_value).format(priceValue.toFloat() * 2)
            R.id.radioThreeDay -> getString(R.string.txt_price_value).format(priceValue.toFloat() * 3)
            R.id.radioFourDay -> getString(R.string.txt_price_value).format(priceValue.toFloat() * 4)
            R.id.radioFiveDay -> getString(R.string.txt_price_value).format(priceValue.toFloat() * 5)
            else -> getString(R.string.txt_price_value).format(priceValue.toFloat())
        }
    }

    private fun observeGymDetails() {
        lifecycleScope.launchWhenStarted {
            gymViewModel.stateGyms.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            this[position ?: 0].apply {
                                planDetails = this
                                if (serviceId == 4){
                                    binding.txtTime.text = getString(R.string.txt_every_friday)
                                }else{
                                    binding.txtTime.text = getString(R.string.txt_times)
                                }
                                binding.imgGym.load(image) {
                                    placeholder(R.drawable.img_logo_default)
                                }
                                binding.txtGymTitle.text = name
                                binding.txtDesc.text = description

                                binding.radioOneDay.text = "${if (serviceId ==4)getString(R.string.txt_one_person)else getString(R.string.txt_one_day)} ${
                                    getString(R.string.txt_price_value).format(price)
                                }"
                                binding.radioTwoDay.text = "${if (serviceId ==4)getString(R.string.txt_two_person)else getString(R.string.txt_two_day)} ${
                                    getString(R.string.txt_price_value).format(price.toFloat() * 2)
                                }"
                                binding.radioThreeDay.text =
                                    "${if (serviceId ==4)getString(R.string.txt_three_person)else getString(R.string.txt_three_day)} ${
                                        getString(R.string.txt_price_value).format(price.toFloat() * 3)
                                    }"
                                binding.radioFourDay.text = "${if (serviceId ==4)getString(R.string.txt_four_person)else getString(R.string.txt_four_day)} ${
                                    getString(R.string.txt_price_value).format(price.toFloat() * 4)
                                }"
                                binding.radioFiveDay.text = "${if (serviceId ==4)getString(R.string.txt_five_person)else getString(R.string.txt_five_day)} ${
                                    getString(R.string.txt_price_value).format(price.toFloat() * 5)
                                }"
                                binding.txtGallery.isVisible = images.isNotEmpty()

                                imageList.clear()
                                imageList.addAll(images)
                                imageAdapter.notifyDataSetChanged()

                                priceValue = price

                                setPrice()
                            }

                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun observeAddOrder() {
        lifecycleScope.launchWhenStarted {
            cartViewModel.stateAddOrder.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    else -> {
                        cartViewModel.getProductList()
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun String.getTotal(): Double{
        val total = this.replace(" DT", "")
        return total.toDouble()
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAddToCard -> {
                cartViewModel.addProductToOrder(
                    Order(
                        createdAt = Date().time,
                        totalPrice = binding.txtTotal.text.toString().getTotal(),
                        category = serviceId?:2,
                        planList = listOf(planDetails)
                    )
                )
            }
        }
    }
}

