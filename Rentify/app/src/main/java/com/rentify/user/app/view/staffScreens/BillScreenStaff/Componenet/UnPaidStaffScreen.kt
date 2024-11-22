package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemUnPaid
import com.rentify.user.app.view.userScreens.BillScreen.FakeData

@Composable
fun UnPaidStaffScreen( navController: NavController) {
    val list = FakeData().fakeRoomPayments

    // Lọc danh sách chỉ lấy các item có status == 0
    val filteredList = list.filter { it.status == 0 }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(7.dp)
            ) {
                items(filteredList) { item ->
                    ItemUnPaid(item, navController = navController)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                Button(
                    onClick = {navController.navigate(ROUTER.ADDBILL_STAFF.name)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorHeaderSearch
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Thêm hóa đơn",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UnPaidStaffScreenPreview() {
    UnPaidStaffScreen(navController = rememberNavController())
}