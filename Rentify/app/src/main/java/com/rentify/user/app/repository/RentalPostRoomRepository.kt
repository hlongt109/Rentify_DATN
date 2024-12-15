package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.RoomPage
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.network.APIService
import retrofit2.Response

class RentalPostRoomRepository(private val apiService: APIService) {
    suspend fun searchRooms(
        address: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        roomType: String? = null,
        sortBy: String? = null,
        page: Int = 1,
        pageSize: Int = 10,
        random: String? = null
    ): Response<RoomPage> {
        return apiService.searchRooms(
            address = address,
            minPrice = minPrice,
            maxPrice = maxPrice,
            roomType = roomType,
            sortBy = sortBy,
            page = page,
            pageSize = pageSize,
            random = random
        )
    }
}