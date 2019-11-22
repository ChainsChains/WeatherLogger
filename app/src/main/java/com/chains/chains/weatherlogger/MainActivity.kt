package com.chains.chains.weatherlogger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chains.chains.weatherlogger.contract.MainContract
import com.chains.chains.weatherlogger.databinding.ActivityMainBinding
import com.chains.chains.weatherlogger.managers.AddressManager
import com.chains.chains.weatherlogger.managers.LocationPermissionManager
import com.chains.chains.weatherlogger.util.Resource
import com.chains.chains.weatherlogger.viewmodel.MainViewModel
import com.chains.chains.weatherlogger.viewmodel.ViewModelFactory
import com.chains.chains.weatherlogger.vo.WeatherConditions

class MainActivity : AppCompatActivity(), MainContract.View {

    override lateinit var viewModel: MainContract.ViewModel

    private var locationPermissionManager = LocationPermissionManager(this)
    private var addressManager = AddressManager(this)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainListRw.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this))[MainViewModel::class.java]
        binding.viewModel = viewModel as MainViewModel

        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.subscribeWeatherData().observe(this, Observer(this::onWeatherDataLoaded))
        viewModel.subscribeLoadingVisibility().observe(this, Observer(this::onDataLoading))
        addressManager.subscribeAddress().observe(this, Observer(this::fetchWeatherData))
        locationPermissionManager.subscribePermissionGranted()
            .observe(this, Observer { requestLocation() })
    }

    override fun fetchWeatherData(city: String) {
        viewModel.fetchWeatherData(city)
    }

    override fun requestLocation() {
        addressManager.requestLocation()
    }

    override fun onSaveClicked(view: View) {
        locationPermissionManager.makePermissionRequest()
    }

    override fun onWeatherDataLoaded(resource: Resource<List<WeatherConditions>>) {
        viewModel.updateAdapterData(resource)
    }

    override fun onError(errorMessage: String) {}

    override fun onDataLoading(visibility: Int) {}

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        locationPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
