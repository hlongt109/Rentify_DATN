package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.location_permission

@Composable
fun NotPermissionPosition(
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(location_permission),
                contentDescription = "Location Permission",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = "Bạn cần cấp quyền truy cập vị trí để có thể sử dụng chức năng này",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorBlack,
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Button(
                onClick = {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        // Yêu cầu lại quyền nếu người dùng chưa chọn "Don't ask again"
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        // Nếu người dùng đã chọn "Don't ask again", mở cài đặt ứng dụng
                        context.startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonColors(
                    contentColor = colorLocation,
                    containerColor = colorLocation,
                    disabledContentColor = colorLocation,
                    disabledContainerColor = colorLocation
                )
            ) {
                Text(
                    text = "Đồng ý",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}