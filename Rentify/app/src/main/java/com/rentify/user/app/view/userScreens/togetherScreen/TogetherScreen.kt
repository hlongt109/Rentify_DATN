package com.rentify.user.app.view.userScreens.togetherScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutComfort
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutInterior
import com.rentify.user.app.view.userScreens.togetherScreen.components.TogetherContent
import com.rentify.user.app.view.userScreens.togetherScreen.components.TogetherHeading
import com.rentify.user.app.view.userScreens.togetherScreen.components.TogetherLast

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TogetherScreenPreview() {
    TogetherScreen(navController = rememberNavController())
}

@Composable
fun TogetherScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
        TogetherHeading(navController)
        TogetherContent(navController)
//        LayoutComfort()
//        LayoutInterior()
        TogetherLast(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
    }

}