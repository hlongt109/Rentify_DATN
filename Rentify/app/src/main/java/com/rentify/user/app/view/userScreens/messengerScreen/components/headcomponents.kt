package com.rentify.user.app.view.userScreens.messengerScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
            .background(color = Color(0xffd2f1ff))
    ) {
        Text(
            text = "Tin nhắn ",
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f)) // This will take up the remaining space
        Image(
            painter = painterResource(id = R.drawable.searchtinnhan),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
                .padding(top = 10.dp)
        )
    }

}