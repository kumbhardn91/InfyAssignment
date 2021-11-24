package com.kumbhar.infyassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumbhar.infyassignment.utils.checkForInternet
import com.kumbhar.infyassignment.utils.showToast
import com.kumbhar.infyassignment.R
import com.kumbhar.infyassignment.adapter.CountryModelAdapter
import com.kumbhar.infyassignment.databinding.FragmentCountryBinding
import com.kumbhar.infyassignment.model.DataRows
import com.kumbhar.infyassignment.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var fragmentCountryBinding: FragmentCountryBinding
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentCountryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.country_data)
        initialization()
        getCountryData()
        swipeToRefresh()
        return fragmentCountryBinding.root
    }

    // Get country data
    private fun getCountryData() {
        if (checkForInternet(requireActivity())) {
            if (!countryViewModel.isDataExist)
                countryViewModel.getCountryData()
        } else {
            showToast(activity, getString(R.string.no_internet))
        }
        observeCountryData()
    }

    // Observe updated data from  database
    private fun observeCountryData() {

        countryViewModel.countryUpdatedData.observe(requireActivity(), {
            if (it != null) {
                if (fragmentCountryBinding.swipeContainer.isRefreshing) {
                    fragmentCountryBinding.swipeContainer.isRefreshing = false
                }
                it.let {
                    val countryData = it
                    updateCountryAdapter(countryData)
                }
            } else {
                showToast(activity, getString(R.string.no_data))
            }
        })
    }

    // Update data with adapter
    private fun updateCountryAdapter(countryData: List<DataRows>) {
        val recyclerView = fragmentCountryBinding.recyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val adapter = CountryModelAdapter(countryData)
        recyclerView.adapter = adapter
    }

    // Refresh country data from api or local
    private fun refreshCountryData() {
        if (checkForInternet(requireActivity())) {
            countryViewModel.getCountryData()
        } else {
            observeCountryData()
            fragmentCountryBinding.swipeContainer.isRefreshing = false
            showToast(activity, getString(R.string.no_internet))
        }
    }

    companion object {
        fun newInstance(): CountryFragment {
            return CountryFragment()
        }
    }

    private fun initialization() {
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        manager = LinearLayoutManager(activity)
    }

    private fun swipeToRefresh() {
        fragmentCountryBinding.swipeContainer.setOnRefreshListener {
            refreshCountryData()
        }
        // Configure the refreshing colors
        fragmentCountryBinding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}