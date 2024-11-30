package com.rentify.user.app.view.contract.contractComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.R
import com.rentify.user.app.model.Contract
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel


@Composable
fun ContractRoomListScreen(navController: NavController,manageId:String) {
    val viewModel: ContractViewModel = viewModel()
    val contracts by viewModel.contracts.observeAsState(emptyList())
    val context = LocalContext.current
    val error by viewModel.error.observeAsState()
    LaunchedEffect(manageId) {
        viewModel.fetchContractsByBuilding(manageId)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
            items(contracts) { contract ->
                ContractCard(contract , onClick = {

                    navController.navigate("contract_detail/${contract._id}")
                }, )
        }
    }
}

@Composable
fun ContractCard(contract: Contract ,onClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() } // Thêm clickable vào đây
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            // Room Image

            Image(
                painter = painterResource(id = R.drawable.contract),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
            )

            // Room Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Số hợp đồng:   ${contract.building_id?.nameBuilding}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Loại hợp đồng:   ${contract.duration}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Building: ${contract.building_id?.nameBuilding ?: "Unknown"}")
//           Text(text = "Room: ${contract.duration?: "Unknown"}")
//           // Text(text = "Start Date: ${contract.start_date ?: "N/A"}")
//          //  Text(text = "End Date: ${contract.end_date ?: "N/A"}")
//        }
//    }
}