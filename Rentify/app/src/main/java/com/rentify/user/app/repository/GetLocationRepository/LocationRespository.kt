package com.rentify.user.app.repository.GetLocationRepository

import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.LocationService

class LocationRepository(private val api: APIService = LocationService.ApiService) {
    suspend fun getProvinces(): Result<List<Province>> = try {
        Result.success(api.getProvinces())
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getProvinceWithDistricts(code: String): Result<Province> = try {
        val province = api.getProvinceWithDistricts(code)
        // Đảm bảo districts không null
        val nonNullProvince = province.copy(districts = province.districts ?: emptyList())
        Result.success(nonNullProvince)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getWard(code: String): Result<District> = try {
        val district = api.getDistrictWithWards(code)
        // Đảm bảo districts không null
        val nonNullProvince = district.copy(wards = district.wards ?: emptyList())
        Result.success(nonNullProvince)
    } catch (e: Exception) {
        Result.failure(e)
    }

}