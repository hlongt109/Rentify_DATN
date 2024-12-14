package com.rentify.user.app.view.userScreens.homeScreen.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.DistrictViewModel
@Composable
fun LayoutVideo(
    districtViewModel: DistrictViewModel = viewModel(),
    navController: NavController,
    city: String
) {
    val listNameDistrict = districtViewModel.districts.value
    val isLoading = districtViewModel.isLoading.value
    val hasMoreData by districtViewModel.hasMoreData.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(city) {
        districtViewModel.fetchDistricts(city)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        state = lazyListState
    ) {
        items(listNameDistrict.size) { index ->
            ImageThumbnail(
                title = listNameDistrict[index],
                navController = navController
            )
        }
        if (isLoading && hasMoreData) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    // Cuộn gần cuối danh sách để tải thêm
    LaunchedEffect(lazyListState, listNameDistrict) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                if (lastVisibleItemIndex == listNameDistrict.size - 1 &&
                    !isLoading && districtViewModel.hasMoreData.value &&
                    listNameDistrict.isNotEmpty()
                ) {
                    districtViewModel.fetchDistricts(city, loadMore = true)
                }
            }
    }

}

@Composable
fun ImageThumbnail(
    title: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .width(150.dp)
            .clickable {
                navController.navigate("RENTAL_POST/${title ?: ""}")
            }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Hình ảnh
            Image(
                painter = painterResource(id = R.drawable.imagelocation),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Text nằm ở dưới cùng và đè lên ảnh
            Text(
                text = title,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(bottom = 5.dp)
                    .align(Alignment.BottomCenter),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
