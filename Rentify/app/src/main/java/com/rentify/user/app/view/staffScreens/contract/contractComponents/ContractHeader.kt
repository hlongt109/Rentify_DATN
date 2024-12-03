package com.rentify.user.app.view.contract.contractComponents

import android.R.attr.singleLine
import android.R.attr.textStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ContractTopBar(
    onClickGoBack: () -> Unit,
    contractViewModel: ContractViewModel
) {

    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClickGoBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Quay lại"
                )
            }
            ContractSearchBar(
                searchText = contractViewModel.searchQuery.value,
                onSearchTextChanged = { contractViewModel.onSearchQueryChange(it) }
            )
        }
    }
}

@Composable
fun ContractSearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Tìm kiếm"
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChanged("") }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Xóa")
                }
            }
        },
        placeholder = { Text("Nhập thông tin tìm kiếm...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF0F0F0))
    )
}