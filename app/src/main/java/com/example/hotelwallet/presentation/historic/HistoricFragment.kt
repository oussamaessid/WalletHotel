package com.example.hotelwallet.presentation.historic

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentHistoricBinding
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource

class HistoricFragment : BaseFragment<FragmentHistoricBinding>(
    FragmentHistoricBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_historic
    )
) {

    private val orderViewModel by activityViewModels<HistoricViewModel>()

    private lateinit var historicAdapter: HistoricAdapter
    private var orderList = mutableListOf<Order>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historicAdapter = HistoricAdapter(orderList) { order ->

        }

        binding.recyclerViewHistoric.setHasFixedSize(true)
        binding.recyclerViewHistoric.isNestedScrollingEnabled = false
        binding.recyclerViewHistoric.adapter = historicAdapter

        orderViewModel.getOrderList()
        observeOrder()

        setBottomNavigation(false)
    }

    private fun observeOrder() {
        lifecycleScope.launchWhenStarted {
            orderViewModel.stateOrderList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        it.data.apply {
                            orderList.clear()
                            this?.let { list ->
                                orderList.addAll(list)
                                historicAdapter.notifyDataSetChanged()
                            }
                            binding.txtNoItems.isVisible = this.isNullOrEmpty()
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

}