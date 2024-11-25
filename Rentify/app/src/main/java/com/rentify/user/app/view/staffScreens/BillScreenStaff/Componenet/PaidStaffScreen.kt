package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemPaid
import com.rentify.user.app.view.userScreens.BillScreen.Component.groupItemsByMonthYear
import com.rentify.user.app.view.userScreens.BillScreen.FakeData
import java.util.Calendar

@Composable
fun PaidStaffScreen(navController: NavController) {
    val list = FakeData().fakeRoomPayments
    // Lọc danh sách chỉ lấy các item có status == 1
    val filteredList = list.filter { it.status == 1 }
    val groupedByYear = filteredList.groupBy {
        val calendar = Calendar.getInstance().apply { time = it.invoiceDate }
        calendar.get(Calendar.YEAR) // Lấy năm từ ngày
    }
    val sortedGroupedByYear = groupedByYear.toSortedMap(compareByDescending { it.toInt() })
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(15.dp)
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
                sortedGroupedByYear.forEach { (year, paymentsByYear) ->
                    // Nhóm các mục trong năm hiện tại theo tháng
                    val groupedByMonth = groupItemsByMonthYear(paymentsByYear)

                    // Thêm ItemPaid cho mỗi năm
                    item {
                        ItemPaidStaff(year = year.toString(), itemsByMonth = groupedByMonth)
                    }
                }
            }
        }
    }
}
