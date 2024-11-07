package com.rentify.user.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.ForgotPasswordScreen
import com.rentify.user.app.view.auth.LoginScreenApp
import com.rentify.user.app.view.auth.RegisterScreen
import com.rentify.user.app.view.intro.IntroScreen
import com.rentify.user.app.view.intro.SplashScreen

import com.rentify.user.app.view.navigator.AppNavigation
import com.rentify.user.app.view.userScreens.TinnhanScreen.TinnhanScreen
import com.rentify.user.app.view.userScreens.laundryScreen.LaundryScreen
import com.rentify.user.app.view.userScreens.laundrydetailscreen.LaundryDetailScreenScreen
import com.rentify.user.app.view.userScreens.messengerScreen.LayoutMessenger
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.profilescreen.ProfileScreen
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.LayoutRoomdetails
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ROUTER.HOME.name) {
            composable(ROUTER.SPLASH.name) {
                SplashScreen(navController = navController)
            }
            composable(ROUTER.INTRO.name) {
                IntroScreen(navController = navController)
            }
            composable(ROUTER.LOGIN.name) {
                LoginScreenApp(navController = navController)
            }
            composable(ROUTER.RESGITER.name) {
                RegisterScreen(navController = navController)
            }
            composable(ROUTER.FORGOTPASS.name) {
                ForgotPasswordScreen(navController = navController)
            }
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
            composable(ROUTER.ROOMDETAILS.name) {
                LayoutRoomdetails(navController = navController)
            }
            composable(ROUTER.PROFILE.name) {
                ProfileScreen(navController = navController)
            }
            composable(ROUTER.LAUDRY.name) {
                LaundryScreen(navController = navController)
            }
            composable(ROUTER.LAUDRYDETAIL.name) {
                LaundryDetailScreenScreen(navController = navController)
            }
            composable(ROUTER.TINNHAN.name) {
                TinnhanScreen(navController = navController)
            }
        }
    }

    enum class ROUTER {
        HOME,
        SERVICE,
        RENT,
        MESSENGER,
        PERSONAL,
        ROOMDETAILS,
        INTRO,
        SPLASH,
        RESGITER,
        LOGIN,
        FORGOTPASS,
        PROFILE,
        LAUDRY,
        LAUDRYDETAIL,
        TINNHAN
    }
}