package com.rentify.user.app.view.userScreens.togetherScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TogetherContentPreview(){
    TogetherContent(navController= rememberNavController())
}
@Composable
fun TogetherContent(navController: NavHostController){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 20.dp)
        ){
            Text(text = "Cần tìm người ở ghép cách Cao đẳng FPT 1km",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){
            Text(text = "Khu vực :",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 17.sp,
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){

            Image(painter = painterResource(id = R.drawable.dc),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Phường Xuân Canh, Quận Nam Từ Liêm, Thành Phố Hà Nội",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 16.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){
            Text(text = "Giá phòng :",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 17.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){
            Text(text = "\$ 3.000.000 VND / tháng",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 16.sp,
                color = Color(0xFFd39580)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){
            Text(text = "Số lượng người :",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 17.sp,
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 17.dp,5.dp)
        ){

            Image(painter = painterResource(id = R.drawable.nguwoi),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "2 người",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 10.dp)
        ){
            Text(text = "Giới tính : ",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 17.sp,
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 17.dp,5.dp)
        ){

            Image(painter = painterResource(id = R.drawable.n),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Nam, Nữ",
                modifier = Modifier.padding(start = 5.dp, bottom = 20.dp),
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(color = Color(0xFFf7f7f7))
        ) {
        }

    }
}