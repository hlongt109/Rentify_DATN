package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import LocationViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Location
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.search
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FakeData
import java.text.Normalizer

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChangeLocation(
    locations: List<String>,
    title: String,
    onItemSelected: (String) -> Unit,
    onKeyboardVisibilityChanged: (Boolean) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onLocationSelected: (Province) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val imeVisible = WindowInsets.isImeVisible
    var searchQuery by remember { mutableStateOf("") }

    // Hàm chuyển đổi chuỗi có dấu thành không dấu
    fun String.removeDiacritics(): String {
        return Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("đ", "d")
            .replace("Đ", "D")
            .replace(Regex("\\p{M}"), "") // Loại bỏ dấu
    }

    // Lọc kết quả với hỗ trợ tìm kiếm có dấu và không dấu
    val filteredLocation = remember(searchQuery, locations) {
        if (searchQuery.isEmpty()) {
            locations
        } else {
            val normalizedQuery = searchQuery.removeDiacritics().lowercase()
            locations.filter { location ->
                location.removeDiacritics().lowercase().contains(normalizedQuery) ||
                        location.lowercase().contains(searchQuery.lowercase())
            }
        }
    }


    LaunchedEffect(imeVisible) {
        onKeyboardVisibilityChanged(imeVisible)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .width(screenWidth.dp / 1.2f)
                    .height(55.dp)
                    .background(color = textFieldBackgroundColor, shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = colorInput, shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(search),
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = title,
                            color = colorText
                        )
                    },
                    modifier = Modifier
                        .width(screenWidth.dp / 1.5f)
                        .onFocusChanged { focusState ->
                            onFocusChanged(focusState.isFocused)
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textFieldBackgroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // Nếu chỉ có một kết quả tìm kiếm, tự động chọn
                            if (filteredLocation.size == 1) {
                                onItemSelected(filteredLocation.first())
                            }
                        }
                    )
                )
            }

            if (filteredLocation.isEmpty() && searchQuery.isNotEmpty()) {
                // Hiển thị thông báo không tìm thấy kết quả
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không tìm thấy địa điểm",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    items(filteredLocation) { location ->
                        ItemLocation(
                            name = location,
                            isSelected = false,
                            onClick = {
                                searchQuery = "" // Reset search query after selection
                                onItemSelected(location)
                            }
                        )
                    }
                }
            }

//            LazyColumn {
//                items(locations) { location ->
//                    ItemLocation(
//                        name = location,
//                        isSelected = false,
//                        onClick = { onItemSelected(location) }
//                    )
//                }
//            }
        }
    }
}





