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
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import com.rentify.user.app.viewModel.SupportViewmodel.SupportViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListSupportByRoomPreview() {
    ListSupportByRoom(navController = rememberNavController(),
        buildingId = "sample_building_id",1
    )
}
@Composable
fun ListSupportByRoom(
    navController: NavHostController,
    buildingId: String?,
    status: Int ?= null,
    supportViewModel: SupportViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val supportList by supportViewModel.listSupportMap.observeAsState(emptyMap())

    val supportDetail by supportViewModel.supportDetail.observeAsState()
    var incidentdescription by remember { mutableStateOf("") }
    var incident by remember { mutableStateOf("") }
      var selectedRoomId by remember { mutableStateOf<String?>(null) }
    val expandedStateMap = remember { mutableStateMapOf<String, Boolean>() }

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
    val rooms = supportList[buildingId]?.filter { it?.status == status } ?: emptyList()

    val support by supportViewModel.listSupport.observeAsState(initial = emptyList())
    Column {
        if (rooms.isNotEmpty()) {
            rooms.forEach { support ->
                //  var selectedRoomId by remember { mutableStateOf<String?>(null) }
                val key = support?._id ?: "default_key"
                val isExpanded = expandedStateMap[key] ?: false // Kiểm tra trạng thái mở rộng của phòng
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))

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
                                    text ="Phòng : ${support?.room_id?.room_name!!}" ,  // Hiển thị tên phòng
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = if (status == 0) "Cảnh báo sự cố !!! ⚠️" else  "Đã hoàn thành 🥇",
                                color =  if (status == 0) Color.Red else Color.Green,
                                modifier = Modifier.padding(start = 20.dp)// Tên tòa nhà
                            )
                            IconButton(
                                onClick = {
                                    selectedRoomId = support?._id // Cập nhật selectedRoomId
                            val key = support?._id ?: "default_key"
                            expandedStateMap[key] = !isExpanded
                                }
                            ) {
                                Icon(
                                    imageVector = if (expandedStateMap[support?._id] == true)
                                        Icons.Default.KeyboardArrowUp
                                    else
                                        Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (expandedStateMap[support?._id] == true) "Collapse" else "Expand",
                                    tint = Color.Black
                                )
                            }
                        }
                        // Chi tiết của phòng
                        AnimatedVisibility(
                            visible = expandedStateMap[support?._id] == true,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())
                            val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
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
                                        items(
                                            supportDetail?.image ?: emptyList()
                                        ) { photoSportUrl ->
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
                                    if (supportDetail?.status == 0) {
                                        Button(
                                            onClick = {
                                                supportDetail?.let { detail ->
                                                    val updatedSupport = detail.copy(status = 1)
                                                    supportViewModel.updateSupportDetail(
                                                        detail._id,
                                                        updatedSupport,
                                                        buildingId!!,
                                                        status!!
                                                    )
                                                    supportViewModel.fetchSupport(
                                                        buildingId,
                                                        status
                                                    )
                                                    supportViewModel.setSelectedSupport(
                                                        updatedSupport
                                                    )
                                                    Toast.makeText(
                                                        context,
                                                        "Đã khắc phục sự cố",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    if (buildingWithRooms.isNotEmpty()) {
                                                        buildingWithRooms.forEach { building ->
                                                            val isExpanded = expandedStates[building._id] ?: false

                                                            expandedStates[building._id] = isExpanded
                                                    if (!isExpanded) {
                                                        supportViewModel.fetchSupport(building._id, 1)
                                                    }}}
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
        } else {
            Text(
                text = "Không có phòng nào",
                modifier = Modifier.padding(8.dp),
                style = TextStyle(color = Color.Gray)
            )
        }
    }

}


@Composable
fun FeetReporthoanthanh(
    navController: NavHostController
) {
    val supportViewModel: SupportViewModel = viewModel()
    val selectedSupport by supportViewModel.selectedSupport.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
        val context = LocalContext.current
        val viewModel: RoomViewModel = viewModel(
            factory = RoomViewModel.RoomViewModeFactory(context)
        )
        val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())

        // Biến trạng thái để theo dõi tòa nhà được chọn
        var selectedBuildingId by remember { mutableStateOf<String?>(null) }

        // Điều kiện hiển thị
        if (selectedBuildingId == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (buildingWithRooms.isNotEmpty()) {
                    buildingWithRooms.forEach { building ->
                        val isExpanded = expandedStates[building._id] ?: false
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
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
                                        text ="Tòa nhà: ${building.nameBuilding}" , // Tên tòa nhà
                                    )

                                    Column(
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .weight(1f)
                                    ) {
                                    }
                                    // Mũi tên điều hướng
                                    IconButton(
                                        onClick = {   expandedStates[building._id] = !isExpanded
                                            if (!isExpanded) {
                                                supportViewModel.fetchSupport(building._id, 1)
                                            }} // Toggle danh sách phòng
                                    ) {
                                        Icon(
                                            imageVector = if (isExpanded)
                                                Icons.Default.KeyboardArrowUp
                                            else
                                                Icons.Default.KeyboardArrowDown,
                                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                                            tint = Color.Black,
                                            modifier = Modifier.size(25.dp)
                                        )
                                    }
                                }
                                // Danh sách sự cố
                                AnimatedVisibility(
                                    visible = isExpanded,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = Color.White)
                                            .padding(5.dp)
                                    ) {
                                        ListSupportByRoom(navController, buildingId = building._id,1)
                                    }
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
       //     ListSupportByRoom(navController, buildingId = selectedBuildingId!!,0)
        }
    }
}

