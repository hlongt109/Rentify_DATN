package com.rentify.user.app.repository.GetLocationRepository

import android.util.Log
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(
    private val api: APIService = LocationService.ApiService
) {
    suspend fun getProvinces(): Result<List<Province>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getProvinces()
            Log.d("LocationRepository", "Received ${response.size} provinces")
            Result.success(response)
        } catch (e: Exception) {
            Log.e("LocationRepository", "Error fetching provinces", e)
            Result.failure(e)
        }
    }

    suspend fun getProvinceWithDistricts(code: String): Result<Province> = withContext(Dispatchers.IO) {
        try {
            val province = api.getProvinceWithDistricts(code)
            Log.d("LocationRepository", "Received districts for province $code")
            Result.success(province.copy(districts = province.districts ?: emptyList()))
        } catch (e: Exception) {
            Log.e("LocationRepository", "Error fetching districts for province $code", e)
            Result.failure(e)
        }
    }

    suspend fun getWard(code: String): Result<District> = withContext(Dispatchers.IO) {
        try {
            val district = api.getDistrictWithWards(code)
            Log.d("LocationRepository", "Received wards for district $code")
            Result.success(district.copy(wards = district.wards ?: emptyList()))
        } catch (e: Exception) {
            Log.e("LocationRepository", "Error fetching wards for district $code", e)
            Result.failure(e)
        }
    }
}