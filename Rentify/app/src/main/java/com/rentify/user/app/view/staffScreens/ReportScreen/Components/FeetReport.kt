package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import com.rentify.user.app.viewModel.SupportViewmodel.SupportViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetReport() {
    FeetReportyeucau(navController = rememberNavController())
}

@Composable
fun FeetReportyeucau(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    var userId = loginViewModel.getUserData().userId

    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())

    // Biến trạng thái để theo dõi tòa nhà được chọn
    var selectedBuildingId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Điều kiện hiển thị
    if (selectedBuildingId == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (buildingWithRooms.isNotEmpty()) {
                buildingWithRooms.forEach { building ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                // Cập nhật trạng thái khi nhấn vào tòa nhà
                                selectedBuildingId = building._id
                            }
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .background(color = Color.White)
                                    .padding(10.dp)
                            ) {
                                // Icon hoặc hình ảnh của tòa nhà
                                Image(
                                    painter = painterResource(id = R.drawable.building), // Hình ảnh đại diện tòa nhà
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF5F5F5))
                                        .padding(5.dp)
                                )
                                Text(
                                    text = building.nameBuilding, // Tên tòa nhà
                                )
                                Text(
                                    text = "Cảnh báo sự cố !!! ⚠️  ",
                                    color = Color.Red,
                                    modifier = Modifier.padding(start = 20.dp)// Tên tòa nhà
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .weight(1f)
                                ) {
                                }
                                // Mũi tên điều hướng
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // Khi không có tòa nhà nào
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No buildings available",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    } else {
        // Hiển thị ListSupportByRoom
        ListSupportByRoom(navController, buildingId = selectedBuildingId!!, 1)
    }
}
@Composable
fun ListSupportByRoom(
    navController: NavHostController,
    buildingId: String?,
    status: Int ?= 1,
    supportViewModel: SupportViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val supportDetail by supportViewModel.supportDetail.observeAsState()
    var incidentdescription by remember { mutableStateOf("") }
    var incident by remember { mutableStateOf("") }
    var selectedRoomId by remember { mutableStateOf<String?>(null) }
    val expandedStateMap = remember { mutableStateOf(mutableMapOf<String, Boolean>()) }

    LaunchedEffect(buildingId) {
        buildingId?.let {
            supportViewModel.fetchSupport(it, status!!)
        }
    }

    LaunchedEffect(selectedRoomId) {
        selectedRoomId?.let {
            supportViewModel.fetchSupportDetail(it)
        }
    }

    val support by supportViewModel.listSupport.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        support.forEach { support ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        selectedRoomId = support?._id // Cập nhật selectedRoomId
                        val key = support?._id ?: "default_key"
                        expandedStateMap.value[key] = !(expandedStateMap.value[key] ?: false)
                    }
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(color = Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.phong),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .padding(start = 20.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = support?.room_id?.room_name!!,  // Hiển thị tên phòng
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(
                            onClick = {
                                val key = support?._id ?: "default_key"
                                expandedStateMap.value[key] = !(expandedStateMap.value[key] ?: false)
                            }
                        ) {
                            Icon(
                                imageVector = if (expandedStateMap.value[support?._id] == true)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = if (expandedStateMap.value[support?._id] == true) "Collapse" else "Expand",
                                tint = Color.Black
                            )
                        }
                    }

                    // Chi tiết của phòng
                    AnimatedVisibility(
                        visible = expandedStateMap.value[support?._id] == true,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            // Incident description
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Sự cố",
                                        color = Color(0xff000000),
                                        fontSize = 13.sp,
                                    )
                                    Text(
                                        text = " *",
                                        color = Color(0xffff1a1a),
                                        fontSize = 16.sp,
                                    )
                                }
                                TextField(
                                    value = incidentdescription,
                                    onValueChange = { incidentdescription = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color(0xFFcecece),
                                        unfocusedIndicatorColor = Color(0xFFcecece),
                                        focusedPlaceholderColor = Color.Black,
                                        unfocusedPlaceholderColor = Color.Gray,
                                        unfocusedContainerColor = Color(0xFFffffff),
                                        focusedContainerColor = Color(0xFFffffff),
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "${supportDetail?.title_support}",
                                            fontSize = 13.sp,
                                            color = Color(0xFF7f7f7f),
                                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                                        )
                                    },
                                    shape = RoundedCornerShape(size = 8.dp),
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                                    )
                                )
                            }
                            // Incident description
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Mô tả sự cố",
                                        color = Color(0xff000000),
                                        fontSize = 13.sp,
                                    )
                                    Text(
                                        text = " *",
                                        color = Color(0xffff1a1a),
                                        fontSize = 16.sp,
                                    )
                                }
                                TextField(
                                    value = incident,
                                    onValueChange = { incident = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color(0xFFcecece),
                                        unfocusedIndicatorColor = Color(0xFFcecece),
                                        focusedPlaceholderColor = Color.Black,
                                        unfocusedPlaceholderColor = Color.Gray,
                                        unfocusedContainerColor = Color(0xFFffffff),
                                        focusedContainerColor = Color(0xFFffffff),
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "${supportDetail?.content_support}",
                                            fontSize = 13.sp,
                                            color = Color(0xFF7f7f7f),
                                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                                        )
                                    },
                                    shape = RoundedCornerShape(size = 8.dp),
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                                    )
                                )
                            }
                            //==========================================
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.camera),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .padding(start = 20.dp)
                                )
                                Text(
                                    text = "Ảnh sự cố",
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 20.dp)
                                )
                            }
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(supportDetail?.image ?: emptyList()) { photoSportUrl ->
                                    val urianhSport: String =
                                        "http://10.0.2.2:3000/${photoSportUrl}"
                                    AsyncImage(
                                        model = urianhSport,
                                        contentDescription = "Ảnh Phòng trọ",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.LightGray),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Log.d("TAG", "FeetReportyeucau: ${supportDetail?.image}")
                            //==============================================
                            Spacer(modifier = Modifier.height(25.dp))
                            Button(
                                onClick = {
                                    supportDetail?.let { detail ->
                                        val updatedSupport = detail.copy(status = 0)
                                        supportViewModel.updateSupportDetail(detail._id, updatedSupport, buildingId!!, status!!)
                                        supportViewModel.fetchSupport(buildingId, status)
                                        supportViewModel.setSelectedSupport(updatedSupport)
                                        Toast.makeText(context, "Đã khắc phục sự cố", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xfffb6b53)
                                )
                            ) {
                                Text(
                                    modifier = Modifier.padding(6.dp),
                                    text = "Đã khắc phục sự cố",
                                    fontSize = 16.sp,
                                    color = Color(0xffffffff)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


