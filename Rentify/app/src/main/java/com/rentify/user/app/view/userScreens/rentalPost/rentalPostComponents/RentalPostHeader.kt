package com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents

import android.util.Log.w
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.view.userScreens.appointment.SearchBar
import com.rentify.user.app.viewModel.RentalPostRoomViewModel

@Composable
fun RentalPostHeader(
    rentalPostRoomViewModel: RentalPostRoomViewModel,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    selectedRoomType: RoomType?,
    onRoomTypeSelected: (RoomType?) -> Unit,
    minPrice: Int?,
    maxPrice: Int?,
    onPriceRangeSelected: (Int?, Int?) -> Unit,
    sortBy: String?,
    onSortSelected: (String?) -> Unit,
    onFilterApplied: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RentalPostTopBar(navController)

        // Search bar
        SearchBarRentalPostRoom(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            onSearch = onFilterApplied
        )

        Spacer(modifier = Modifier.padding(5.dp))

        // Hiển thị loại phòng
        RentalPostRoomType(
            selectedRoomType = selectedRoomType,
            onRoomTypeSelected = {
                onRoomTypeSelected(it)
                onFilterApplied()
            }
        )

        RentalPostArrange(
            onPriceRangeSelected = { min, max ->
                onPriceRangeSelected(min, max)
                onFilterApplied()
            },
            onSortSelected = { selectedSort ->
                onSortSelected(selectedSort)
                onFilterApplied()
            }
        )
    }
}



@Composable
fun RentalPostTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Canh giữa theo chiều dọc
    ) {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        Text(
            text = "Tin đăng cho thuê",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
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
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (searchText.isEmpty() && !isFocused) { // Hiển thị placeholder khi ô trống và không được focus
                        Text(
                            text = "Nhập thông tin tìm kiếm",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField() // Hiển thị nội dung của TextField
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