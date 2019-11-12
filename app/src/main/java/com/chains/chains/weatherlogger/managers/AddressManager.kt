package com.chains.chains.weatherlogger.managers

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.service.Constants
import com.chains.chains.weatherlogger.service.FetchAddressIntentService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AddressManager(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private var resultReceiver: AddressResultReceiver
    private val address: MutableLiveData<String> = MutableLiveData()

    init {
        resultReceiver = AddressResultReceiver(Handler())
    }

    fun subscribeAddress(): MutableLiveData<String> {
        return address
    }

    fun requestLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            val intent = Intent(context, FetchAddressIntentService::class.java).apply {
                putExtra(Constants.RECEIVER, resultReceiver)
                putExtra(Constants.LOCATION_DATA_EXTRA, location)
            }
            context.startService(intent)
        }
    }

    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            val addressOutput = resultData?.getString(Constants.RESULT_DATA_KEY) ?: ""
            if (resultCode == Constants.SUCCESS_RESULT) {
                address.postValue(addressOutput)
            }
        }
    }
}