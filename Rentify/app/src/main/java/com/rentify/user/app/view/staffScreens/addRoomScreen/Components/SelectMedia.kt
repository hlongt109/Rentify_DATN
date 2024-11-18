package com.rentify.user.app.view.staffScreens.addRoomScreen.Components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material3.IconButton


import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R

import androidx.compose.material3.Icon

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

import com.google.accompanist.flowlayout.FlowRow
@Composable
fun SelectMedia(
    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
) {
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val selectedVideos = remember { mutableStateListOf<Uri>() }

    val context = LocalContext.current
    // Launcher cho viá»‡c chá»n nhiá»u áº£nh tá»« album
    val launcherImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            uris?.let {
                // ThÃªm áº£nh Ä‘Ã£ chá»n vÃ o danh sÃ¡ch
                selectedImages.addAll(it)
            }
        }
    )

    // Launcher cho viá»‡c chá»n nhiá»u video tá»« album
    val launcherVideo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            uris?.let {
                // ThÃªm video Ä‘Ã£ chá»n vÃ o danh sÃ¡ch
                selectedVideos.addAll(it)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // NÃºt chá»n nhiá»u áº£nh tá»« album
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .clickable { launcherImage.launch(arrayOf("image/*")) } // "image/*" Ä‘á»ƒ chá»n áº£nh tá»« album
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
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
                Text(
                    text = "áº¢nh PhÃ²ng trá»",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Tá»‘i Ä‘a 10 áº£nh",
                    color = Color(0xFFBFBFBF),
                    fontSize = 13.sp
                )
            }
        }
        // Hiá»ƒn thá»‹ áº£nh Ä‘Ã£ chá»n
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "áº¢nh Ä‘Ã£ chá»n:",
            color = Color.Black,
            fontSize = 16.sp
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(selectedImages) { imageUri ->
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(17.dp))

        // NÃºt chá»n nhiá»u video tá»« album
        Column(
            modifier = Modifier
                .clickable { launcherVideo.launch(arrayOf("video/*")) } // "video/*" Ä‘á»ƒ chá»n video tá»« album
                .fillMaxWidth()
                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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



        // Hiá»ƒn thá»‹ video Ä‘Ã£ chá»n
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Video Ä‘Ã£ chá»n:",
            color = Color.Black,
            fontSize = 16.sp
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(selectedVideos) { videoUri ->
                // Sá»­ dá»¥ng ExoPlayer hoáº·c má»™t thÆ° viá»‡n khÃ¡c Ä‘á»ƒ hiá»ƒn thá»‹ video
                // VÃ­ dá»¥ dÆ°á»›i Ä‘Ã¢y chá»‰ lÃ  placeholder
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Video",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
