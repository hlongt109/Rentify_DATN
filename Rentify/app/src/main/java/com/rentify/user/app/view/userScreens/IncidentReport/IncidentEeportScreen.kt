package com.rentify.user.app.view.userScreens.IncidentReport

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController

import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.ui.theme.error_image
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.userScreens.IncidentReport.Components.CustomTabBar
import com.rentify.user.app.view.userScreens.IncidentReport.Components.HeaderComponent
import com.rentify.user.app.view.userScreens.IncidentReport.Components.ItemIncident
import com.rentify.user.app.viewModel.UserViewmodel.SupportUiState
import com.rentify.user.app.viewModel.UserViewmodel.SupportViewModel


@Composable
fun IncidentReportScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Đang yêu cầu", "Đã hoàn thành")
    val supportService = RetrofitService()
    val supportRepository = SupportRepository(supportService.ApiService)
    val supportViewModel: SupportViewModel = viewModel(
        factory = SupportViewModel.SupportViewModelFactory(supportRepository)
    )

    val loginViewModel = getLoginViewModel(context)
    val userId = loginViewModel.getUserData().userId

    LaunchedEffect(Unit) {
        if (supportViewModel.listSupport.value.isNullOrEmpty()) {
            supportViewModel.getListSupport(userId)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderBar(navController, title = "Báo cáo sự cố")
            CustomTabBar(
                items = tabs,
                selectedIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                }
            )

            when (selectedTabIndex) {
                0 -> Requesting(
                    navController,
                    userId,
                    viewModel = supportViewModel
                )

                1 -> Completed(
                    navController,
                    userId,
                    viewModel = supportViewModel
                )
            }
        }

        // Nút Add cố định ở góc dưới bên phải

        Box(
            modifier = Modifier
                //  .background(Color(0xff70cbff))
                .fillMaxSize()
                .padding(25.dp),

            contentAlignment = Alignment.BottomEnd // Cố định Row ở góc dưới bên phải
        ) {
            Row(
                modifier = Modifier
                    .shadow(3.dp, shape = RoundedCornerShape(30.dp))
                    .background(color = Color(0xFF84d8ff))
                    .border(
                        width = 0.dp,
                        color = Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable(onClick = { navController.navigate("ADDINCIDENTREPORT") },
                        indication = null, // Bỏ hiệu ứng tối khi nhấn
                        interactionSource = remember { MutableInteractionSource() })

                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.addr),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp, 20.dp)
                )
            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutIncidentReportScreenScreen() {
    IncidentReportScreen(navController = rememberNavController())
}

@Composable
fun Requesting(
    navController: NavController,
    userId: String,
    viewModel: SupportViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val unProcessed by viewModel.unprocessed.collectAsState()
    Log.d("UnProcessed", "Requesting: $unProcessed")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is SupportUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is SupportUiState.Success -> {
                if (unProcessed.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(error_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Không có báo cáo cần hỗ trợ",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp)
                    ) {
                        items(unProcessed) { item ->
                            ItemIncident(item, item.room_id)
                        }
                    }
                }
            }

            is SupportUiState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Image(
                        painter = painterResource(error_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = (uiState as SupportUiState.Error).message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }
}

@Composable
fun Completed(
    navController: NavController,
    userId: String,
    viewModel: SupportViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val process by viewModel.processed.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is SupportUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is SupportUiState.Success -> {
                if (process.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(error_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Không có báo cáo cần hỗ trợ",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp)
                    ) {
                        items(process) { item ->
                            ItemIncident(item, item.room_id)
                        }
                    }
                }
            }

            is SupportUiState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Image(
                        painter = painterResource(error_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = (uiState as SupportUiState.Error).message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }
}


