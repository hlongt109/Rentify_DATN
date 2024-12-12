package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldMapSearch(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isFocused: MutableState<Boolean>,
    onSearch: (String) -> Unit,
    onClick: (() -> Unit?)? = null,
    enable: Boolean = false,
){
        var passwordVisible = remember { mutableStateOf(false) }
        val context = LocalContext.current

        TextField(
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            placeholder = { Text(text = placeholder, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(
                    width = 1.dp,
                    color = if (isFocused.value) greenInput else colorInput,
                    shape = RoundedCornerShape(15.dp)
                )
                .focusable()
                .clip(RoundedCornerShape(15.dp))
                .clickable {
                    if (onClick != null) {
                        onClick()
                    }
                }
                .onFocusChanged { focusState -> isFocused.value = focusState.isFocused },
            enabled = enable,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorInput_2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = greenInput
            ),
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                IconLocation()
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(value)
                    isFocused.value = false
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            )
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
@Composable fun PreviewText(){
    TextFieldMapSearch(
        value = "",
        onValueChange = {},
        placeholder = "Search",
        isFocused = remember { mutableStateOf(false) },
        onSearch = {}
    )
}
