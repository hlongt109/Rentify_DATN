package com.rentify.user.app.view.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IntroScreenPreview() {
    IntroScreen(navController = rememberNavController())
}

@Composable
fun IntroScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    // Effect để check login status khi màn hình được tạo
//    LaunchedEffect(Unit) {
//        loginViewModel.checkLoginStatus()
//    }

    val initialRoute by loginViewModel.initialRoute.observeAsState()
    LaunchedEffect(initialRoute) {
        initialRoute?.let { route ->
            navController.navigate(route) {
                popUpTo(MainActivity.ROUTER.SPLASH.name) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    if (initialRoute == null) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painterResource(R.drawable.g),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        // Workaround to enable alpha compositing
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithContent {
                            val colors = listOf(
                                Color.Black,
                                Color.Transparent
                            )
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    startY = size.height - 120.dp.toPx(),
                                    endY = size.height,
                                    colors = colors
                                ),
                                blendMode = BlendMode.DstIn
                            )

                        }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom

            ) {
                Text(
                    text = "NƠI MỚI, NHÀ MỚI !",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Chào mừng bạn đến với Rentify. Cùng bắt đầu nào!",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(30.dp))

                ElevatedButton(
                    onClick = {
                        navController.navigate("LOGIN")
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(color = 0xFF209FA8)
                    ),
                    modifier = Modifier
                        .size(width = 300.dp, height = 50.dp)
                        .clip(RoundedCornerShape(30.dp))

                ) {
                    Text(
                        text = "ĐĂNG NHẬP",
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                ElevatedButton(
                    onClick = {
                        navController.navigate("RESGITER")
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White, // Set the container color to green
                    ),
                    modifier = Modifier
                        .size(width = 300.dp, height = 50.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {
                    Text(
                        text = "ĐĂNG KÝ",
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

            }
        }
    }
}
//