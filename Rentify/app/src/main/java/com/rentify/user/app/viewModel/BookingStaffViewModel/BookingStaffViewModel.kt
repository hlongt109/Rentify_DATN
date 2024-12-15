package com.rentify.user.app.viewModel.BookingStaffViewModel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.Booking
import com.rentify.user.app.model.Model.BookingStaff
import com.rentify.user.app.model.Model.toBooking
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.repository.StaffRepository.BookingRepository.BookingRespository
import com.rentify.user.app.utils.Component.getLoginViewModel
import kotlinx.coroutines.launch

class BookingStaffViewModel(private val respository: BookingRespository, private val staffId: String) : ViewModel() {
    private val _listBooking = MutableLiveData<List<BookingStaff>>()
    val listBooking: LiveData<List<BookingStaff>> = _listBooking

    constructor() : this(BookingRespository(RetrofitStaffService), "")

    init {
        if (staffId.isNotEmpty()) {
            loadBookingList(staffId)
        }
    }

    fun loadBookingList(manager_id: String) {
        viewModelScope.launch{
            try {
                val res = respository.getBooking(manager_id)
                if(res.isSuccessful){
                    _listBooking.postValue(res.body()?.map{it.toBooking()})
                }else{
                    _listBooking.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get booking error: " + e.message)
                _listBooking.postValue(emptyList())
            }
        }
    }

    fun updateStatusBooking(
        id: String,
        status: Int,
        manager_id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch{
            try {
                val res = respository.updateBooking(id, status)
                if (res.isSuccessful && res.body()?.status == 200){
                    loadBookingList(manager_id)
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "Error, update status booking: " + e.message)
                onResult(false)
            }
        }
    }

    fun getBookingById(id: String): LiveData<BookingStaff?>{
        val result = MutableLiveData<BookingStaff?>()
        viewModelScope.launch{
            try {
                val res = respository.getBookingDetails(id)
                if(res.isSuccessful){
                    result.postValue(res.body()?.toBooking())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Booking", "getBookingById: "+e )
                result.postValue(null)
            }
        }
        return result
    }
}

class BookingViewModelFactory(private val staffId: String, private val respository: BookingRespository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookingStaffViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookingStaffViewModel(respository, staffId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}