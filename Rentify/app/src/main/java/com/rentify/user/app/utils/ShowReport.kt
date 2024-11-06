package com.rentify.user.app.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ShowReport {
    @Composable
    fun ShowError(message: String) {
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}