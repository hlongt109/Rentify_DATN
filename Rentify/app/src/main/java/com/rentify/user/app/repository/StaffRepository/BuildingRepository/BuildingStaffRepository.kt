package com.rentify.user.app.repository.StaffRepository.BuildingRepository

import android.util.Log
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService

class BuildingStaffRepository(
    private val api: ApiServiceStaff = RetrofitStaffService.ApiService
) {
    suspend fun getListBuildingStaffId(staffId: String): Result<BuildingStaffResponse> {
        Log.d("Repository", "Starting API call with staffId: $staffId")
        return try {
            val response = api.getListBuildingStaffId(staffId)
            Log.d("Repository", "Response received - Code: ${response.code()}")
            Log.d("Repository", "Response body: ${response.body()}")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Repository", "Successful response with data: ${body.data.size} buildings")
                    Result.success(body)
                } else {
                    Log.e("Repository", "Response body is null")
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Log.e("Repository", "API call failed with code: ${response.code()}")
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("Repository", "Exception during API call", e)
            Result.failure(e)
        }
    }
}