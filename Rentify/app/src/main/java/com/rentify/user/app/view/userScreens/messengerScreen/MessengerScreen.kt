package com.rentify.user.app.view.userScreens.messengerScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.messengerScreen.components.MessengerComponent
import com.rentify.user.app.view.userScreens.messengerScreen.components.headcomponent
import com.rentify.user.app.viewModel.UserViewmodel.ChatViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessengerScreen() {
    LayoutMessenger(navController= rememberNavController())
}

@Composable
fun LayoutMessenger(navController: NavHostController) {
    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModel.ChatViewModelFactory())
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        headcomponent(navController)
        MessengerComponent(navController, chatViewModel)
    }

}