package com.rentify.user.app.view.userScreens.chatScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TinnhanScreenPreview() {
    TinnhanScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TinnhanScreen(navController: NavHostController) {
    val messages = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .imePadding()
    ) {


        // Header
        Column (
            modifier = Modifier.fillMaxWidth()
                .height(100.dp)
                .background(color = Color(0xff84d8ff))
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .background(color = Color(0xff84d8ff)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Quay lại",
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Nguyễn Thiên Thiên",
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        // Scrollable message area
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Chiếm không gian còn lại
                .background(color = Color(0xfff7f7f7))
                .verticalScroll(scrollState)
        ) {
            messages.forEach { message ->
                Text(
                    text = message,
                    modifier = Modifier.padding(8.dp), // Thêm padding để dễ đọc hơn
                    color = Color.Black
                )
            }
        }

        // Input area
        var sentText by remember { mutableStateOf(TextFieldValue("")) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = sentText,
                onValueChange = { sentText = it },
                placeholder = { Text("Nhập tin nhắn ở đây", color = Color.Gray) },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp) // Kích thước lớn hơn icon
                                .background(
                                    color = Color(0xfffe9a00),
                                    shape = RoundedCornerShape(20.dp)
                                ), // Nền đỏ với góc tròn
                            contentAlignment = Alignment.Center // Căn giữa icon trong box
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.addgim),
                                contentDescription = "Thêm",
                                modifier = Modifier.size(24.dp) // Kích thước icon
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(40.dp) // Kích thước lớn hơn icon
                                .background(
                                    color = Color(0xFF44acfe),
                                    shape = RoundedCornerShape(20.dp)
                                ), // Nền đỏ với góc tròn
                            contentAlignment = Alignment.Center // Căn giữa icon trong box
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sent),
                                contentDescription = "Gửi",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (sentText.text.isNotEmpty()) {
                                            messages.add(sentText.text) // Thêm tin nhắn vào danh sách
                                            sentText = TextFieldValue("") // Xóa nội dung TextField
                                        }
                                    }
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xffdfdfdf),
                        shape = RoundedCornerShape(20.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}
