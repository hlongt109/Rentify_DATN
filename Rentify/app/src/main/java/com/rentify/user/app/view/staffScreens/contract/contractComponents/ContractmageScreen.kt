package com.rentify.user.app.view.staffScreens.contract.contractComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.view.userScreens.contractScreen.components.ContractBody
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContractScreenPreview(){
   // ContractScreen(navController= rememberNavController())
}

@Composable
fun ContractImageScreen(navController: NavHostController, contractId: String) {
    val contractViewModel: ContractViewModel = viewModel()
    val contractDetail by contractViewModel.contractDetail.observeAsState()

    // Lấy chi tiết hợp đồng
    LaunchedEffect(contractId) {
        contractViewModel.fetchContractDetail(contractId)
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxWidth() // Đảm bảo chiều rộng đầy đủ
    ) {
        HeaderSection(backgroundColor = Color.White, title = "Văn bản hợp đồng", navController = navController)

        contractDetail?.let { contract ->
            // LazyColumn để hiển thị danh sách ảnh hợp đồng
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                // Nếu có ảnh trong hợp đồng
                items(contract.photos_contract!!) { photoUrl ->
                    val imageUrl = "http://10.0.2.2:3000/$photoUrl" // Gắn "localhost" vào trước URL

                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
