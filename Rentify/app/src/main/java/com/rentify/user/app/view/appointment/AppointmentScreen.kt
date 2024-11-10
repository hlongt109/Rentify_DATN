package com.rentify.user.app.view.appointment


import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn

import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService


@Preview(showBackground = true)
@Composable
fun AppointmentScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppointmentTopAppBarWithActions(
            onBackClick = { /* Handle back icon click */ },
            canlendarClick = { /* Handle  icon click */ }
        )
        SearchBar()
        AppointmentTabRow()
    }
}


@Composable
fun AppointmentTopAppBarWithActions(
    onBackClick: () -> Unit,
    canlendarClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Quản lý lịch hẹn",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = Color(0xFFFFFFFF), // Customize background color
        contentColor = Color.Black, // Customize text/icon color
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = canlendarClick) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar")
            }
        },
        elevation = 4.dp // Customize elevation if needed
    )
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Nhập thông tin tìm kiếm"
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Nhập thông tin tìm kiếm...",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(15.dp))
    )
}

@Composable
fun AppointmentTabRow() {

    var selectedTabIndex by remember { mutableStateOf(0) }
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.White,
            contentColor = Color(0xFFFF5722),
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("Chờ xác nhận") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("Phòng chưa xem") }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = { Text("Phòng đã xem") }
            )
        }
        when (selectedTabIndex) {
            0 -> Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoomListScreen()
            }
            1 -> Column (
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Phòng chưa xem")
            }
            2 -> Column (
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Phòng đã xem")
            }
        }
    }
}
