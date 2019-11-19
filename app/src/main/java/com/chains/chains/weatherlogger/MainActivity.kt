package com.chains.chains.weatherlogger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chains.chains.weatherlogger.contract.MainContract
import com.chains.chains.weatherlogger.managers.AddressManager
import com.chains.chains.weatherlogger.managers.LocationPermissionManager
import com.chains.chains.weatherlogger.ui.RwAdapter
import com.chains.chains.weatherlogger.util.Resource
import com.chains.chains.weatherlogger.viewmodel.MainViewModel
import com.chains.chains.weatherlogger.viewmodel.ViewModelFactory
import com.chains.chains.weatherlogger.vo.WeatherConditions
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/*
    TODO
    0. MVVM
    1. Show current location in textview
    2. Show weather data for current location in textview
    3. Add realm to save each returned data
    4. Add list
    5. Modify UI
    6.
 */

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override lateinit var viewModel: MainContract.ViewModel

    private lateinit var locationPermissionManager: LocationPermissionManager
    private lateinit var addressManager: AddressManager

    private lateinit var rwAdapter: RwAdapter
    private lateinit var rwLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationPermissionManager = LocationPermissionManager(this)
        addressManager = AddressManager(this)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(MainViewModel::class.java)
        subscribeToData()

        rwLayoutManager = LinearLayoutManager(this)
        rwAdapter = RwAdapter(emptyList())

        main_list_rw.apply {
            layoutManager = rwLayoutManager
            adapter = rwAdapter
        }
    }

    private fun subscribeToData() {
        viewModel.subscribeWeatherData()
            .observe(this, Observer(this::onWeatherDataLoaded))
        viewModel.subscribeLoadingVisibility()
            .observe(this, Observer(this::onDataLoading))
        addressManager.subscribeAddress().observe(this, Observer {
            viewModel.fetchWeatherData(it)
        })
        locationPermissionManager.subscribePermissionGranted().observe(this, Observer {
            addressManager.requestLocation()
        })
    }

    override fun onSaveClicked(view: View) {
        locationPermissionManager.makePermissionRequest()
    }

    override fun onWeatherDataLoaded(resource: Resource<List<WeatherConditions>>) {
        resource.data?.let { rwAdapter.setData(it) }
    }

    override fun onError(errorMessage: String) {}

    override fun onDataLoading(visibility: Int) {}

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        locationPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
