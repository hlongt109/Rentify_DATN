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
import com.rentify.user.app.view.userScreens.chatScreen.TinnhanScreen
import com.rentify.user.app.view.userScreens.laundryScreen.LaundryScreen
import com.rentify.user.app.view.userScreens.laundrydetailScreen.LaundryDetailScreenScreen
import com.rentify.user.app.view.userScreens.messengerScreen.LayoutMessenger
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.profileScreen.ProfileScreen
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.roomdetailScreen.LayoutRoomdetails
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService
import com.rentify.user.app.view.userScreens.togetherScreen.TogetherScreen

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
        NavHost(navController = navController, startDestination = ROUTER.TogeTher.name) {
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
            composable(ROUTER.TogeTher.name) {
                TogetherScreen(navController = navController)
            }
        }
    }

    enum class ROUTER {
        HOME, // trangChu
        SERVICE,// dịch vụ
        RENT,// thuê
        MESSENGER,// tin nhắn
        PERSONAL, // trang cá nhân
        ROOMDETAILS,// chi tiết phòng
        INTRO,//intro
        SPLASH,// màn hinh chào
        RESGITER,//đăng ký
        LOGIN,//đăng nhập
        FORGOTPASS,//đổi mật kẩu
        PROFILE,//hồ sơ
        LAUDRY,// giặt là
        LAUDRYDETAIL,// chi tiết giặt là
        TINNHAN,// đoạn chat tin nhắn
        TogeTher,// tìm bạn ở ghép
        PaymentConfirmation,// xác nhận thanh toán
        Payments,// thanh toán
        ConTract,// hợp đồng
    }
}