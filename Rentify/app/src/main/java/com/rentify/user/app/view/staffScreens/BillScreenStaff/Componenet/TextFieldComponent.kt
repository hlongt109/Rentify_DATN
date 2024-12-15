package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.Building
import com.rentify.user.app.repository.StaffRepository.RoomRepository.Room
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.building_icon
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFiledComponent(
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    placeHolder: String,
    isFocused: MutableState<Boolean>,
    isShowIcon: Boolean = false,
    isIcon: Any? = null,
    listBuilding: List<Building>? = null,
    listRoom: List<Room>? = null,
    enable: Boolean = false,
    check: Boolean = false,
    onExpandedChange: ((Boolean) -> Unit)? = null,
    onExpandedRoom: ((Boolean) -> Unit)? = null,
    onBuildingSelected: ((String) -> Unit)? = null,
    onRoomSelected: ((Room) -> Unit)? = null,
    onExpandedRoomNull: ((Boolean) -> Unit)? = null,
    onExpandedBuildingNull: ((Boolean) -> Unit)? = null
) {
    var showIcon = remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var isExpandedRoom by remember { mutableStateOf(false) }
    var isExpandedRoomNull by remember { mutableStateOf(false) }
    var isExpandedBuildingNull by remember { mutableStateOf(false) }

    Column {
        Text(
            text = placeHolder,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ColorBlack,
            modifier = Modifier.padding(bottom = 10.dp, start = 5.dp, top = 15.dp)
        )

        TextField(
            textStyle = TextStyle( // Cập nhật style chữ
                color = Color.Black, // Đặt màu chữ đậm
                fontWeight = FontWeight.SemiBold, // Làm đậm chữ
                fontSize = 14.sp // Kích thước chữ
            ),
            value = value,
            onValueChange = { newValue ->
                // Kiểm tra xem onValueChange có khác null không
                onValueChange?.invoke(newValue)
            },
            enabled = enable,
            placeholder = { Text(text = placeHolder, color = ColorBlack) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(
                    width = 1.dp,
                    color = if (isFocused.value) colorLocation else colorInput,
                    shape = RoundedCornerShape(15.dp)
                )
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                )
                .focusable()
                .onFocusChanged { focusState -> isFocused.value = focusState.isFocused }
                .clickable(enabled  = !check) {
                    // Xử lý click cho cả trường hợp chọn ngày và chọn building/room
                    onClick?.invoke()
                    if (listBuilding != null) {
                        isExpanded = !isExpanded
                        onExpandedChange?.invoke(isExpanded)
                    } else if (listRoom != null) {
                        isExpandedRoom = !isExpandedRoom
                        onExpandedRoom?.invoke(isExpandedRoom)
                    }else if(listRoom == null){
                        isExpandedRoomNull = !isExpandedRoomNull
                        onExpandedRoomNull?.invoke(isExpandedRoomNull)
                    }else if(listBuilding == null){
                        isExpandedBuildingNull = !isExpandedBuildingNull
                        onExpandedBuildingNull?.invoke(isExpandedBuildingNull)
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorInput,
                focusedIndicatorColor = Color.Transparent,  // Ẩn indicator khi focus
                unfocusedIndicatorColor = Color.Transparent,  // Ẩn indicator khi không focus
                disabledIndicatorColor = Color.Transparent,  // Ẩn indicator khi disable
                cursorColor = colorLocation,
            ),
            shape = RoundedCornerShape(15.dp),
            // Thêm trailing icon cho password
            trailingIcon = if (isShowIcon) {
                {
                    IconButton(onClick = { showIcon.value = !showIcon.value }) {
                        when (isIcon) {
                            is ImageVector ->
                                Icon(
                                    imageVector = if (showIcon.value)
                                        isIcon
                                    else
                                        isIcon,
                                    contentDescription = if (showIcon.value)
                                        "Up"
                                    else
                                        "Down"
                                )

                            is Int -> Image(
                                painter = painterResource(isIcon),
                                contentDescription = null
                            )
                        }
                    }
                }
            } else null,
        )

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Hiển thị các item theo tháng khi mở rộng
                if (listBuilding != null) {
                    listBuilding.forEachIndexed { index, building ->
                        ItemBuildingAndRoom(
                            icon = building_icon,
                            building = building,
                            onItemClick = {
                                onValueChange?.invoke(building.nameBuilding)
                                isExpanded = false
                                onExpandedChange?.invoke(false)
                                onBuildingSelected?.invoke(building._id)
                            },
                            isLastItem = index == listBuilding.size - 1
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isExpandedRoom,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (listRoom.isNullOrEmpty()) {
                    // Hiển thị thông báo nếu không có phòng
                    Text(
                        text = "Không có phòng hoặc phòng đã có hóa đơn",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                } else {
                    // Hiển thị danh sách phòng
                    listRoom.forEachIndexed { index, room ->
                        ItemBuildingAndRoom(
                            icon = building_icon,
                            room = room,
                            onItemClick = {
                                onValueChange?.invoke(room.room_name)
                                isExpanded = false
                                isExpandedRoom = false
                                onExpandedChange?.invoke(false)
                                onRoomSelected?.invoke(room)
                            },
                            isLastItem = index == listRoom.size - 1
                        )
                    }
                }
            }
        }
    }
}