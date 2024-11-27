package com.rentify.user.app.view.staffScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// quản lý các màn hình của nhân viên
// em làm các màn hình thì gom lại như ví dụ thư mục personalScreen để quản lý cho dễ
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    Screen(navController= rememberNavController())
}
@Composable
fun Screen(navController: NavHostController){
}




