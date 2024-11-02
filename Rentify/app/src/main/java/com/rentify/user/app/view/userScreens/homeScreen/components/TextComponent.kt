package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KhamPhaComponent() {
    LayotKhamPha()
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DoitacComponent() {
    LayotDoitac()
}

@Composable
fun LayotKhamPha() {
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Image(painter = painterResource(id = R.drawable.iconkhampha),
            contentDescription ="" ,
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Khám Phá ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 20.sp
            )
    }
}
@Composable
fun LayotDoitac() {
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Image(painter = painterResource(id = R.drawable.icondoitac),
            contentDescription ="" ,
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Đối tác",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 20.sp
        )
    }
}