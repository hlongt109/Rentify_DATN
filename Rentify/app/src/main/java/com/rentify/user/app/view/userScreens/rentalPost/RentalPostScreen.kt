package com.rentify.user.app.view.userScreens.rentalPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Ro
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.model.Room
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FakeData
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ArrangeComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemTypeRoom
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostArrange
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostHeader
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostList

import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostRoomType
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.getRentalRoomList

@Preview(showBackground = true)
@Composable
fun RentalPostScreenPreview() {
    RentalPostScreen(navController = rememberNavController())
}

@Composable
fun RentalPostScreen(navController: NavHostController) {
    val list = FakeData().rooms
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    Column(
        modifier = Modifier
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RentalPostHeader()
        Spacer(modifier = Modifier.height(10.dp))
        RentalPostList(getRentalRoomList())
    }
}

