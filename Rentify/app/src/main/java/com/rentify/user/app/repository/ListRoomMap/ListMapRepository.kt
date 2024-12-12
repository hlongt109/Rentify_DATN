package com.rentify.user.app.repository.ListRoomMap

import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitService
import retrofit2.Response

class ListMapRepository {
    private val apiService: APIService = RetrofitService().ApiService

//    suspend fun getListRoomMap():Result<RoomResponse>{
//        return try {
//            val response = apiService.getListRoomMap()
//            if(response.isSuccessful){
//                val body = response.body()
//                if(body != null){
//                    Result.success(body)
//                }else{
//                    Result.failure(Exception("Response body is null"))
//                }
//            }else{
//                Result.failure(Exception("Error: ${response.code()}"))
//            }
//        }catch (e: Exception){
//            Result.failure(e)
//        }
//    }
    suspend fun getListRoomMap(): Response<RoomResponse>{
        return apiService.getListRoomMap()
    }

}