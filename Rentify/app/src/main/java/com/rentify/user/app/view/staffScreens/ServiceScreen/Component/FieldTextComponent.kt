package com.rentify.user.app.view.staffScreens.ServiceScreen.Component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack

@Composable
fun FieldTextComponent(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = ColorBlack
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        TextField(
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            placeholder = { Text(text = placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
            ,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFieldText(){
    FieldTextComponent(value = "", onValueChange = {}, placeholder = "Email", title = "Email")
}