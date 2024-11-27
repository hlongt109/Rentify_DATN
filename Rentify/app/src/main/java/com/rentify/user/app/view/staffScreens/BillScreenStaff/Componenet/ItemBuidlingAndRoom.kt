package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.Building
import com.rentify.user.app.repository.StaffRepository.RoomRepository.Room
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorTextSX


@Composable
fun ItemBuildingAndRoom(
    building: Building?=null,
    room: Room ?=null,
    icon: Int,
    onItemClick: () -> Unit,
    isLastItem: Boolean = false // Thêm param để kiểm tra item cuối cùng
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 10.dp)
                .clickable { onItemClick() },
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            // Kiểm tra nếu building không null
            building?.let {
                Text(
                    text = "Tòa nhà: ${it.nameBuilding}",
                    color = ColorBlack,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                )
            } ?: room?.let {
                Text(
                    text = "Phòng: ${it.room_name}",
                    color = ColorBlack,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                )
            }
        }

        // Thêm divider nếu không phải item cuối cùng
        if (!isLastItem) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                color = colorTextSX,
                thickness = 1.dp
            )
        }
    }
}
