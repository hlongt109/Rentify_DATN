package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.greenInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSmall(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    isFocused: MutableState<Boolean>,
){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        placeholder = { Text(text = placeHolder) },
        modifier = Modifier
            .width(screenWidth.dp / 2.3f)
            .padding(bottom = 10.dp)
            .border(
                width = 1.dp,
                color = if (isFocused.value) colorLocation else colorInput,
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
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSmallField(){
    TextFieldSmall(
        value = "",
        onValueChange = {},
        placeHolder = "Email",
        isFocused = remember { mutableStateOf(false) }
    )
}