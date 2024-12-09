package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldMapSearch(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isFocused: MutableState<Boolean>,
){
        var passwordVisible = remember { mutableStateOf(false) }

        TextField(
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            placeholder = { Text(text = placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(
                    width = 1.dp,
                    color = if (isFocused.value) greenInput else colorInput,
                    shape = RoundedCornerShape(15.dp)
                )
                .focusable()
                .onFocusChanged { focusState -> isFocused.value = focusState.isFocused },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorInput,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = greenInput
            ),
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                IconLocation()
            }
        )
}

@Composable
fun IconLocation(){
    Image(
        painter = painterResource(location),
        contentDescription = "Location",
        modifier = Modifier.padding(start = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreViewMapSearch(){
    TextFieldMapSearch(
        value = "",
        onValueChange = {},
        placeholder = "Search",
        isFocused = remember { mutableStateOf(false) }
    )
}