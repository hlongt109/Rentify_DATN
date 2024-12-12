package com.rentify.user.app.view.staffScreens.ReportScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.AppointmentAppBarc
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.BodyReport
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.HeadReport

// _vanphuc : màn hình báo cáo sự cố
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReportScreen(){
    ReportScreen(navController = rememberNavController())
}
@Composable
fun ReportScreen(navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ){
        AppointmentAppBarc( onBackClick = {
            // Logic quay lại, ví dụ: điều hướng về màn hình trước
            navController.navigate("HOME_STAFF")
            {
                //    popUpTo("ADDCONTRAC_STAFF") { inclusive = true }

            }
        })
        BodyReport(navController)
    }
}