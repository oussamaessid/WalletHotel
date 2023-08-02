package com.example.hotelwallet.presentation.search

import android.os.Bundle
import android.view.View
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentSearchBinding
import com.example.hotelwallet.domain.model.Service
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.presentation.service.ServiceListAdapter
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_service
    )
) {
    private lateinit var serviceListAdapter: ServiceListAdapter
    private var menuList = mutableListOf<Service>()
    private lateinit var query : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getMealInformationFromIntent()


        menuList.clear()
//        homeAdapter = HomeAdapter(menuList) { type ->
//            if (type.type == CATEGORY_EAT) {
//                findNavController().navigate(R.id.action_homeFragment_to_menuFragment)
//            } else {
//                findNavController().navigate(R.id.action_homeFragment_to_gymFragment)
//
//            }
//        }

        binding.recyclerViewService.setHasFixedSize(true)
        binding.recyclerViewService.isNestedScrollingEnabled = false
        setSlideList()
        binding.recyclerViewService.adapter = serviceListAdapter

//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                filterList(query)
//                return false
//            }
//        })

    }

//   private fun filterList(query: String?) {
//
//    if (query != null) {
//        val filteredList = java.util.ArrayList<Service>()
//        for (i in menuList) {
//            if (i.title.lowercase(Locale.ROOT).contains(query)) {
//                filteredList.add(i)
//            }
//        }
//
//        if (filteredList.isEmpty()) {
//            Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
//        } else {
//            homeAdapter.setFilteredList(filteredList)
//        }
//    }
//}
    private fun setSlideList() {
        menuList.add(
            Service(
                R.drawable.img_pizza,
                "Restaurant",
                1
            )
        )
        menuList.add(
            Service(
                R.drawable.img_pizza,
                "Salle De Sport",
                2
            )
        )
        menuList.add(
            Service(
                R.drawable.img_pizza,
                "Restaurant",
                1
            )
        )
        menuList.add(
            Service(
                R.drawable.img_pizza,
                "Salle De Sport ",
                2
            )
        )
        menuList.add(
            Service(
                R.drawable.img_pizza,
                "Salle De Sport ",
                2
            )
        )
    }

    private fun getMealInformationFromIntent() {
        val args = this.arguments
        query = args?.get("query").toString()
    }
}