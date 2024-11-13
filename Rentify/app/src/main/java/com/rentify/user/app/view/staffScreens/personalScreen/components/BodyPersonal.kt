package com.rentify.user.app.view.staffScreens.personalScreen.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

// _vanphuc: phần thân
@Preview(showBackground = true, showSystemUi =true)
@Composable
fun BodyPersonalPreview(){
    BodyPersonal(navController = rememberNavController())
}
@Composable
fun BodyPersonal(navController: NavHostController){
    val context= LocalContext.current
    Column (
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFFeef3f6))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.anhdaidien),
                contentDescription = "back",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(start = 30.dp)
                    .clickable {
                        Toast.makeText(context,"đổi ảnh đại điện ",Toast.LENGTH_LONG).show()
                    }
            )
            Column(
                modifier = Modifier
                    .padding(top = 22.dp, start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                verticalArrangement = Arrangement.Center // Center vertically
            ) {
                Text(
                    text = "Phùng Đức Tâm",
                    modifier = Modifier.padding(),
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "tamp8785@gmail.com",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}