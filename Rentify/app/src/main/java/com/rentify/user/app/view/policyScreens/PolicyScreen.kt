package com.rentify.user.app.view.policyScreens

import android.R.attr.fontWeight
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Preview (showBackground = true)
@Composable
fun PolicyScreenPreview() {
    PolicyScreen(navController = rememberNavController())
}
@Composable
fun PolicyScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.chinhsach),
            contentDescription = "Intro Screen",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = "Có lỗi xảy ra",
            fontSize = 25.sp,
            color = Color.Red,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Đã xảy ra lỗi trong quá trình tải trang, bạn vui lòng thử lại hoặc liên hệ Rentify Support để được hỗ trợ",
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(horizontal = 20.dp)

        )

        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Quay lại trang chủ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).
            clickable() {
                //TODO
            }
        )
    }

}
