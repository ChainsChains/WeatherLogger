package com.chains.chains.weatherlogger.managers

import android.Manifest
import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.chains.chains.weatherlogger.util.Event
import com.chains.chains.weatherlogger.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class LocationPermissionManager(private val context: Activity) :
    EasyPermissions.PermissionCallbacks {

    private val permissionGranted: MutableLiveData<Event<String>> = MutableLiveData()

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(context, perms)) {
            AppSettingsDialog.Builder(context).build().show()
        }
    }

    fun subscribePermissionGranted(): MutableLiveData<Event<String>> {
        return permissionGranted
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionGranted.postValue(Event(perms[0]))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun makePermissionRequest() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        if (EasyPermissions.hasPermissions(context, permission)) {
            permissionGranted.postValue(Event(permission))
        } else {
            doRequest()
        }
    }

    private fun doRequest() {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                context,
                RC_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                .setRationale(R.string.location_rationale)
                .setPositiveButtonText(R.string.rationale_ask_ok)
                .setNegativeButtonText(R.string.rationale_ask_cancel)
                .build()
        )
    }

    companion object {
        const val RC_LOCATION = 100
    }
}