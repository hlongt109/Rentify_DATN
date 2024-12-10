package com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false,
    placeholder: String = ""
) {
    Column(modifier = modifier) {
        // Label
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // TextField
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .background(
                    color = Color.Transparent, shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp, color = Color(0xFF908b8b), shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF989898),
            ),
            enabled = !isReadOnly,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 14.sp,
                            color = Color(0xFF989898)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}