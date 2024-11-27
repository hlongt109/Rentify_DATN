package com.rentify.user.app.view.userScreens.rentalPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostHeader
import com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents.RentalPostList
import com.rentify.user.app.viewModel.RentalPostRoomViewModel

@Preview(showBackground = true)
@Composable
fun RentalPostScreenPreview() {
    RentalPostScreen(navController = rememberNavController())
}

@Composable
fun RentalPostScreen(
    navController: NavHostController,
    rentalPostRoomViewModel: RentalPostRoomViewModel = viewModel()
) {
    val listRoom by rentalPostRoomViewModel.roomList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RentalPostHeader(
            rentalPostRoomViewModel = rentalPostRoomViewModel
        )
        Spacer(modifier = Modifier.height(10.dp))
        RentalPostList(
            getRentalPostList = listRoom
        )
    }
}

