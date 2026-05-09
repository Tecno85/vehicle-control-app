package com.ivanmadrid.vehiclecontrolapp

import android.app.Application
import com.ivanmadrid.vehiclecontrolapp.data.AppContainer

class VehicleControlApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
