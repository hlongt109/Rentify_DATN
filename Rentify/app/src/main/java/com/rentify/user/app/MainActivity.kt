package com.rentify.user.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.ui.theme.RentifyTheme
import com.rentify.user.app.view.auth.RegisterScreen
import com.rentify.user.app.view.navigator.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RentifyTheme {
                RegisterScreen(navController = rememberNavController())
            }
        }
    }
}

