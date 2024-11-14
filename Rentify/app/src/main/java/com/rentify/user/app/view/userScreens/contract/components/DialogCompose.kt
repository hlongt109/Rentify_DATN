package com.rentify.user.app.view.userScreens.contract.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rentify.user.app.R

@Composable
fun DialogCompose(
    onConfirmation: () -> Unit,
    onCloseDialog: () -> Unit,
    titleDialog: String,
    mess: String
) {

    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 3.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = titleDialog,
                    fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                )
                Text(
                    text = mess,
                    color = Color(0xff888888),
                //    fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    fontSize = 14.sp,
                 //   fontWeight = FontWeight(400),
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    Button(
                        onClick = onCloseDialog,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                end = 10
                                    .dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Text(
                            text = "Quay lại",
                            color = Color(0xff2e90fa),
                            fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp,
                        )
                    }
                    Button(
                        onClick = onConfirmation,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Xác nhận",
                            color = Color(0xfff04438),
                            fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp
                        )
                    }

                }
            }

        }
    }

}

@Composable
fun GetLayoutTestDialog() {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showDialog) {
            DialogCompose(
                onConfirmation = {

                },
                titleDialog = "Thông báo",
                mess = "Bạn có chắc chắn muốn xóa món ăn này không ?",
                onCloseDialog = {
                    showDialog = false
                }
            )
        }

        Button(
            onClick = { showDialog = true }
        ) {
            Text(text = "Show dialog")
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingGetLayoutTestDialog() {
    GetLayoutTestDialog()
}