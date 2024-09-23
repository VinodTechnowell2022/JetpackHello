package com.tw.offlinegpslocationdemo.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.tw.offlinegpslocationdemo.MyApp

/**
 * Class to fetch lat long
 */
class LocationModel( private val mContext: Context, private var locationCallback: LocationCallback? ) : LocationListener {
    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false
    var canGetLocation = false
    var locationn : Location? = null

    /**
     * Function to get latitude
     */
    var latitude = 0.0
        private set

    /**
     * Function to get longitude
     */
    var longitude = 0.0
        private set
    var accuracy = 0f
        private set

    // Declaring a Location Manager
    var locationManager: LocationManager? = null
    fun initialize() {
        getLocation()
    }

    /**
     * In this function we�ll get the location from network provider first. If
     * network provider is disabled, then we get the location from GPS provider.
     *
     * @return
     */
    private fun getLocation(): Location? {
        try {
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.isAltitudeRequired = false
            criteria.isBearingRequired = false
            criteria.isCostAllowed = true
            criteria.powerRequirement = Criteria.POWER_LOW
            val provider = locationManager!!.getBestProvider(criteria, true)
            // getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            // getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
                latitude = 0.0
                longitude = 0.0
                accuracy = 0.0f
                // no network provider is enabled
            } else {
                canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return null
                    }
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        //location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (locationn != null) {
                            latitude = locationn!!.latitude
                            longitude = locationn!!.longitude
                            accuracy = locationn!!.accuracy
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (locationn == null) {
                        if (ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return null
                        }
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {

                            // location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (locationn != null) {
                                latitude = (locationn as Location).latitude
                                longitude = (locationn as Location).longitude
                                accuracy = (locationn as Location).accuracy
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //callback for lat long
        if (locationCallback != null && latitude != 0.0) {
            locationCallback!!.onLocationFound(latitude, longitude, accuracy)
        }
        //        Toast.makeText(mContext, "Location found Lat>> " + latitude + " >>Long>> " + longitude
//                + " >> accuracy >> " + accuracy, Toast.LENGTH_LONG).show();
        return locationn
    }

    override fun onLocationChanged(location: Location) {
        if (location != null) {
            latitude = location.latitude
            longitude = location.longitude
            accuracy = location.accuracy


            //callback for lat long
            if (locationCallback != null && latitude != 0.0) {
                //System.out.println("location>>onLocationChanged >> " + MyApplication.getInstance().getLocationModel());
                //System.out.println("location>>onLocationChanged this >> " + this);
                println("location>>loadStores >> getLatitude$latitude")
                println("location>>loadStores >> getLongitude$longitude")
                locationCallback!!.onLocationFound(latitude, longitude, accuracy)
                MyApp.instance.getLocationModel()?.setLocationCallback(null)

//                Toast.makeText(mContext, "Refreshing after 5 second.    Lat>> " + latitude + " >>Long>> " + longitude
//                        + " >> accuracy >> " + accuracy, Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onProviderDisabled(provider: String) {
        MyApp.instance.getLocationModel()?.initialize()
    }

    override fun onProviderEnabled(provider: String) {
        MyApp.instance.getLocationModel()?.initialize()
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}


    fun setLocationCallback(locationCallback: LocationCallback?) {
        this.locationCallback = locationCallback
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 0 // 1 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES: Long = 0 // 2 sec
    }
}