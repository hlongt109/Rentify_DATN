package com.rentify.user.app.view.policyScreens

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rentify.user.app.view.intro.IntroScreen

class PolicyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PolicyScreen()
        }
    }
}