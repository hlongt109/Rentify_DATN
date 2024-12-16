package com.rentify.user.app.view.userScreens.messengerScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.messengerScreen.components.MessengerComponent
import com.rentify.user.app.view.userScreens.messengerScreen.components.headcomponent
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.ChatViewModel
import com.rentify.user.app.viewModel.UserViewmodel.chatUser

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessengerScreen() {
    LayoutMessenger(navController= rememberNavController())
}

@Composable
fun LayoutMessenger(navController: NavHostController) {
    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModel.ChatViewModelFactory())
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val currentUserId = loginViewModel.getUserData().userId
    val usersList = remember { mutableStateOf<List<chatUser>>(emptyList()) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        MessengerComponent(
            navController,
        )
    }
}
