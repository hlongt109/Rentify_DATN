package com.rentify.user.app.view.userScreens.addIncidentReportScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.SupportRepository.ContractRoom
import com.rentify.user.app.repository.SupportRepository.ContractRoomData
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.ui.theme.building_icon
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.utils.CheckUnit.toFilePath
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.userScreens.IncidentReport.Components.ContentExpand
import com.rentify.user.app.view.userScreens.addIncidentReportScreen.Components.HeaderComponent
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.UserViewmodel.RoomSupportUiState
import com.rentify.user.app.viewModel.UserViewmodel.SupportViewModel


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncidentReportScreen(
    navController: NavHostController,
    notiViewmodel: NotificationViewModel = viewModel()
    ) {
    var isCheckedHigh by remember { mutableStateOf(false) }
    var isCheckedMedium by remember { mutableStateOf(false) }
    var isCheckedLow by remember { mutableStateOf(false) }
    var incidentdescription by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var incident by remember { mutableStateOf("") }

    val supportService = RetrofitService()
    val supportRepository = SupportRepository(supportService.ApiService)
    val supportViewModel: SupportViewModel = viewModel(
        factory = SupportViewModel.SupportViewModelFactory(supportRepository)
    )
    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val userId = loginViewModel.getUserData().userId

    val roomUiState by supportViewModel.roomUiState.collectAsState()
    val listRoom by supportViewModel.listRoom.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var roomNumber by remember { mutableStateOf("Chọn phòng") }
    var roomId by remember { mutableStateOf("") }
    var buildingId by remember { mutableStateOf("") }
    var selectedImages by rememberSaveable { mutableStateOf(listOf<Uri>()) }
    val launcherImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            uris?.let {
                val remainingSpace = 10 - selectedImages.size
                if (remainingSpace > 0) {
                    selectedImages = selectedImages + uris
                    Log.d("ImageUploadSelect", "Selected image URI: $uris")
                }
                if (it.size > remainingSpace) {
                    Toast.makeText(context, "Giới hạn tối đa 10 ảnh.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    val formatTimeNoti = CheckUnit.formatTimeNoti(System.currentTimeMillis())

    selectedImages.forEach { uri ->
        val path = uri.toFilePath(context)
        Log.d("ImagePathCheck", "URI: $uri -> Path: $path")
    }
    val imagePaths = selectedImages.mapNotNull { uri -> uri.toFilePath(context) }
    Log.d("CheckImage", "AddIncidentReportScreen: $imagePaths")

//    var selectedRoom by remember { mutableStateOf<ContractRoomData?>(null) }
//    Log.d("AddIncidentReportScreen", "Selected Room: ${selectedRoom?.room?.room_number}")
    val errorRoom by supportViewModel.errorRoom.observeAsState()
    val errorTitle by supportViewModel.errorTitle.observeAsState()
    val errorContent by supportViewModel.errorContent.observeAsState()

    val successMessage by supportViewModel.successMessage.observeAsState()
    val successAdd by supportViewModel.successMessageAdd.observeAsState()
    val isLoading by supportViewModel.isLoading.observeAsState()

    listRoom.forEach { room ->
        roomNumber = room.room.room_number
        roomId = room.room.room_id
        buildingId = room.room.building.building_id
    }

    LaunchedEffect(userId) {
        supportViewModel.getInfoRoom(userId)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderBar(navController, title = "Báo cáo sự cố")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = screenHeight.dp / 8f)
                    .padding(15.dp)
            ) {
// chọn phòg
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                        expanded = !expanded
                    }
                    .border(
                        width = 0.dp,
                        color = Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )) {
                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                            .background(color = Color(0xFFffffff))
                            .border(
                                width = 0.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                        ) {
                            Row {
                                Text(
                                    text = "Chọn phòng",
                                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                    color = Color(0xff7f7f7f),
                                    // fontWeight = FontWeight(700),
                                    fontSize = 16.sp,
                                )
                                Text(

                                    text = " *",
                                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                    color = Color(0xffff1a1a),
                                    // fontWeight = FontWeight(700),
                                    fontSize = 16.sp,

                                    )
                            }

                            Spacer(modifier = Modifier.height(7.dp))
                            Text(

                                text = "$roomNumber",
                                color = Color.Black,
                                fontSize = 14.sp,
                            )
                        }
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.next),
                                contentDescription = null,
                                modifier = Modifier.size(17.dp, 17.dp)
                            )
                        }

                    }
//                    AnimatedVisibility(
//                        visible = expanded,
//                        enter = fadeIn() + expandVertically(),
//                        exit = fadeOut() + shrinkVertically()
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(color = Color.White)
//                                .padding(10.dp)
//                        ) {
//                            listRoom.forEach { item ->
//                                ItemRoomExpand(
//                                    room = item.room,
//                                    onRoomSelected = {
//                                        buildingId = item.room.building.building_id
//                                        roomId = item.room.room_id
//                                        roomNumber = item.room.room_number
//                                        expanded = false
//                                        supportViewModel.clearErrorRoom()
//                                    }
//                                )
//                            }
//                        }
//                    }
                }
                errorRoom?.let {
                    ShowReport.ShowError(message = it)
                }
///2
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier

                        .fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,

                    ) {
// sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(

                                text = "Sự cố",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xff000000),
                                // fontWeight = FontWeight(700),
                                fontSize = 13.sp,

                                )
                            Text(

                                text = " *",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffff1a1a),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,

                                )
                        }
                        TextField(
                            value = incident,
                            onValueChange = {
                                incident = it
                                supportViewModel.clearErrorTitle()
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
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
                                    text = "Nhập tóm tắt sự cố",
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
                    errorTitle?.let {
                        ShowReport.ShowError(message = it)
                    }
                    // mô tả sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(

                                text = "Mô tả sự cố",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xff000000),
                                // fontWeight = FontWeight(700),
                                fontSize = 13.sp,

                                )
                            Text(

                                text = " *",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffff1a1a),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,

                                )
                        }
                        TextField(
                            value = incidentdescription,
                            onValueChange = {
                                incidentdescription = it
                                supportViewModel.clearErrorContent()
                            },
                            modifier = Modifier
                                .fillMaxWidth(),

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
                                    text = "Nhập mô tả ( ghi chú  ) khi giải quyết sự cố",
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
                    errorContent?.let {
                        ShowReport.ShowError(message = it)
                    }
//ảnh
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = "Ảnh sự cố",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        // fontWeight = FontWeight(700),
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Row {
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable { launcherImage.launch(arrayOf("image/*")) },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier
                                    .size(100.dp)
                                    //  .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                                    .background(color = Color(0xFFffffff))

                                    .drawBehind {
                                        val borderWidth = 2.dp.toPx()  // Độ rộng của viền
                                        val dashWidth = 2.dp.toPx()   // Độ dài của nét đứt
                                        val gapWidth =
                                            1.dp.toPx()     // Khoảng cách giữa các nét đứt
                                        val radius = 10.dp.toPx()      // Độ cong của góc

                                        // Vẽ viền nét đứt xung quanh
                                        drawRoundRect(
                                            color = Color(0xFF7ccaef),
                                            size = size.copy(
                                                width = size.width - borderWidth,
                                                height = size.height - borderWidth
                                            ),
                                            style = Stroke(
                                                width = borderWidth,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    floatArrayOf(
                                                        dashWidth,
                                                        gapWidth
                                                    )
                                                )
                                            ),
                                            cornerRadius = CornerRadius(radius, radius)
                                        )
                                    },

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Row(
                                    modifier = Modifier.padding(25.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.image1),
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp, 30.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            LazyRow(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
//
                                items(selectedImages) { uri ->
                                    Box(
                                        modifier = Modifier
                                            .size(200.dp)
                                    ) {
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier
                                                .size(200.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .border(
                                                    2.dp,
                                                    Color.Gray,
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            contentScale = ContentScale.Inside
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .align(Alignment.TopEnd)
                                                .background(
                                                    Color.Red,
                                                    shape = RoundedCornerShape(30.dp)
                                                )
                                        ) {
                                            IconButton(
                                                modifier = Modifier.fillMaxSize(),
                                                onClick = {
                                                    selectedImages =
                                                        selectedImages.filter { it != uri }
                                                },
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Remove Image",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(17.dp))
                    Button(
                        onClick = {/**/
                            if (supportViewModel.isLoading.value == true) return@Button // Ngăn bấm nhiều lần
                            supportViewModel.createSupportReport(
                                userId,
                                roomId,
                                buildingId,
                                titleSupport = incident,
                                contentSupport = incidentdescription,
                                imagePaths = imagePaths,
                                status = 0
                            ) {
                                Toast.makeText(
                                    context,
                                    "Báo cáo đã được gửi thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val notificationRequest = NotificationRequest(
                                    user_id = userId ,
                                    title = "Báo cáo",
                                    content = "Bạn đã gửi thành công một báo cáo vào lúc: $formatTimeNoti"
                                )
                                notiViewmodel.createNotification(
                                    notificationRequest
                                )
                                navController.navigate(MainActivity.ROUTER.INCIDENTREPORT.name)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        //  , RoundedCornerShape(25.dp)), // Bo tròn 12.dp

                        shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xfffb6b53)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = "Báo cáo sự cố",
                            fontSize = 16.sp,
                            // fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xffffffff)
                        )
                    }
                }
            }

        }
    }
    if (isLoading == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorLocation)
            }
        }
    }
//    val rooms = (roomUiState as RoomSupportUiState.Success).data

}


@Composable
fun ItemRoomExpand(
    room: ContractRoom,
    onRoomSelected: (ContractRoom) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRoomSelected(room)
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(building_icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp, 30.dp)
        )

        Text(
            text = room.room_number,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 15.sp
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutAddPostScreen() {
    AddIncidentReportScreen(navController = rememberNavController())
}