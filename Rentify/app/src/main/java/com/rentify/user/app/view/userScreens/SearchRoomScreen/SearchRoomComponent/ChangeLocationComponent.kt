package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.Location
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.location
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.search
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FakeData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChangeLocation(
    listLocation: List<Location>,
    onKeyboardVisibilityChanged: (Boolean) -> Unit
) {
    // Kích thước màn hình
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    var value by remember { mutableStateOf("") }

    // Theo dõi trạng thái bàn phím
    val imeVisible = WindowInsets.isImeVisible

    // Gọi callback mỗi khi trạng thái bàn phím thay đổi
    LaunchedEffect(imeVisible) {
        onKeyboardVisibilityChanged(imeVisible)
    }
    var selectedItem by remember { mutableStateOf<Location?>(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Text(
                text = "Tỉnh/Thành phố",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = ColorBlack
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))
            Row(
                modifier = Modifier
                    .width(screenWidth.dp / 1.2f)
                    .height(55.dp)
                    .background(color = textFieldBackgroundColor, shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = colorInput, shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(search),
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                TextField(
                    value = value,
                    onValueChange = { value = it },
                    placeholder = {
                        Text(
                            text = "Nhập vị trí",
                            color = colorText
                        )
                    },
                    modifier = Modifier.width(screenWidth.dp / 1.5f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textFieldBackgroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
            }

            Spacer(modifier = Modifier.padding(top = 15.dp))
            LazyColumn {
                items(listLocation) { items ->
                    ItemLocation(items, isSelected = items == selectedItem, onClick = {
                        selectedItem = items
                    })
                }
            }
        }
    }
}



