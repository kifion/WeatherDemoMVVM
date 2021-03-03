package com.example.weather.presentation.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.weather.Constants.Companion.DAYS_NUMBER
import com.example.weather.R
import com.example.weather.data.SharedPreferencesUtil
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.DayWeather
import com.example.weather.presentation.base.BaseFragment
import com.example.weather.presentation.home.adapter.CityPagerAdapter
import com.example.weather.presentation.home.adapter.DayListAdapter
import com.example.weather.presentation.home.adapter.HourlyListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment(), DayListAdapter.ClickListener, CityViewPagerFragment.Callback,
    SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout() = R.layout.fragment_home

    val viewModel: HomeViewModel by inject()
    lateinit var binding: FragmentHomeBinding
    var dayListAdapter: DayListAdapter? = null
    var hourlyListAdapter: HourlyListAdapter? = null
    var pagerAdapter: CityPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.actionSearchButtonClicked.observe(this, Observer {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        })
        viewModel.update.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            updateUi()
            viewModel.setLoading(false)
        })
        viewModel.getData.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.setLoading(false)
            if(it == null) {
                updateUi()
                setCityPagerCurrentPage(viewModel.getCityList().size - 1)
            } else {
                Toast.makeText(context, "Connection problem", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isDataLoading.observe(this, Observer {
            binding.pageContentGroup.visibility = if (it == true) View.GONE else View.VISIBLE
            binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
        arguments?.let {
            HomeFragmentArgs.fromBundle(it).city?.let { city ->
                viewModel.getCityDetailsByCity(city.cityId)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.initData()
        updateUi()
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.lifecycleOwner = this
        binding.weatherPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                viewModel.setSelectCity(viewModel.getCityList()[position])
                setHeaderImage(viewModel.getCityList()[position])
                initDaysAdapter(viewModel.getCityList()[position])
                initHoursAdapter(viewModel.getCityList()[position])
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })
    }

    override fun setCityPagerCurrentPage(page: Int) {
        binding.weatherPager.currentItem = page
    }

    fun updateUi() {
        initViewPager(viewModel.getCityList())
        viewModel.getCurrentCity()?.let {
            setHeaderImage(it)
            initDaysAdapter(it)
            initHoursAdapter(it)
        }
        if(viewModel.getCityList().size > 0) {
            binding.cityImage.visibility = View.VISIBLE
            binding.hourList.visibility = View.VISIBLE
            binding.dayList.visibility = View.VISIBLE
            binding.hourlyHeader.visibility = View.VISIBLE
        } else {
            binding.cityImage.visibility = View.GONE
            binding.hourList.visibility = View.GONE
            binding.dayList.visibility = View.GONE
            binding.hourlyHeader.visibility = View.GONE
        }
    }

    private fun setHeaderImage(details: CityDetail) {
        Glide.with(this)
            .load(details.city.imageUrl)
            .centerCrop()
            .into(city_image)
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

    fun initViewPager(cities: MutableList<CityDetail>) {
        pagerAdapter = CityPagerAdapter(childFragmentManager, cities)
        binding.weatherPager.adapter = pagerAdapter
    }

    private fun initDaysAdapter(details: CityDetail, dayOfWeek: Int = 0) {
        dayListAdapter = DayListAdapter(this)
        dayListAdapter?.let {
            it.selected = dayOfWeek
            it.items = ArrayList(details.weather)
            binding.dayList.adapter = it
            binding.dayList.layoutManager = GridLayoutManager(context, DAYS_NUMBER)
        }
    }

    private fun initHoursAdapter(details: CityDetail, dayOfWeek: Int = 0) {
        hourlyListAdapter = HourlyListAdapter()
        hourlyListAdapter?.let {
            it.items = details.weather[dayOfWeek].hourlyWeather
            binding.hourList.adapter = it
            binding.hourList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDayItemClicked(dayWeather: DayWeather) {
        viewModel.getCurrentCity()?.let {
            initDaysAdapter(it, dayWeather.dayOfTheWeek)
            initHoursAdapter(it, dayWeather.dayOfTheWeek)
        }
    }

    override fun onRefresh() {
        viewModel.getCurrentCity()?.let {
            viewModel.getCityDetailsByCity(it.city.cityId)
        }
    }
}