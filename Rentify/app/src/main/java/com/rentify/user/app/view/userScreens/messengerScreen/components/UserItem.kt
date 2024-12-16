package com.rentify.user.app.view.userScreens.messengerScreen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import com.rentify.user.app.utils.localUrl
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomDetailViewModel
import com.rentify.user.app.viewModel.UserViewmodel.chatUser

@Composable
fun UserItem(
    user: LoginResponse,
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 7.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = Color.White)
                .clickable {
                    navController.navigate("TINNHAN/${user._id}/${user.name}")
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = localUrl + user.profile_picture_url,
                    builder = {
                        crossfade(true)
                        fallback(R.drawable.default_avatar)
                        error(R.drawable.default_avatar)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(50.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = user.name,
                    fontSize = 16.sp, // Font size giảm một chút
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
