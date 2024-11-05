package com.rentify.user.app.view.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            IntroScreen()
        }
    }
}