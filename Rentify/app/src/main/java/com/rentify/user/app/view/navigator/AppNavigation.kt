package com.rentify.user.app.view.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.view.userScreens.homeScreen.LayoutHome
import com.rentify.user.app.view.userScreens.messengerScreen.LayoutMessenger
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.auth.LoginScreenApp
import com.rentify.user.app.view.auth.RegisterScreen
import com.rentify.user.app.viewModel.LoginViewModel

enum class ROUTER {
    HOME,
    SERVICE,
    RENT,
    MESSENGER,
    PERSONAL,
}
@Composable
fun AppNavigation(navHostController: NavHostController) {
    var isSelected by rememberSaveable { mutableStateOf(ROUTER.HOME.name) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                modifier = Modifier.height(100.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Trang chủ
                    NavigationBarItem(
                        selected = isSelected == ROUTER.HOME.name,
                        onClick = {
                            isSelected = ROUTER.HOME.name
                            navController.navigate(ROUTER.HOME.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.home),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = "Home",color = if (isSelected == ROUTER.HOME.name) Color(0xFF059BEE) else Color(color = 0xFFb7b7b7))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color(color = 0xFFb7b7b7),
                            indicatorColor = Color.White
                        ),
                    )

                    // Dịch vụ
                    NavigationBarItem(
                        selected = isSelected == ROUTER.SERVICE.name,
                        onClick = {
                            isSelected = ROUTER.SERVICE.name
                            navController.navigate(ROUTER.SERVICE.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.sevice),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = "Service",color = if (isSelected == ROUTER.SERVICE.name) Color(0xFF059BEE) else Color(color = 0xFFb7b7b7))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color(color = 0xFFb7b7b7),
                            indicatorColor = Color.White
                        )
                    )

                    NavigationBarItem(
                        selected = isSelected == ROUTER.RENT.name,
                        onClick = {
                            isSelected = ROUTER.RENT.name
                            navController.navigate(ROUTER.RENT.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.rent),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = "Rent",color = if (isSelected == ROUTER.SERVICE.name) Color(0xFF059BEE) else Color(color = 0xFFb7b7b7))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color(color = 0xFFb7b7b7),
                            indicatorColor = Color.White
                        )
                    )

                    // Tin nhắn
                    NavigationBarItem(
                        selected = isSelected == ROUTER.MESSENGER.name,
                        onClick = {
                            isSelected = ROUTER.MESSENGER.name
                            navController.navigate(ROUTER.MESSENGER.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.mess),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = "Tin nhăn",color = if (isSelected == ROUTER.MESSENGER.name) Color(0xFF059BEE) else Color(color = 0xFFb7b7b7))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color(color = 0xFFb7b7b7),
                            indicatorColor = Color.White
                        )
                    )

                    // Trang cá nhân
                    NavigationBarItem(
                        selected = isSelected == ROUTER.PERSONAL.name,
                        onClick = {
                            isSelected = ROUTER.PERSONAL.name
                            navController.navigate(ROUTER.PERSONAL.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.person),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = "Personal",color = if (isSelected == ROUTER.PERSONAL.name) Color(0xFF059BEE) else Color(color = 0xFFb7b7b7))},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color(color = 0xFFb7b7b7),
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            NavHost(
                navController = navController,
                startDestination = isSelected
            ) {
                composable(ROUTER.HOME.name) {
                    LayoutHome(navHostController)
                }
                composable(ROUTER.SERVICE.name) {
                    LayoutService(navHostController)
                }
                composable(ROUTER.RENT.name) {
                    LayoutRent(navHostController)
                }
                composable(ROUTER.MESSENGER.name) {
                    LayoutMessenger(navHostController)
                }
                composable(ROUTER.PERSONAL.name) {
//                    LayoutPersonal(navHostController)
                }
            }
        }
    }
}
