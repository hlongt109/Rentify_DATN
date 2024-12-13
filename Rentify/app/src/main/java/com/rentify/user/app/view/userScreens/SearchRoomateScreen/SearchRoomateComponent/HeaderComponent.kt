package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.iconBack
import com.rentify.user.app.ui.theme.search
import com.rentify.user.app.ui.theme.textFieldBackgroundColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSearchComponent(navController: NavController) {
    //kich thuoc man hinh
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    var value by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(color = colorHeaderSearch)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
//            Icon(
//                painter = painterResource(iconBack),
//                contentDescription = "Back",
//                tint = Color.Black,
//                modifier = Modifier.size(25.dp)
//                    .offset(x = -20.dp, y = 0.dp)
//            )

            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .offset(x = -20.dp, y = 0.dp)
            ) {
                Icon(
                    painter = painterResource(iconBack),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(25.dp)
                        .offset(x = 0.dp, y = 0.dp)
                )
            }


            Row(
                modifier = Modifier
                    .width(screenWidth.dp / 1.3f)
                    .height(55.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(search),
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                TextField(
                    value = value,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = "Nhập tiêu đề tin đăng", color = colorInput_2
                        )

                    },
                    modifier = Modifier
                        .width(screenWidth.dp / 1.5f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
//                        cursorColor = greenInput
                    ),
//                    shape = RoundedCornerShape(20.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearch() {
    val navController = rememberNavController()
    HeaderSearchComponent(navController)
}