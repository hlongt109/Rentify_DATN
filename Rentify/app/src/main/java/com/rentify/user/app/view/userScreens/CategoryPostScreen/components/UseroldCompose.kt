package com.rentify.user.app.view.userScreens.CategoryPostScreen.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UseroldCompose(onClickBack: () -> Unit, column: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cum tứm đim",
                   //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.White,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,
                    )
                },
                navigationIcon = {
                    Row {
                        IconButton(onClick = onClickBack) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.White
                            )

                        }
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Image(
//                                painter = painterResource(id = R.drawable.avatar),
//                                contentDescription = null,
//                                modifier = Modifier.size(60.dp, 50.dp)
//                            )
//                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff252121)
                ),

                )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = Color(0xff000000)
                )
        ) {
            //Spacer(modifier = Modifier.height(1.dp))
            SpacerHeightCompose(height = 1)
            column()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingUseroldCompose() {
    var context = LocalContext.current
    UseroldCompose(onClickBack = {
        Toast.makeText(context, "Compose", Toast.LENGTH_LONG).show()
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xff252121)
                )
        ) {

        }

    }
}