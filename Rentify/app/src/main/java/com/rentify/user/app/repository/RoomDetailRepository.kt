package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.EmptyRoomResponse
import com.rentify.user.app.model.Model.RoomDetailResponse
import com.rentify.user.app.model.UserOfRoomDetail
import com.rentify.user.app.network.APIService
import retrofit2.Response

class RoomDetailRepository(private val apiService: APIService) {
    suspend fun getRoomDetails(roomId: String): Response<RoomDetailResponse>{
        return apiService.getRoomDetail(roomId)
    }

    suspend fun getUserOfRoomDetail(landlordId: String): Response<UserOfRoomDetail>{
        return apiService.getDetailLandlord(landlordId)
    }

    suspend fun getEmptyRoom(buildId: String): Response<List<EmptyRoomResponse>>{
        return apiService.getEmptyRooms(buildId)
    }
}