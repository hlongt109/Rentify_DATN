package com.rentify.user.app.repository

import ContractResponse
import com.rentify.user.app.network.APIService
import retrofit2.Response

class ContractRepository (private val apiService: APIService)  {

    suspend fun getContractDetails(user_id : String): Response<List<ContractResponse>> {
        return apiService.getContractDetail(user_id)
    }

}