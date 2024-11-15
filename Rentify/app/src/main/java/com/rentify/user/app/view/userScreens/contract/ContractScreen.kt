package com.rentify.user.app.view.userScreens.contract

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.CategoryPostScreen.components.SpacerHeightCompose
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.cancelContract.components.CancelContractOptions
import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.view.userScreens.cancelContract.components.ViewContractButton
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose

@Composable
fun ContractScreen(navController: NavController)
{
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    var context = LocalContext.current

    if (isShowDialog) {
        DialogCompose(
            onConfirmation = {

                isShowDialog = false

                Toast.makeText(
                    context,
                    "Hủy thành công",
                    Toast.LENGTH_SHORT
                ).show()
navController.navigate("CANCELCONTRACT")
            },
            onCloseDialog = { isShowDialog = false },
            titleDialog = "Thông báo !",
            mess = "Bạn có chắc chắn muốn hủy hơph đồng không?"
        )
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff3f3f3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(bottom = screenHeight.dp / 6f)
        ) {
            HeaderSection(backgroundColor = Color.White, title = "Xem hợp đồng", navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
            Column( modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier

                        .background(color = Color(0xFFffffff))
                ){
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .background(color = Color(0xFFffffff))
                    ) {
                        Text(
                            text = "Thông tin hợp đồng",
                            color = Color.Black,
                            fontWeight = FontWeight(700),
                            fontSize = 17.sp
                        )
                        ContractInfoRow("Số hợp đồng", "ABC/1234")
                        ContractInfoRow("Loại hợp đồng", "6 tháng")
                        ContractInfoRow("Thời hạn ký kết", "01/09/2024")
                        ContractInfoRow("Thời hạn kết thúc", "01/04/2024")
                        ContractInfoRow("Tiền cọc", "3.500.000")
                        ContractInfoRow("Tiền thuê", "3.500.000 VND / tháng")
                        ContractInfoRow("Kỳ thanh toán", "01 - 05 hằng tháng")
                    }}
                Spacer(modifier = Modifier
                    .background(color = Color(0xFFeeeeee))
                    .fillMaxWidth()
                    .height(25.dp))
                Box(
                    modifier = Modifier

                        .padding(15.dp)
                        .fillMaxWidth()){
                    ViewContractButton(onClick = { navController.navigate("CONTRACT") })
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp/6f)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xfff5f5f5)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nút Hủy hợp đồng
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .background(color = Color(0xffffffff))
                        .clickable(
                            onClick = { isShowDialog = true },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF84d8ff),
                            shape = RoundedCornerShape(9.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = "Hủy hợp đồng",
                        color = Color(0xff000000),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa hai nút

                // Nút Gia hạn hợp đồng
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .clickable(
                            onClick = { /* Handle extend contract */ },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .background(
                            color = Color(0xFF84d8ff),
                            shape = RoundedCornerShape(9.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = "Gia hạn hợp đồng",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }


        }}}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutContractScreen() {
    ContractScreen(navController = rememberNavController())
}