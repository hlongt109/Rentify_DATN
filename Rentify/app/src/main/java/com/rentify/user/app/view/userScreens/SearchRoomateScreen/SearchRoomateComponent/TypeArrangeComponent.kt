package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.FakeTypeArrange
import com.rentify.user.app.ui.theme.ColorBlack

@Composable
fun TypeArrangeComponent(
    listType: List<FakeTypeArrange>,
    heightBox: Dp,
    title: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    // Sử dụng MutableStateMap để lưu trữ trạng thái isSelected cho từng item
    val selectedItems = remember { mutableStateMapOf<FakeTypeArrange, Boolean>() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Column() {
            Text(
                text = title,
                color = ColorBlack,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(15.dp)
            )
           listType.forEach{
               item ->
               val isSelected = selectedItems[item] ?: false
               ItemTypeArrange(
                   item,
                   isSelected,
                   onItemClicked = { clickedItem ->
                       // Cập nhật trạng thái isSelected cho mục này trong selectedItems
                       selectedItems[clickedItem] = !(selectedItems[clickedItem] ?: false)
                   }
               )
           }
        }
    }
}