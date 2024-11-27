package com.rentify.user.app.view.userScreens.BillScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemUnPaid

@Composable
fun UnPaidScreen( navController: NavController ) {
    val list = FakeData().fakeRoomPayments

    // Lọc danh sách chỉ lấy các item có status == 0
    val filteredList = list.filter { it.status == 0 }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(7.dp)
            ) {
                items(filteredList) { item ->
                    ItemUnPaid(item, navController = navController)
                }
            }
        }
    }
}

