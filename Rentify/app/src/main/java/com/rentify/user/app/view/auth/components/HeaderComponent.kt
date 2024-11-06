package com.rentify.user.app.view.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.iconBack

@Composable
fun HeaderComponent(
    backgroundColor:Color,
    title: String
){
    Box(modifier = Modifier
        .background(backgroundColor)
        .fillMaxWidth()
        .height(40.dp))
    {
        Row (modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        ){
        Icon(
           painter = painterResource(id = iconBack),
            contentDescription = "icon back",
            tint = Color.Black,
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )

          Text(
              text = title,
              color = Color.Black,
              textAlign = TextAlign.Center,
              fontSize = 17.sp,
              fontStyle = FontStyle.Normal
          )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewComponent(){
    HeaderComponent(
        Color.White,
        ""
    )
}
