package com.rentify.user.app.view.userScreens.SearchRoomateScreen

import BottomSheetContent
import LocationViewModel
import LocationViewModelFactory
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.model.Room
import com.rentify.user.app.repository.GetLocationRepository.LocationRepository
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ArrangeComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ChangeLocation
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.HeaderSearchComponent

import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemTypeRoom
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.LocationComponent
import com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.FeetReportyeucau
import com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.ItemPost
import com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.PostListRoomateScreen

import kotlinx.coroutines.launch

enum class LocationLevel {
    PROVINCE, DISTRICT, WARD
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchRoommateScreen(navController: NavController) {
//    val list = FakeData().rooms
    val listPost = FakeData().listPost
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetHeight by remember { mutableStateOf(0.6f) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }

    // Khởi tạo LocationViewModel
    val repository = remember { LocationRepository() } // Hoặc inject từ DI container
    val factory = remember { LocationViewModelFactory(repository) }
    val locationViewModel: LocationViewModel = viewModel(factory = factory)
    val provinces by locationViewModel.provinces.observeAsState(initial = Result.success(emptyList()))
    val currentProvinceWithDistricts by locationViewModel.provinceWithDistricts.observeAsState(
        initial = Result.success(null)
    )
    val currentDistrictWithWards by locationViewModel.districtWithWards.observeAsState(
        initial = Result.success(
            null
        )
    )
    var selectionLevel by remember { mutableStateOf(0) } // 0: Province, 1: District, 2: Ward

    //
    var selectedProvinceName by remember { mutableStateOf("") }
    var selectedDistrictName by remember { mutableStateOf("") }
    var selectedWardName by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }
    val locationState by locationViewModel.locationState.collectAsState()

    // Hàm cập nhật địa chỉ đầy đủ
    fun updateFullAddress() {
        fullAddress = buildString {
            append(selectedProvinceName)
            if (selectedDistrictName.isNotEmpty()) {
                append(", ")
                append(selectedDistrictName)
            }
            if (selectedWardName.isNotEmpty()) {
                append(", ")
                append(selectedWardName)
            }
        }
    }

    // Nhận danh sách và tiêu đề phù hợp dựa trên mức độ lựa chọn
    val (currentList, currentTitle) = when (selectionLevel) {
        0 -> Pair(
            provinces.getOrNull()?.map { it.name } ?: emptyList(),
            "Chọn Tỉnh/Thành phố"
        )

        1 -> Pair(
            currentProvinceWithDistricts.getOrNull()?.districts?.map { it.name } ?: emptyList(),
            "Chọn Quận/Huyện"
        )

        2 -> Pair(
            currentDistrictWithWards.getOrNull()?.wards?.map { it.name } ?: emptyList(),
            "Chọn Phường/Xã"
        )

        else -> Pair(emptyList(), "")
    }

    LaunchedEffect(Unit) {
        locationViewModel.fetchProvinces()
    }

    // Theo dõi trạng thái của BottomSheet
    LaunchedEffect(sheetState.currentValue) {
        if (!sheetState.isVisible && selectedProvinceName.isNotEmpty()) {
            updateFullAddress()
        }
    }

    // Xử lý back press
    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
            showBottomSheet = false
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                    }
                },
                content = {
                    ChangeLocation(
                        locations = currentList,
                        title = currentTitle,
                        onItemSelected = { selectedLocation ->
                            when (selectionLevel) {
                                0 -> {
                                    val selectedProvince =
                                        provinces.getOrNull()?.find { it.name == selectedLocation }
                                    selectedProvince?.let {
                                        locationViewModel.updateLocation(province = it.name)
                                        locationViewModel.fetchDistrictsByProvince(it.code)
                                        selectionLevel = 1
                                    }
                                }

                                1 -> {
                                    val selectedDistrict =
                                        currentProvinceWithDistricts.getOrNull()?.districts?.find { it.name == selectedLocation }
                                    selectedDistrict?.let {
                                        locationViewModel.updateLocation(
                                            province = locationState.provinceName,
                                            district = it.name
                                        )
                                        locationViewModel.fetchWardsByDistrict(it.code)
                                        selectionLevel = 2
                                    }
                                }

                                2 -> {
                                    locationViewModel.updateLocation(
                                        province = locationState.provinceName,
                                        district = locationState.districtName,
                                        ward = selectedLocation
                                    )
                                    scope.launch {
                                        sheetState.hide()
                                        showBottomSheet = false
                                        selectionLevel = 0
                                    }
                                }
                            }
                        },
                        onKeyboardVisibilityChanged = { isVisible ->
                            bottomSheetHeight = if (isVisible) 0.8f else 0.6f
                        },
                        onFocusChanged = { isFocused ->
                            bottomSheetHeight = if (isFocused) 0.8f else 0.6f
                        },
                        onLocationSelected = { province ->
                            // Handle final location selection if needed
                        }
                    )

                },
                maxHeight = bottomSheetHeight
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxSize()
                .background(color = Color.White)
                .clickable(
                    enabled = sheetState.isVisible,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    }
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HeaderSearchComponent(navController)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    ArrangeComponent(navController)
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
//                        items(list) { item ->
//                            ItemTypeRoom(
//                                item,
//                                isSelected = item == selectedRoom,
//                                onClick = { selectedRoom = item })
//                        }
                    }

                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Divider(color = colorInput, thickness = 1.dp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) {
                        LocationComponent(
                            enabled = !sheetState.isVisible,
                            onShowBottomSheet = {
                                scope.launch {
                                    showBottomSheet = true
                                    sheetState.show()
                                }
                            },
                            locationState = locationState
                        )
                    }
                    Divider(color = colorInput, thickness = 1.dp)
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    PostListRoomateScreen( navController, postType = "roomate" )
                    }
                }
            }
        }
    }

