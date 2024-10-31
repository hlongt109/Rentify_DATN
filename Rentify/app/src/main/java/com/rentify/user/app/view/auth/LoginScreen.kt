package com.rentify.user.app.view.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import com.rentify.user.app.view.auth.components.HeaderComponent


class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreenApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreenApp() {
        var text by remember { mutableStateOf("") }// Biến lưu giá trị nhập vào
        var isFocusedEmail by remember { mutableStateOf(false) }
        var isFocusedPass by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Spacer(modifier = Modifier.padding(top = 30.dp))
                HeaderComponent(backgroundColor = Color.White, title = "")

                Spacer(modifier = Modifier.padding(top = 50.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                ) {
                    Text(
                        text = "Chào mừng quay trở lại !",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Text(
                        text = "Hãy đăng nhập vào tài khoản của bạn để bắt đầu nào ! ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                }

                Spacer(modifier = Modifier.padding(top = 50.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        text = "Đăng nhập",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    //email
                    TextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        placeholder = { Text(text = "Nhập email của bạn") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(
                                width = 1.dp,
                                color = if (isFocusedEmail) greenInput else Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .focusable()
                            .onFocusChanged { focusState -> isFocusedEmail = focusState.isFocused },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = textFieldBackgroundColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = greenInput
                        ),
                        shape = RoundedCornerShape(20.dp),
                    )

                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    //password
                    TextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        placeholder = { Text(text = "Nhập mật khẩu của bạn") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(
                                width = 1.dp,
                                color = if (isFocusedPass) greenInput else Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .focusable()
                            .onFocusChanged { focusState -> isFocusedPass = focusState.isFocused },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = textFieldBackgroundColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = greenInput
                        ),
                        shape = RoundedCornerShape(20.dp),
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    LoginScreenApp()
}