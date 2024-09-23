package com.tw.offlinegpslocationdemo.location

interface LocationCallback {
    fun onLocationFound(latitude: Double, longitude: Double, accuracy: Float)
}