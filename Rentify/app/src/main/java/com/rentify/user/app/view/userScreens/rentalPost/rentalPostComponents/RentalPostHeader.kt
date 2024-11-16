package com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents

import android.util.Log.w
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RentalPostHeader() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RentalPostTopBar()
        RentalPostSearchBar()
        RentalPostArrange()
        RentalPostRoomType()

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalPostTopBar() {
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                "Tin đăng cho thuê",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun RentalPostSearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        singleLine = true,
        value = searchText,
        onValueChange = { searchText = it },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Nhập tên Quận/Huyện..."
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Nhập tên Quận/Huyện...",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 3.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(15.dp)),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )

    )
}
