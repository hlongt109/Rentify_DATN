package com.rentify.user.app.view.staffScreens.BillScreenStaff

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.rentify.user.app.ui.theme.elevator
import com.rentify.user.app.ui.theme.parking
import com.rentify.user.app.ui.theme.washing
import com.rentify.user.app.ui.theme.wifi

data class ServiceStaff(
    val title: String,
    val image: Int,
    val price: Double
)

val fakeList = listOf(
    ServiceStaff(
        title = "Máy giặt",
        image = washing,
        price = 3000.0
    ),
    ServiceStaff(
        title = "Thang máy",
        image = elevator,
        price = 3000.0
    ),
    ServiceStaff(
        title = "Đỗ xe",
        image = parking,
        price = 3000.0
    ),
    ServiceStaff(
        title = "Mạng",
        image = wifi,
        price = 3000.0
    )
)