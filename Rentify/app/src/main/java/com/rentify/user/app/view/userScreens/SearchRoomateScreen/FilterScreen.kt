package com.rentify.user.app.view.userScreens.SearchRoomateScreen

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.backgroundGrayColor
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ComfortComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.HeaderFilter
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.PriceComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.TypeArrangeComponent
import androidx.compose.ui.unit.sp

@Composable
fun FilterScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val listArrage_1 = FakeData().listFakeTypeArrange_1s
    val listArrage_2 = FakeData().listFakeTypeArrange_2s
    val listService = FakeData().listFakeService
    val listService_2 = FakeData().listFakeService_2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundGrayColor)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = screenHeight.dp/7f) // Tạo khoảng trống cho nút
        ) {
            HeaderFilter()
            Spacer(modifier = Modifier.padding(2.dp))
            PriceComponent()

            Spacer(modifier = Modifier.padding(5.dp))
            TypeArrangeComponent(
                listArrage_1,
                heightBox = screenHeight.dp / 4.2f,
                title = "Sắp xếp theo"
            )

            Spacer(modifier = Modifier.padding(5.dp))
            TypeArrangeComponent(
                listArrage_2,
                heightBox = screenHeight.dp / 2.8f,
                title = "Loại phòng"
            )

            Spacer(modifier = Modifier.padding(5.dp))
            ComfortComponent(
                listService = listService,
                title = "Tiện nghi",
                heightBox = screenHeight.dp / 4.0f
            )

            Spacer(modifier = Modifier.padding(5.dp))
            ComfortComponent(
                listService = listService_2,
                title = "Nội thất",
                heightBox = screenHeight.dp / 4.0f
            )
        }

        // Nút "Áp dụng" nổi lên ở dưới cùng
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp/7f)
                .background(color = Color.White)
        ) {
           Box(modifier = Modifier.padding(20.dp)){
               Button(
                   onClick = {},
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(50.dp)
                       .clip(RoundedCornerShape(10.dp)),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = colorHeaderSearch
                   ),
                   shape = RoundedCornerShape(10.dp)
               ) {
                   Text(
                       text = "Áp dụng",
                       color = Color.White,
                       fontSize = 17.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
           }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFilter() {
    val navController = rememberNavController()
    FilterScreen(navController)
}