package com.rentify.user.app.view.userScreens.saleRoomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostHeader
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RoomType
import com.rentify.user.app.view.userScreens.saleRoomScreen.component.SaleRoomPostHeader
import com.rentify.user.app.view.userScreens.saleRoomScreen.component.SaleRoomPostList
import com.rentify.user.app.viewModel.SaleRoomViewModel

@Composable
fun SaleRoomPostScreen(
    navController: NavHostController,
    saleRoomViewModel: SaleRoomViewModel = viewModel(),
    title: String?
) {
    val listRoom by saleRoomViewModel.roomList.observeAsState(emptyList())
    val errorMessage by saleRoomViewModel.errorMessage.observeAsState("")
    val totalRooms by saleRoomViewModel.totalRooms.observeAsState(0)
    val isLoading by saleRoomViewModel.isLoading.observeAsState(false)
    var currentPage by remember { mutableStateOf(1) }
    val pageSize = 6

    var searchText by remember { mutableStateOf(title ?: "") }
    var selectedRoomType by remember { mutableStateOf<RoomType?>(null) }
    var minPrice by remember { mutableStateOf<Int?>(null) }
    var maxPrice by remember { mutableStateOf<Int?>(null) }
    var sortBy by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(title) {
        if (!title.isNullOrEmpty()) {
            saleRoomViewModel.fetchListRoom(
                address = title,
                page = currentPage,
                pageSize = pageSize
            )
        }
    }

    LaunchedEffect(currentPage) {
        if (currentPage == 1 && listRoom.isEmpty()) {
            saleRoomViewModel.fetchListRoom(page = currentPage, pageSize = pageSize)
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SaleRoomPostHeader(
            rentalPostRoomViewModel = saleRoomViewModel,
            searchText = searchText,
            onSearchTextChange = { newText ->
                searchText = newText
                currentPage = 1
                saleRoomViewModel.fetchListRoom(
                    address = searchText,
                    roomType = selectedRoomType?.name,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    sortBy = sortBy,
                    page = currentPage,
                    clearCurrentList = true
                )
            },
            selectedRoomType = selectedRoomType,
            onRoomTypeSelected = { selectedRoomType = it },
            minPrice = minPrice,
            maxPrice = maxPrice,
            onPriceRangeSelected = { min, max ->
                minPrice = min
                maxPrice = max
            },
            sortBy = sortBy,
            onSortSelected = { sortBy = it },
            onFilterApplied = {
                currentPage = 1
                saleRoomViewModel.fetchListRoom(
                    address = searchText,
                    roomType = selectedRoomType?.name,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    sortBy = sortBy,
                    page = currentPage,
                    clearCurrentList = true
                )
            },
            navController
        )

        if (isLoading && currentPage == 1) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.loading)
                        .decoderFactory(GifDecoder.Factory())
                        .build(),
                    contentDescription = "Loading GIF",
                    modifier = Modifier.size(150.dp)
                )
            }
        } else if (listRoom.isEmpty() && !isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chinhsach),
                    contentDescription = "No Data",
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage.ifEmpty { "Không có dữ liệu" },
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            SaleRoomPostList(
                getRentalPostList = listRoom,
                totalRooms = totalRooms,
                currentPage = currentPage,
                loadMoreRooms = {
                    currentPage++
                    saleRoomViewModel.fetchListRoom(
                        address = searchText,
                        roomType = selectedRoomType?.name,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        sortBy = sortBy,
                        page = currentPage,
                        pageSize = pageSize
                    )
                },
                isLoading = isLoading,
                pageSize = pageSize,
                navController
            )
        }
    }
}