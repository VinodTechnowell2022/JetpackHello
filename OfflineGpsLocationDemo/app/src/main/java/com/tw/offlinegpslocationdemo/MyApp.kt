package com.tw.offlinegpslocationdemo

import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import com.tw.offlinegpslocationdemo.location.LocationModel


class MyApp: MultiDexApplication() {

    private var locationModel: LocationModel? = null

    companion object {
        lateinit var instance: MyApp private set
    }

    override fun onCreate() { super.onCreate()
        instance = this
        locationModel = LocationModel(this, null)
        locationModel!!.initialize()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    fun getLocationModel(): LocationModel? {
        return locationModel
    }
}