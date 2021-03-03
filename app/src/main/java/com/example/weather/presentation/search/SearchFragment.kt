package com.example.weather.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.domain.model.City
import com.example.weather.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class SearchFragment : BaseFragment(), KoinComponent, SearchListAdapter.ClickListener {
    override fun getLayout(): Int = R.layout.fragment_search

    val viewModel: SearchViewModel by inject()
    lateinit var binding: FragmentSearchBinding
    lateinit var searchListAdapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cities.observe(this, Observer {
            viewModel.setLoading(false)
            if(!it.hasErrors()) {
                it.data?.let { cityList ->
                    initSearchAdapter(cityList)
                }
            }
        })
        viewModel.isDataLoading.observe(this, Observer {
            binding.pageContentGroup.visibility = if (it == true) View.GONE else View.VISIBLE
            binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureSearchView(binding.searchView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    private fun configureSearchView(searchView: AppCompatEditText) {
        searchView.doOnTextChanged { text, start, before, count ->
            text?.let {
                viewModel.getCities(text.toString())
            }
        }
    }

    private fun initSearchAdapter(list: List<City> = arrayListOf()) {
        searchListAdapter = SearchListAdapter(this)
        searchListAdapter.items = ArrayList(list)
        search_list.adapter = searchListAdapter
        search_list.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCitySelected(city: City) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToHomeFragment(city)
        )
    }
}