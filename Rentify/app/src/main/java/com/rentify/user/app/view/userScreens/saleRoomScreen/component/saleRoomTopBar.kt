package com.rentify.user.app.view.userScreens.saleRoomScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostArrange
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostHeader
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostList
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostRoomType
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RoomType
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.SearchBarRentalPostRoom
import com.rentify.user.app.viewModel.RentalPostRoomViewModel
import com.rentify.user.app.viewModel.SaleRoomViewModel

@Composable
fun SaleRoomPostHeader(
    rentalPostRoomViewModel: SaleRoomViewModel,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    selectedRoomType: RoomType?,
    onRoomTypeSelected: (RoomType?) -> Unit,
    minPrice: Int?,
    maxPrice: Int?,
    onPriceRangeSelected: (Int?, Int?) -> Unit,
    sortBy: String?,
    onSortSelected: (String?) -> Unit,
    onFilterApplied: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RentalPostTopBar(navController)

        // Search bar
        SearchBarRentalPostRoom(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            onSearch = onFilterApplied
        )

        Spacer(modifier = Modifier.padding(5.dp))

        // Hiển thị loại phòng
        RentalPostRoomType(
            selectedRoomType = selectedRoomType,
            onRoomTypeSelected = {
                onRoomTypeSelected(it)
                onFilterApplied()
            }
        )

        RentalPostArrange(
            onPriceRangeSelected = { min, max ->
                onPriceRangeSelected(min, max)
                onFilterApplied()
            },
            onSortSelected = { selectedSort ->
                onSortSelected(selectedSort)
                onFilterApplied()
            }
        )
    }
}



@Composable
fun RentalPostTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Canh giữa theo chiều dọc
    ) {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        Text(
            text = "Săn phòng giảm giá",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}