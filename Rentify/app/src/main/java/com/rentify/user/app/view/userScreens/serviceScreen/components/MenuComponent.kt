package com.rentify.user.app.view.userScreens.serviceScreen.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


data class TypeProduct(val type: String, val icon: Int)


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MenuComponent() {
    LayoutMenu()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMenu() {

}