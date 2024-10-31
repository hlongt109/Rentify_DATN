package com.rentify.user.app.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SplashScreen()
        }
        lifecycleScope.launch {
            delay(2000) // Delay for 2 seconds (adjust as needed)
            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            finish() // Finish the splash activity
        }
    }
}