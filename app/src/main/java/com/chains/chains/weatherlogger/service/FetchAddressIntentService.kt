package com.chains.chains.weatherlogger.service

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.chains.chains.weatherlogger.R
import com.chains.chains.weatherlogger.service.Constants.TAG
import java.io.IOException
import java.util.*

object Constants {
    const val TAG = "FetchAddressService"
    const val SUCCESS_RESULT = 0
    const val FAILURE_RESULT = 1
    const val PACKAGE_NAME = "com.chains.chains.weatherlogger"
    const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
    const val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
    const val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"
}

class FetchAddressIntentService : IntentService("FetchAddressIntentService") {

    private var receiver: ResultReceiver? = null

    override fun onHandleIntent(p0: Intent?) {
        p0 ?: return

        val geocoder = Geocoder(this, Locale.getDefault())

        var errorMessage = ""

        // Get the location passed to this service through an extra.
        val location = p0.getParcelableExtra<Location>(
            Constants.LOCATION_DATA_EXTRA
        )
        receiver = p0.getParcelableExtra(
            Constants.RECEIVER
        )


        var addresses: List<Address> = emptyList()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                // In this sample, we get just a single address.
                1
            )
        } catch (e: IllegalStateException) {
            errorMessage = getString(R.string.service_not_available)
            Log.e(TAG, errorMessage, e)
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available)
            Log.e(TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used)
            Log.e(
                TAG, "$errorMessage. Latitude = $location.latitude , " +
                        "Longitude =  $location.longitude", illegalArgumentException
            )
        }

        // Handle case where no address was found.
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                Log.e(TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
        } else {
            val cityName = addresses[0].locality
            Log.i(TAG, getString(R.string.address_found))

            deliverResultToReceiver(
                Constants.SUCCESS_RESULT,
                cityName
            )
        }
    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message) }
        receiver?.send(resultCode, bundle)
    }

}
