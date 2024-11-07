package com.rentify.user.app.view.userScreens.TinnhanScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun headcomponentPreview(){
    headcomponent(navController= rememberNavController())
}
@Composable
fun headcomponent(navController: NavHostController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color(0xffd2f1ff)),
        verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back",
            modifier = Modifier.size(34.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.width(8.dp)) // Adds space between images and text
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "",
            modifier = Modifier.size(34.dp) // Adjust size as needed
        )
        Spacer(modifier = Modifier.width(8.dp)) // Adds space between images and text
        Text(
            text = "Vũ Văn Phúc",
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}