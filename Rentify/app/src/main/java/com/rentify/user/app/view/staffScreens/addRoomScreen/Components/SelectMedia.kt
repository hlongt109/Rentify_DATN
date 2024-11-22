package com.rentify.user.app.view.staffScreens.addRoomScreen.Components


import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background


import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.padding


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.rentify.user.app.R
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.rentify.user.app.utils.CheckUnit
import java.io.File




@Composable
fun SelectMedia(
    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
) {
    var selectedImages by rememberSaveable{ mutableStateOf(listOf<Uri>())}
    var selectedVideos by rememberSaveable {mutableStateOf(listOf<Uri>())}


    val context = LocalContext.current
    val launcherImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            uris?.let {
                val remainingSpace = 10 - selectedImages.size
                if (remainingSpace > 0) {
                    selectedImages = selectedImages + uris
                    onMediaSelected(selectedImages, selectedVideos)
                    Log.d("ImageUploadSelect", "Selected image URI: $uris")
                }
                if (it.size > remainingSpace) {
                    Toast.makeText(context, "Giới hạn tối đa 10 ảnh.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )
    val launcherVideo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let {
            try {
                // Lưu URI trực tiếp
                selectedVideos = selectedVideos + uri
                onMediaSelected(selectedImages, selectedVideos)
                Log.d("VideoUploadSelect", "Selected video URI: $uri")
            } catch (e: Exception) {
                Log.e("VideoUploadError", "Error selecting video: ${e.message}")
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // Phần chọn ảnh
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .clickable { launcherImage.launch(arrayOf("image/*"))}
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                    .padding(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                if (selectedImages.isEmpty()) {
                    Text(
                        text = "Thêm ảnh từ máy",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Giới hạn 10 ảnh",
                        color = Color(0xFFBFBFBF),
                        fontSize = 13.sp
                    )
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(selectedImages) { imageUri ->
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(imageUri),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.TopEnd)
                                    .background(Color.Red, shape = RoundedCornerShape(10.dp))
                                    .clickable {
                                        selectedImages.filterNot { it == imageUri}
                                        onMediaSelected(selectedImages, selectedVideos) // Gọi callback
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "X",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))


        // Phần chọn video
        Column(
            modifier = Modifier
                .clickable { launcherVideo.launch("video/*") }
                .fillMaxWidth()
                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedVideos.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.video),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "Video",
                    color = Color.Black,
                    fontSize = 13.sp
                )
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(selectedVideos) { videoUri ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Video",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd)
                                .background(Color.Red, shape = RoundedCornerShape(10.dp))
                                .clickable {
                                    selectedVideos.filterNot {it == videoUri}
                                    onMediaSelected(selectedImages, selectedVideos) // Gọi callback
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "X",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
