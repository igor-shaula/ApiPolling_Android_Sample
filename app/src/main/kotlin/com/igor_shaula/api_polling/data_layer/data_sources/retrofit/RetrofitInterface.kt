package com.igor_shaula.api_polling.data_layer.data_sources.retrofit

import com.igor_shaula.api_polling.BuildConfig
import com.igor_shaula.api_polling.data_layer.data_sources.API_ENDPOINT_VEHICLES_LIST
import com.igor_shaula.api_polling.data_layer.data_sources.API_ENDPOINT_VEHICLE_DETAILS_BASE
import com.igor_shaula.api_polling.data_layer.data_sources.API_KEY_HEADER
import com.igor_shaula.api_polling.data_layer.data_sources.API_PATH_VEHICLE_ID
import com.igor_shaula.api_polling.data_layer.data_sources.VehicleDetailsModel
import com.igor_shaula.api_polling.data_layer.data_sources.VehicleModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface VehiclesRetrofitNetworkService {

    @Headers("$API_KEY_HEADER: ${BuildConfig.API_KEY}")
    @GET(API_ENDPOINT_VEHICLES_LIST)
    suspend fun getVehiclesList(): Response<List<VehicleModel>>

    @Headers("$API_KEY_HEADER: ${BuildConfig.API_KEY}")
    @GET("$API_ENDPOINT_VEHICLE_DETAILS_BASE/{$API_PATH_VEHICLE_ID}")
    suspend fun getVehicleDetails(
        @Path(API_PATH_VEHICLE_ID) vehicleId: String
    ): Response<VehicleDetailsModel>
}
