package com.rentify.user.app.repository.StaffRepository.RoomRepository

import android.util.Log
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService

class RoomStaffRepository(
    private val api: ApiServiceStaff = RetrofitStaffService.ApiService
) {
    suspend fun getListRoomBuildingId(buildingId: String): Result<RoomResponse> {
        return try {
            val response = api.getListRoomBuildingId(buildingId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Repository", "Successful response with data: ${body.data.size} rooms")
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
