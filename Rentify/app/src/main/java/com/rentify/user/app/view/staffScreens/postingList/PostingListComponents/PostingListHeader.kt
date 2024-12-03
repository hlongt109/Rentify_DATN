package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.Post
import com.rentify.user.app.view.contract.contractComponents.ContractSearchBar
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import org.checkerframework.checker.units.qual.m

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PostingListTopBar(navController: NavHostController, viewModel: PostViewModel) {
    val searchQuery by viewModel.searchQuery // Lấy trạng thái tìm kiếm từ ViewModel

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 10.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { navController.navigate("HOME_STAFF") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
            PostingListSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    viewModel.onSearchQueryChange(query) // Cập nhật trạng thái tìm kiếm khi thay đổi
                }
            )
            IconButton(onClick = { /* Xử lý sự kiện khi nhấn nút sort */ }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort"
                )
            }
        }
    }
}

@Composable
fun PostingListSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TextField(
        singleLine = true,
        value = searchQuery,
        onValueChange = onSearchQueryChange, // Gọi callback khi giá trị thay đổi
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                tint = Color.Gray,
                contentDescription = "Nhập thông tin tìm kiếm"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Nhập thông tin tìm kiếm...",
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
    )
}
