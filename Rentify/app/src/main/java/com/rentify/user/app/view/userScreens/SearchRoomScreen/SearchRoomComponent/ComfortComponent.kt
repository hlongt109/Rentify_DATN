package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.FakeService
import com.rentify.user.app.model.FakeModel.FakeTypeArrange
import com.rentify.user.app.ui.theme.ColorBlack

@Composable
fun ComfortComponent(
    listService: List<FakeService>,
    heightBox: Dp,
    title: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    //state để theo dõi các item được chọn
    var selectedItems by remember { mutableStateOf(setOf<FakeService>()) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightBox)
            .background(color = Color.White)
    ) {
        Column {
            Text(
                text = title,
                color = ColorBlack,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(15.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 0.dp, start = 25.dp, end = 25.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(listService) { item ->
                    ItemService(item, isSelected = selectedItems.contains(item), onItemClick = {
                        selectedItems =
                            if (item in selectedItems) {
                                //neu duoc chon thi bo chon
                                selectedItems - item
                            } else {
                                //neu chua duoc chon thi chon
                                selectedItems + item
                            }
                    })
                }
            }
        }
    }
}