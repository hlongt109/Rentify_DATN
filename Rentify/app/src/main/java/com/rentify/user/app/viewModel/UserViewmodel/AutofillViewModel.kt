package com.rentify.user.app.viewModel.UserViewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Point
import com.mapbox.search.QueryType
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteOptions
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import com.mapbox.search.autocomplete.PlaceAutocompleteType
import com.mapbox.search.autofill.AddressAutofill
import com.mapbox.search.autofill.AddressAutofillOptions
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.mapbox.search.autofill.Query
import com.mapbox.search.common.IsoCountryCode
import com.mapbox.search.common.IsoLanguageCode
import com.rentify.user.app.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.Normalizer
import java.util.regex.Pattern
class AutofillViewModel :ViewModel(){
    private val addressAutofill = AddressAutofill.create()
    private val _suggestions = MutableStateFlow<List<AddressAutofillSuggestion>>(emptyList())
    val suggestions: StateFlow<List<AddressAutofillSuggestion>> = _suggestions
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var searchJob: Job? = null

    private val _searchResults = MutableStateFlow<List<PlaceAutocompleteSuggestion>>(emptyList())
    val searchResults: StateFlow<List<PlaceAutocompleteSuggestion>> = _searchResults
    private val _searchNull = MutableLiveData<String>()
    val searchNull: LiveData<String> = _searchNull

    private val _pointLocation = MutableLiveData<Point>()
     val pointLocation : LiveData<Point> = _pointLocation
    private val _selectedLocation = MutableLiveData<Point?>()
    val selectedLocation: LiveData<Point?> get() = _selectedLocation

    private lateinit var placeAutocomplete: PlaceAutocomplete



    fun removeDiacritics(input: String): String {
        return Normalizer
            .normalize(input, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    fun getSuggestions(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                val processedQuery = removeDiacritics(query)
                val response = Query.create(processedQuery)?.let {
                    _isLoading.postValue(true)
                    addressAutofill.suggestions(
                        query = it,
                        options = AddressAutofillOptions(
                            countries = listOf(IsoCountryCode.VIETNAM, IsoCountryCode("VN")),
                            language = IsoLanguageCode("vi"),
                        )
                    )
                }
                if (response != null) {
                    if (response.isValue) {
                        _isLoading.postValue(false)
                        _suggestions.value = response.value ?: emptyList()
                    }else{
                        _isLoading.postValue(false)
                        Log.e("AddressAutofill", "Error: ${response.error}")
                    }
                }
            }catch (e:Exception){
                _isLoading.postValue(false)
                Log.e("AddressAutofill", "Exception: ${e.message}")
                // Xử lý ngoại lệ
            }
        }
    }

    fun selectSuggestion(suggestion: AddressAutofillSuggestion) {
        viewModelScope.launch {
            val result = addressAutofill.select(suggestion)
            if (result.isValue) {
                // Xử lý kết quả được chọn
            }
        }
    }

    fun searchLocation(context: Context, query: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val accessToken = context.getString(R.string.mapbox_access_token)
            val placeAutocomplete = PlaceAutocomplete.create()

            val options = PlaceAutocompleteOptions(
                    countries = listOf(IsoCountryCode("VN")),
                    language = IsoLanguageCode("vi"),
                    limit = 10,
                    types = listOf(
                        PlaceAutocompleteType.Poi,
                        PlaceAutocompleteType.AdministrativeUnit.Country,
                        PlaceAutocompleteType.AdministrativeUnit.Region,
                        PlaceAutocompleteType.AdministrativeUnit.Postcode,
                        PlaceAutocompleteType.AdministrativeUnit.District,
                        PlaceAutocompleteType.AdministrativeUnit.Place,
                        PlaceAutocompleteType.AdministrativeUnit.Locality,
                        PlaceAutocompleteType.AdministrativeUnit.Street,
                    ),
                )
            val response = placeAutocomplete.suggestions(
                query,
                options = options,
            )

            response.onValue { suggestions ->
                Log.d("LogResponse", "searchLocation: $suggestions")
                _isLoading.postValue(false)
                if(suggestions.isNotEmpty()){
                    _searchResults.value = suggestions
                } else {
                    _searchResults.value = emptyList()
                    _searchNull.value = "Không có kết quả bạn tìm kiếm"
                }
            }.onError { error ->
                error.printStackTrace()
                _isLoading.postValue(false)
                _searchResults.value = emptyList()
            }
        }
    }

    suspend fun getCoordinates(suggestion: PlaceAutocompleteSuggestion): Point? {
        val placeAutocomplete = PlaceAutocomplete.create()
        val selectionResponse = placeAutocomplete.select(suggestion)

        return selectionResponse.value?.coordinate
    }

    fun setSelectedLocation(point: Point, onSuccess: () -> Unit) {
        _selectedLocation.value = point
        onSuccess()
    }

//     fun getCoordinates(suggestion: PlaceAutocompleteSuggestion) {
//        viewModelScope.launch {
//            val placeAutocomplete = PlaceAutocomplete.create()
//            val selectionResponse = placeAutocomplete.select(suggestion)
//
//            selectionResponse.onValue { result ->
//                val coordinate = result.coordinate
//                _pointLocation.value = result.coordinate
//                if (coordinate != null) {
//                    val latitude = coordinate.latitude()
//                    val longitude = coordinate.longitude()
//
//                    Log.d("Coordinates", "Latitude: $latitude, Longitude: $longitude")
//                    // Ở đây bạn có thể cập nhật UI hoặc lưu trữ tọa độ
//                } else {
//                    Log.d("Coordinates", "Không tìm thấy tọa độ cho địa điểm này")
//                }
//            }.onError { error ->
//                Log.e("Coordinates", "Lỗi khi lấy thông tin chi tiết: ${error.message}")
//            }
//        }
//    }


    fun removeVietnameseAccents(str: String): String {
        val normalized = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(normalized).replaceAll("").replace("đ", "d").replace("Đ", "D")
    }

}