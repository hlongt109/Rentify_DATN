package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.Post
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import org.checkerframework.checker.units.qual.m

@Composable
fun AppointmentAppBar(
    onBackClick: () -> Unit,

) {
    val viewModel: PostViewModel = viewModel()
    var searchQuery by viewModel.searchQuery // Lấy trạng thái tìm kiếm từ ViewModel
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF)) // Background color
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material.IconButton(
                        onClick = { onBackClick() }
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

                    androidx.compose.material.Text(
                        text = "Danh sách bài đăng",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.h6
                    )
                }
//                IconButton(onClick = canlendarClick) {
//                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar", tint = Color(0xff5d5d5d))
//                }
            }
            SearchBarRentalPostRoom(
                searchText = searchQuery,
                onSearchTextChange = { searchQuery = it },
                onSearch = {
                    // Thực hiện logic tìm kiếm
                    Log.d("SearchBar", "Tìm kiếm với từ khóa: $searchQuery")
                }
            )
        }

    }
}
@Composable
fun SearchBarRentalPostRoom(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) } // Trạng thái focus
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) }, // Cập nhật text khi thay đổi
        textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .focusRequester(focusRequester) // Đăng ký focus requester
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }, // Theo dõi focus
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFf7f7f7), RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon tìm kiếm
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Placeholder khi trống và không focus
                    if (searchText.isEmpty() && !isFocused) {
                        Text(
                            text = "Nhập thông tin tìm kiếm...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    // Nội dung của TextField
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }

                    // Icon xóa (trailing icon)
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { onSearchTextChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search // Đặt hành động IME là Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() } // Thực hiện tìm kiếm khi nhấn nút "Search" trên bàn phím
        )
    )
}
