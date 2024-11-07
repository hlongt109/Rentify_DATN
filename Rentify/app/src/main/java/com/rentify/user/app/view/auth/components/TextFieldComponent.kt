package com.rentify.user.app.view.auth.components

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.textFieldBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isFocused: MutableState<Boolean> // Thay đổi kiểu dữ liệu thành MutableState<Boolean>
) {
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
            containerColor = textFieldBackgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = greenInput
        ),
        shape = RoundedCornerShape(20.dp),
    )
}
