package com.rentify.user.app.view.userScreens.IncidentReport.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.SupportRepository.Room
import com.rentify.user.app.repository.SupportRepository.Support
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.ui.theme.building_icon
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemPaidExpand
import com.rentify.user.app.viewModel.UserViewmodel.SupportViewModel

@Composable
fun ItemIncident(support: Support, room: Room) {
//    support: Support,room: Room
    var isExpanded by remember { mutableStateOf(false) }

    // Card cho từng item
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .padding(vertical = 8.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp),
                    spotColor = Color.Black
                )
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                )
                .clickable {
                    isExpanded = !isExpanded
                }
                .clip(shape = RoundedCornerShape(8.dp))
            ,
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp,
                focusedElevation = 8.dp
            )

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(building_icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )

                    Text(
                        text = "Phòng: ${room.room_name}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                IconButton(
                    onClick = {
                        // Tạo sự kiện nhấn để mở rộng hoặc thu gọn
                        isExpanded = !isExpanded
                    }
                ) {
                    Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color.Black
                    )
                }
            }

            // Animation cho việc mở rộng
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                        ContentExpand(
                            item = support,
                            listImage = support.image
                        )
                }
            }
        }
    }
}