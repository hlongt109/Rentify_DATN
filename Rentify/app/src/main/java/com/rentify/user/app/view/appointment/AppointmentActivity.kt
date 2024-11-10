package com.rentify.user.app.view.appointment

import Test
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rentify.user.app.view.intro.IntroScreen

class AppointmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AppointmentScreen()
        }
    }
}