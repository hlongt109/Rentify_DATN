package com.rentify.user.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.StatusBookingRequest
import com.rentify.user.app.model.Model.UserOfBooking
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.BookingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val bookingRepository = BookingRepository(apiService)

    private val _addBookingResult = MutableSharedFlow<Result<BookingRequest>>()
    val addBookingResult = _addBookingResult.asSharedFlow()

    private val _userDetails = MutableLiveData<UserOfBooking>()
    val userDetails: LiveData<UserOfBooking> get() = _userDetails

    private val _bookingList = MutableLiveData<List<BookingResponse>>()
    val bookingList: LiveData<List<BookingResponse>> get() = _bookingList

    private val _updateBookingStatusResult = MutableLiveData<Result<StatusBookingRequest>?>()
    val updateBookingStatusResult: LiveData<Result<StatusBookingRequest>?> get() = _updateBookingStatusResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun resetUpdateBookingStatusResult() {
        _updateBookingStatusResult.value = null
    }

    fun fetchListBooking(userId: String, status: Int) {
        viewModelScope.launch {
            try {
                setLoading(true)
                val response = bookingRepository.getBookings(userId, status)
                if (response.isSuccessful && response.body() != null) {
                    _bookingList.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                setLoading(false)
            }
        }
    }

    fun fetchUserDetails(userId: String){
        viewModelScope.launch {
            try {
                val response = bookingRepository.getUserDetails(userId)
                if(response.isSuccessful && response.body()!= null){
                    _userDetails.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun addBooking(bookingRequest: BookingRequest) {
        viewModelScope.launch {
            try {
                val response = bookingRepository.bookRoom(bookingRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addBookingResult.emit(Result.success(it))
                    } ?: run {
                        _addBookingResult.emit(Result.failure(Exception("Response is empty")))
                    }
                } else {
                    _addBookingResult.emit(Result.failure(Exception(response.message())))
                }
            } catch (e: Exception) {
                _addBookingResult.emit(Result.failure(e))
            }
        }
    }

    fun updateBookingStatus(bookingId: String, status: Int) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val statusBookingRequest = StatusBookingRequest(status)
                val response = bookingRepository.updateStatusBooking(bookingId, statusBookingRequest)
                if (response.isSuccessful && response.body() != null) {
                    _updateBookingStatusResult.value = Result.success(response.body()!!)

                    // Cập nhật danh sách hiện tại thay vì gọi lại API
                    _bookingList.value = _bookingList.value?.map { booking ->
                        if (booking._id == bookingId) {
                            booking.copy(status = status) // Thay đổi trạng thái của item
                        } else {
                            booking
                        }
                    }

                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _updateBookingStatusResult.value = Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                _updateBookingStatusResult.value = Result.failure(e)
            } finally {
                setLoading(false)
            }
        }
    }
}