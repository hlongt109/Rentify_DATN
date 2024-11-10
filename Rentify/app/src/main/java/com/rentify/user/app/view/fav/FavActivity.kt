package com.rentify.user.app.view.fav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rentify.user.app.view.appointment.AppointmentScreen
import org.checkerframework.checker.units.qual.m

class FavActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            FavScreen()
        }
    }
}