package com.rentify.user.app.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import com.rentify.user.app.network.ApiClient
import com.rentify.user.app.network.RegisterRequest
import com.rentify.user.app.network.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    // Biến trạng thái cho từng TextField
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRepasswordVisible by remember { mutableStateOf(false) }
    // Biến để theo dõi trạng thái focus cho từng TextField
    var isNameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isRePasswordFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffffffff)
            )
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //icone
        Row(
            modifier = Modifier.fillMaxWidth(), // Để IconButton nằm bên trái
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        //text
        //Spacer(modifier = Modifier.fillMaxHeight(0.1f)) // Thêm khoảng cách giữa hai Text

        Column(
            modifier = Modifier.fillMaxWidth(),
            ) {
            Text(
                text = "Chào mừng bạn đến với Rentify",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(8.dp)) // Thêm khoảng cách giữa hai Text

            Text(
                text = "Hãy đăng ký tài khoản của bạn để bắt đầu nào ! ",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.07f)) // Thêm khoảng cách giữa hai Text
            // Thêm khoảng cách giữa hai Text
            Text(
                text = "Đăng ký",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text
        }

        Column(
            modifier = Modifier.fillMaxWidth()

        ) {
            TextField(
                value = username,
                onValueChange = {  username=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isNameFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isNameFocused = focusState.isFocused },


                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Name",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )


        }
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text
        //emai
        Column(
            modifier = Modifier.fillMaxWidth()

        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isEmailFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isEmailFocused = focusState.isFocused },


                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Email",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )


        }
        //pass
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(value = password, onValueChange = {
                password = it
            }, modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = if (isPasswordFocused) Color(0xFF56b5bc) else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged { focusState -> isPasswordFocused = focusState.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ), placeholder = {
                    Text(
                        text = "Password",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),

                        )
                }, shape = RoundedCornerShape(size = 8.dp), trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.show else R.drawable.an
                            ), contentDescription = null, modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }, visualTransformation = if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(), textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )
        }
        //nhập lại pass
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text


        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                value = repassword, onValueChange = {
                    repassword = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isRePasswordFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isRePasswordFocused = focusState.isFocused },

                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ), placeholder = {
                    Text(
                        text = "Re-enter password",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        style = TextStyle(

                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                },

                shape = RoundedCornerShape(size = 8.dp), trailingIcon = {
                    IconButton(onClick = { isRepasswordVisible = !isRepasswordVisible }

                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isRepasswordVisible) R.drawable.show else R.drawable.an
                            ), contentDescription = null, modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }, visualTransformation = if (isRepasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(), textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )

            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.1f)) // Thêm khoảng cách giữa hai Text
//đăng ký

        Button(
            onClick = {
                // Kiểm tra các trường không được để trống
                if (username.isBlank() || email.isBlank() || password.isBlank() || repassword.isBlank()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin đăng ký.", Toast.LENGTH_SHORT).show()
                } else if (password != repassword) {
                    Toast.makeText(context, "Mật khẩu và mật khẩu nhập lại không khớp.", Toast.LENGTH_SHORT).show()
                } else {
                    // Thực hiện đăng ký khi các trường hợp lệ và mật khẩu khớp
                    val registerRequest = RegisterRequest(
                        username = username,
                        email = email,
                        password = password
                    )

                    ApiClient.apiService.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
//                            val responseBody = response.body()
//                            println("Phản hồi đăng ký: $responseBody")
                            if (response.isSuccessful) {
                                // Xử lý khi đăng ký thành công
                                val user = response.body()?.user
                                // Chuyển hướng hoặc thông báo cho người dùng
                              //  Toast.makeText(context, "Đăng ký thành công: ${user?.username}", Toast.LENGTH_SHORT).show()
                                Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            } else {
                                // Xử lý khi có lỗi
                                println("Lỗi đăng ký: ${response.message()}")
                                Toast.makeText(context, "Lỗi đăng ký: ${response.message()}", Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            // Xử lý lỗi kết nối
                            Toast.makeText(context, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()

                        }
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            //  .background(Color(0xffFE724C), RoundedCornerShape(25.dp)), // Bo tròn 12.dp

            shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff209FA8)
            )
        ) {
            Text(
                text = "Đăng ký",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color(0xffffffff)
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.2f)) // Thêm khoảng cách giữa hai Text

//text đăng nhập
        Row {
            Text(
                text = "Bạn có tài khoản rồi!",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black
            )

            Text(
                text = "Đăng nhập",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color(0xff209FA8),
                        modifier = Modifier
                        .clickable {
                    // Hành động khi người dùng nhấp vào nút
                    // Ví dụ: chuyển đến trang đăng ký
                  //  navController.navigate("register")
                }
            )

        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingGetLayoutRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}
//            BasicTextField(
//                value = email,
//                onValueChange = { email = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(47.dp) // Chiều cao nhỏ hơn
//                    .background(Color.White, RoundedCornerShape(8.dp))
//                    .padding(horizontal = 8.dp, vertical = 8.dp), // Thêm padding để đảm bảo văn bản không bị cắt
//                singleLine = true,
//                textStyle = TextStyle(
//                    color = Color.Black,
//                    fontSize = 15.sp,
//                    fontFamily = FontFamily(Font(R.font.cairo_regular))
//                ),
//                decorationBox = { innerTextField ->
//                    if (email.isEmpty()) {
//                        Text(
//                            text = "Enter your email",
//                            color = Color.Gray,
//                            fontSize = 15.sp,
//                            fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    }
//                    innerTextField() // Hiển thị nội dung nhập của người dùng
//                }
//            )