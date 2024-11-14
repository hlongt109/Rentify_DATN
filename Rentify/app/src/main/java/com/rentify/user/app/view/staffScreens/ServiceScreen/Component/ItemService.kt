package com.rentify.user.app.view.staffScreens.ServiceScreen.Component

import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.model.Service
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.colorMoney
import com.rentify.user.app.ui.theme.delete
import com.rentify.user.app.ui.theme.edit
import com.rentify.user.app.ui.theme.image_service
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.utils.CheckUnit.toJson

@Composable
fun ItemService(
    item: Service,
    navController: NavController
) {
    val formatPrice = CheckUnit.formattedPrice(item.price.toFloat())
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color.Black
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = item.photos?.getOrNull(0),
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "image_service",
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight()
                        .offset(x = -10.dp, y = 0.dp),
                    contentScale = ContentScale.Crop
                )

                //thong tin dich vu
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorBlack,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    Text(
                        text = item.description,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorBlack,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    //tien dich vu
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    Row {
                        Text(
                            text = formatPrice,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorMoney
                        )
                        Text(
                            text = " /phòng",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorMoney
                        )
                    }
                    //button sua xoa
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        //sua
                        IconButton(
                            onClick = {
                                val serviceJson = Uri.encode(item.toJson())
                                navController.navigate("${ROUTER.ADDEDITSERVICE.name}/true/$serviceJson")  // truyền cả isEditing và serviceJson
                            }
                        ) {
                            Icon(
                                painter = painterResource(edit),
                                contentDescription = "edit",
                                tint = colorLocation,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        //xoa
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(delete),
                                contentDescription = "delete",
                                tint = colorLocation,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    DialogComponent(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            // Handle the delete action here
            showDialog = false
        },
        title = "Xóa dịch vụ",
        textConfirm = "Xác nhận",
        textDismiss = "Quay lại"
    )
}
