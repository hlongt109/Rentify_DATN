package com.rentify.user.app.view.userScreens.TinnhanScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun tinnhanComponent(messages: List<String>) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .verticalScroll(scrollState)
    ) {
        // Display messages
        messages.forEach { message ->
            Text(text = message)
        }
    }
}
