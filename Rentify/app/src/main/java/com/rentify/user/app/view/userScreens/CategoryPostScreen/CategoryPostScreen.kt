package com.rentify.user.app.view.userScreens.CategoryPostScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.CategoryPostScreen.components.SpacerHeightCompose
import com.rentify.user.app.R

@Composable
fun CategoryPostScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xffffffff))
                .padding(10.dp)
        ) {
            CategoryTopBar(navController)
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)) // Thêm bo tròn
                    .background(color = Color(0xffffffff))
                    .padding(10.dp)
                    .clickable(
                        indication = null, // Loại bỏ hiệu ứng Ripple
                        interactionSource = remember { MutableInteractionSource() }, // Quản lý trạng thái click mà không hiển thị hiệu ứng
                        onClick = {
                            navController.navigate("SEARCHPOSTROOM")
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.room),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp, 35.dp)
                )
                Text(
                    text = "Tìm phòng",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            SpacerHeightCompose(height = 15)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)) // Thêm bo tròn
                    .background(color = Color(0xffffffff))
                    .padding(10.dp)

                    .clickable(
                        indication = null, // Loại bỏ hiệu ứng Ripple
                        interactionSource = remember { MutableInteractionSource() }, // Trạng thái click nhưng không hiệu ứng
                        onClick = {
                            navController.navigate("SEARCHPOSTROOMATE")
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp, 35.dp)
                )
                Text(
                    text = "Tìm người ở ghép",
                    //  fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutCategoryPostScreen() {
    CategoryPostScreen(navController = rememberNavController())
}

@Composable
fun CategoryTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(
            onClick = { navController.popBackStack() }
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        androidx.compose.material.Text(
            text = "Quản lý bài đăng",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}
