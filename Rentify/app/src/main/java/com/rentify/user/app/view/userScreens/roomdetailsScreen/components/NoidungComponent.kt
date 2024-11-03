package com.rentify.user.app.view.userScreens.roomdetailsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R


@Preview(showSystemUi = true, showBackground =true)
@Composable
fun NoidungComponent (){
    LayoutNoidung()
}
@Composable
fun LayoutNoidung(){
    Column {
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "Phòng Trọ",
                modifier = Modifier.padding(start = 5.dp)
                    .padding(end = 10.dp),
                fontSize = 20.sp,
                color = Color(0xfffeb051)
            )
            Image(painter = painterResource(id = R.drawable.n),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Nam / Nữ  ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 20.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "250 Kim Giang - Phòng rộng giá rẻ ở 4 người ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "3.500.000 - 5.100.00đ/tháng",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Red,
                fontSize = 20.sp,
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){

            Image(painter = painterResource(id = R.drawable.nha),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Tên toà nhà : GHM26",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){

            Image(painter = painterResource(id = R.drawable.dc),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "27/143 xuân phương, nam từ liêm, hà nội",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
    }
}