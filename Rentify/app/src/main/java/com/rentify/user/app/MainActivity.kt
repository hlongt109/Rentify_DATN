package com.rentify.user.app


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.navigator.AppNavigation
import com.rentify.user.app.view.userScreens.messengerScreem.LayoutMessenger
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService


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
    }
}

enum class ROUTER {
    HOME, SERVICE, RENT, MESSENGER, PERSONAL,
}
