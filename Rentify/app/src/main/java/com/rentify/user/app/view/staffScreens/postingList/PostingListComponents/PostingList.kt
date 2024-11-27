@file:OptIn(ExperimentalMaterial3Api::class)

package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import android.system.Os.remove
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

@Preview (showBackground = true)
@Composable
fun PostListScreen() {
    var rooms by remember { mutableStateOf(getPostingList()) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(rooms) { room ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {

                        true
                    }
                    else {
                        false
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Red),
                        contentAlignment = Alignment.CenterEnd
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp).padding(end = 10.dp)
                        )
                    }
                },
                content = {
                    PostingListCard(room = room)
                }

            )

        }
    }
}

@Composable
fun PostingListCard(room: PostingList) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = room.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Từ ${room.price}",
                    color = Color.Red,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF03A9F4),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${room.address}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// Data model for room
data class PostingList(
    val name: String,
    val price: String,
    val address: String,
)

// Sample data for room list
fun getPostingList(): List<PostingList> {
    return listOf(
        PostingList(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "27/143 Xuân Phương, Hà Nội",
        ),
        PostingList(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "27/143 Xuân Phương, Hà Nội",

            ),
        PostingList(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "27/143 Xuân Phương, Hà Nội",

            )
    )
}





