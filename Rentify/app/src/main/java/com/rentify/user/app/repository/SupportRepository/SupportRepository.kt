package com.rentify.user.app.repository.SupportRepository

import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class SupportRepository(private val apiService: APIService = RetrofitService().ApiService) {
    suspend fun getSupportsByUserId(userId: String): Result<APISupportResponse> {
        return try {
            val response = apiService.getSupportsByUserId(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message") // Lấy giá trị trường "message"
                    } catch (e: JSONException) {
                        "Lỗi không xác định" // Trường hợp không parse được JSON
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //lay thong tin phong theo hop dong
    suspend fun getInfoRoom(userId: String): Result<ContractRoomResponse> {
        return try {
            val response = apiService.getRoomByContract(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message") // Lấy giá trị trường "message"
                    } catch (e: JSONException) {
                        "Lỗi không xác định" // Trường hợp không parse được JSON
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun checkStatusContract(userId: String): Result<ContractResponse> {
        return try {
            val response = apiService.checkContract(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message") // Lấy giá trị trường "message"
                    } catch (e: JSONException) {
                        "Lỗi không xác định" // Trường hợp không parse được JSON
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //add
    suspend fun createReport(
        userId: String,
        roomId: String,
        buildingId: String,
        titleSupport: String,
        contentSupport: String,
        status: Int,
        imagePaths: List<String?>
    ): Result<AddSupport> {
        return try {
            // Chuyển đổi dữ liệu sang dạng `RequestBody`
            val userIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), userId)
            val roomIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), roomId)
            val buildingId = RequestBody.create("text/plain".toMediaTypeOrNull(), buildingId)
            val titleBody = RequestBody.create("text/plain".toMediaTypeOrNull(), titleSupport)
            val contentBody = RequestBody.create("text/plain".toMediaTypeOrNull(), contentSupport)
            val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status.toString())

            // Chuyển đổi danh sách ảnh sang `MultipartBody.Part`
            val imageParts = imagePaths.map { path ->
                val file = File(path)
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            val response = apiService.createSupportReport(
                userIdBody,
                roomIdBody,
                buildingId,
                titleBody,
                contentBody,
                statusBody,
                imageParts
            )

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message") // Lấy giá trị trường "message"
                    } catch (e: JSONException) {
                        "Lỗi không xác định" // Trường hợp không parse được JSON
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
