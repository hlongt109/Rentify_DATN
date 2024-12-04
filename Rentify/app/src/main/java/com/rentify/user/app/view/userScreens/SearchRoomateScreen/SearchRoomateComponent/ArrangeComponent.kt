package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.arrange
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorTextSX
import com.rentify.user.app.ui.theme.down

@Composable
fun ArrangeComponent(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sắp xếp theo",
                    color = colorTextSX,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    painter = painterResource(down),
                    contentDescription = "Down",
                    tint = colorTextSX,
                    modifier = Modifier.size(30.dp),
                )
            }
            //sap xep 2
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = "Khoảng giá",
                    color = colorTextSX,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Icon(
                    painter = painterResource(down),
                    contentDescription = "Down",
                    tint = colorTextSX,
                    modifier = Modifier.size(30.dp),
                )
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Divider(
                color = colorInput,
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .align(Alignment.CenterVertically),
            )

            IconButton(
                onClick = { navController.navigate(ROUTER.Filter_room.name)},
                modifier = Modifier.width(70.dp)
            ) {
              Row {
                  Icon(
                      painter = painterResource(arrange),
                      contentDescription = "Arrange",
                      tint = Color.Black,
                      modifier = Modifier
                          .size(20.dp)
                          .align(Alignment.CenterVertically)
                  )
                  Text(
                      text = "Lọc",
                      color = colorTextSX,
                      fontSize = 15.sp,
                      fontWeight = FontWeight.Medium,
                      modifier = Modifier.padding(start = 5.dp)
                  )
              }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArrange() {
//    ArrangeComponent()
}