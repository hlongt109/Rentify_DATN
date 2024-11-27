package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.network.APIService
import retrofit2.Response

class RentalPostRoomRepository(private val apiService: APIService) {
    suspend fun getListOfRandomRooms(): Response<List<RoomResponse>> {
        return apiService.getListOfRandomRooms()
    }

    suspend fun searchRooms(
        address: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        roomType: String? = null,
        sortBy: String? = null
    ): Response<List<RoomResponse>> {
        return apiService.searchRooms(
            address = address,
            minPrice = minPrice,
            maxPrice = maxPrice,
            roomType = roomType,
            sortBy = sortBy
        )
    }
}