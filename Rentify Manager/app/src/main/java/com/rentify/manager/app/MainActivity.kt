package com.rentify.manager.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rentify.manager.app.ui.theme.RentifyManagerTheme
import com.rentify.manager.app.view.navigator.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RentifyManagerTheme {
                AppNavigation()
            }
        }
    }
}
