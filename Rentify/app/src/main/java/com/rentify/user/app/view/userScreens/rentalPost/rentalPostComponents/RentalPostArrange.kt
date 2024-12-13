package com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RentalPostArrange(
    onSortSelected: (String) -> Unit,
    onPriceRangeSelected: (Int, Int) -> Unit
) {
    var isSortMenuExpanded by remember { mutableStateOf(false) }
    var isPriceMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sort Button with Dropdown
        Box {
            Button(
                onClick = { isSortMenuExpanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.width(120.dp) // Cố định chiều rộng
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Sắp xếp theo",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Down",
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            DropdownMenu(
                expanded = isSortMenuExpanded,
                onDismissRequest = { isSortMenuExpanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                DropdownMenuItem(onClick = {
                    isSortMenuExpanded = false
                    onSortSelected("price_asc")
                }) {
                    Text(text = "Giá thấp nhất")
                }
                DropdownMenuItem(onClick = {
                    isSortMenuExpanded = false
                    onSortSelected("price_desc")
                }) {
                    Text(text = "Giá cao nhất")
                }
            }
        }

        // Price Range Button with Dropdown
        Box {
            Button(
                onClick = { isPriceMenuExpanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.width(120.dp) // Cố định chiều rộng
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Khoảng giá",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Down",
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            DropdownMenu(
                expanded = isPriceMenuExpanded,
                onDismissRequest = { isPriceMenuExpanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                // Hiển thị tất cả
                DropdownMenuItem(onClick = {
                    isPriceMenuExpanded = false
                    onPriceRangeSelected(0, Int.MAX_VALUE) // Giá trị toàn bộ
                }) {
                    Text(text = "Hiển thị tất cả", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    isPriceMenuExpanded = false
                    onPriceRangeSelected(0, 1000000)
                }) {
                    Text(text = "Dưới 1 triệu", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    isPriceMenuExpanded = false
                    onPriceRangeSelected(1000000, 3000000)
                }) {
                    Text(text = "1 triệu đến 3 triệu", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    isPriceMenuExpanded = false
                    onPriceRangeSelected(3000000, 5000000)
                }) {
                    Text(text = "3 triệu đến 5 triệu", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    isPriceMenuExpanded = false
                    onPriceRangeSelected(5000000, Int.MAX_VALUE)
                }) {
                    Text(text = "Trên 5 triệu", color = Color.Black)
                }
            }
        }

    }
}

