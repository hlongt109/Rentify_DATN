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
import com.rentify.user.app.view.navigator.ROUTER
import com.rentify.user.app.view.userScreens.BillScreen.BillScreen
import com.rentify.user.app.view.userScreens.BillScreen.PaidScreen
import com.rentify.user.app.view.userScreens.BillScreen.UnPaidScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FilterScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.PostRoomScreen
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
//            PostRoomScreen(navController = rememberNavController())
//            FilterScreen(navController = rememberNavController())
//            BillScreen(navController = rememberNavController())
            TestNavigation()
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ROUTER.HOME.name) {
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
            composable(ROUTER.FILTER.name) {
                FilterScreen(navController)
            }
            composable(ROUTER.SEARCHSCREEN.name) {
                PostRoomScreen(navController)
            }
        }
    }

    @Composable
    fun TestNavigation() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ROUTER.BILL.name
        ) {
            composable(ROUTER.BILL.name) {
                BillScreen(navController)
            }
            composable(ROUTER.UNPAID.name) {
                UnPaidScreen()
            }
            composable(ROUTER.PAID.name) {
                PaidScreen()
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
        REGISTER,
        FILTER,
        SEARCHSCREEN,
        UNPAID,
        PAID,
        BILL
    }
}