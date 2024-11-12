package com.rentify.user.app.view.policyScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PolicyBottomSheet() {
  PolicyBottomSheetContent(onClose = {})
}

@Composable
fun PolicyBottomSheetContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Điều khoản và chính sách", fontSize = 18.sp, color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /* Handle "Chính sách bảo mật" action */ },
            modifier = Modifier.fillMaxWidth().heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1)),

        ) {
            Text("Chính sách bảo mật")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Handle "Điều khoản sử dụng" action */ },
            modifier = Modifier.fillMaxWidth().heightIn(min = 45.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1))
        ) {
            Text("Điều khoản sử dụng")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Handle "Quy chế hoạt động" action */ },
            modifier = Modifier.fillMaxWidth().heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1))
        ) {
            Text("Quy chế hoạt động")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("Đóng", color = Color.White)
        }
    }
}