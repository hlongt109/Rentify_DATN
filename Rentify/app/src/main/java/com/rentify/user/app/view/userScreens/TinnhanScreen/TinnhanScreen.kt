package com.rentify.user.app.view.userScreens.TinnhanScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rentify.user.app.view.userScreens.TinnhanScreen.components.headcomponent
import com.rentify.user.app.view.userScreens.TinnhanScreen.components.sentComponent
import com.rentify.user.app.view.userScreens.TinnhanScreen.components.tinnhanComponent

@Composable
fun TinnhanScreen(navController: NavHostController) {
    val messages = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        headcomponent(navController)
        tinnhanComponent(messages)
        sentComponent { message ->
            messages.add(message)
        }
    }
}
