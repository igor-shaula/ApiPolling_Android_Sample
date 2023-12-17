package com.igor_shaula.api_polling.data_layer

import androidx.lifecycle.MutableLiveData

interface TheRepository {

    // MLD = MutableLiveData
    val vehicleDetailsMapMLD get() = MutableLiveData<MutableMap<String, VehicleDetailsRecord>>()

    suspend fun getAllVehiclesIds(): MutableMap<String, VehicleStatus>

    fun startGettingVehiclesDetails(
        vehiclesMap: MutableMap<String, VehicleStatus>?,
        updateViewModel: (Pair<String, VehicleStatus>) -> Unit
    )

    fun stopGettingVehiclesDetails()

    fun getNumberOfNearVehicles(vehiclesMap: MutableMap<String, VehicleStatus>?): Int
}