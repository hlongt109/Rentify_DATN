package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.Room
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.RoomSaleResponse
import com.rentify.user.app.network.APIService
import retrofit2.Response

class HomeScreenRepository (private val apiService: APIService) {
    suspend fun getListOfRandomRooms(): Response<List<RoomSaleResponse>> {
        return apiService.getListOfRandomRooms()
    }
}