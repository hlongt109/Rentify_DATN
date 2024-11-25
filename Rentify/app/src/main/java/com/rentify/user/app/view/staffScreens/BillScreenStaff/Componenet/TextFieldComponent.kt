package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.greenInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFiledComponent(
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    placeHolder: String,
    isFocused: MutableState<Boolean>,
    isShowIcon: Boolean = false,
    isIcon: Any? =null
){
    var showIcon = remember { mutableStateOf(false) }
    Column {

        Text(
            text = placeHolder,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ColorBlack,
            modifier = Modifier.padding(bottom = 10.dp, start = 5.dp, top = 15.dp)
        )

        TextField(
            value = value,
            onValueChange = { newValue ->
                // Kiểm tra xem onValueChange có khác null không
                onValueChange?.invoke(newValue)
            },
            placeholder = {Text(text = placeHolder)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(
                    width = 1.dp,
                    color = if (isFocused.value) colorLocation else colorInput,
                    shape = RoundedCornerShape(15.dp)
                )
                .focusable()
                .onFocusChanged { focusState -> isFocused.value = focusState.isFocused }
                .clickable(
                    enabled = onClick != null,
                    onClick = {
                        // Gọi onClick nếu onClick khác null
                        onClick?.invoke()
                    }
                )
            ,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorInput,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorLocation
            ),
            shape = RoundedCornerShape(15.dp),
            // Thêm trailing icon cho password
            trailingIcon = if (isShowIcon) {
                {
                    IconButton(onClick = { showIcon.value = !showIcon.value }) {
                        when(isIcon){
                            is ImageVector ->
                                Icon(
                                    imageVector = if (showIcon.value)
                                        isIcon
                                    else
                                        isIcon,
                                    contentDescription = if (showIcon.value)
                                        "Up"
                                    else
                                        "Down"
                                )
                            is Int -> Image(painter = painterResource(isIcon), contentDescription = null)
                        }
                    }
                }
            } else null,
        )
    }
}
