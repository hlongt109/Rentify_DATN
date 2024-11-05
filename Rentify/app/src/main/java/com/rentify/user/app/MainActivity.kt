package com.rentify.user.app

import MyScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.LoginScreenApp
import com.rentify.user.app.view.auth.RegisterScreen

import com.rentify.user.app.view.navigator.AppNavigation
import com.rentify.user.app.view.userScreens.SearchRoomScreen.ListPostRoomScreen
import com.rentify.user.app.view.userScreens.messengerScreem.LayoutMessenger
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MainNavigation()
//            MyScreen()
            ListPostRoomScreen(navController = rememberNavController())
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ROUTER.LOGIN.name) {
            composable(ROUTER.HOME.name) {
                AppNavigation(navController)
            }
            composable(ROUTER.SERVICE.name) {
                LayoutService(navController = navController)
            }
            composable(ROUTER.RENT.name) {
                LayoutRent(navController = navController)
            }
            composable(ROUTER.MESSENGER.name) {
                LayoutMessenger(navController = navController)
            }
            composable(ROUTER.PERSONAL.name) {
                LayoutPersonal(navController = navController)
            }
            composable(ROUTER.LOGIN.name) {
                LoginScreenApp(navController)
            }
            composable(ROUTER.REGISTER.name) {
                RegisterScreen(navController)
            }
        }
    }

    enum class ROUTER {
        HOME,
        SERVICE,
        RENT,
        MESSENGER,
        PERSONAL,
        LOGIN,
        REGISTER
    }
}