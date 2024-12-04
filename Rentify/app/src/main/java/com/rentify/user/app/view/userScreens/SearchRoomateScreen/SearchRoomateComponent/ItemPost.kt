package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.R
import com.rentify.user.app.model.Post
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.viewModel.UserViewmodel.PostUserViewModel

@Composable
fun PostListRoomateScreen(navController: NavController, postType: String) {
    val viewModel: PostUserViewModel = viewModel()
    val posts = viewModel.posts.value // Truy cập giá trị từ State

    // Gọi API khi màn hình được khởi chạy
    LaunchedEffect(postType) {
        viewModel.fetchPostsByType(postType)
        Log.d("Debug", "LaunchedEffect triggered with postType: $postType")
    }


    if (posts.isEmpty()) {
        Text(
            text = "Không có bài đăng nào",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyColumn {
            items(posts) { post ->
                // Sử dụng PostResponse để truyền vào ItemPost
                Log.d("UI posts", posts.toString())
                ItemPost(post = post)
            }
        }
    }
}


@Composable
fun ItemPost(post: PostResponse) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxWidth = screenWidth * 0.57f // Giới hạn 80% chiều rộng màn hình
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(color = Color.White)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color.Black
                )
                .align(Alignment.Center),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(0.dp), // Căn đầy chiều rộng và padding cho đẹp
                    horizontalArrangement = Arrangement.SpaceBetween, // Căn giữa đầu và cuối
                    verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                ) {
                    Column {
                        Text(
                            text = post.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.widthIn(max = maxWidth)
                        )
                        Text(
                            text = post.user?.name ?: "Nội dung trống",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xffB95533)
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp, 25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.mess),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp, 25.dp)
                        )
                    }
                //title
            }
                Spacer(modifier = Modifier.height(10.dp))
                Column{
                    Text(
                        text = "$ ${post.price?.toString()} VND / 1 người", // Chuyển giá trị price thành String
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xffB95533)
                    )
                    Text(
                        text = "Loại Phòng: ${post.room_type}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff9D9D9D)
                    )
                    Text(
                        text = "Khu vực : ${post.address}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff9D9D9D)
                    )
                }
            }
        }
    }
}

