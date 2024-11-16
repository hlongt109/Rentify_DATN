package com.rentify.user.app.view.userScreens.SearchRoomScreen

import BottomSheetContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.model.Room
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ArrangeComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ChangeLocation
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.HeaderSearchComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemPost
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemTypeRoom
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.LocationComponent
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostRoomScreen(navController: NavController) {
    val list = FakeData().rooms
    val listPost = FakeData().listPost
    val listLocation = FakeData().listLocations
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetHeight by remember { mutableStateOf(0.6f) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    // Theo dõi trạng thái của BottomSheet
    LaunchedEffect(sheetState.currentValue) {
        showBottomSheet = sheetState.currentValue != ModalBottomSheetValue.Hidden
    }

    // Xử lý back press
    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
            showBottomSheet = false
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                    }
                },
                content = {
//                    ChangeLocation(listLocation) { isFocused ->
//                        bottomSheetHeight = if (isFocused) 0.8f else 0.6f
//                    }
                    ChangeLocation(listLocation) { isFocused ->
                        bottomSheetHeight = if (isFocused) 0.8f else 0.6f
                    }
                },
                maxHeight = bottomSheetHeight
            )
        },
        modifier = Modifier.fillMaxSize(),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .clickable(
                    enabled = sheetState.isVisible,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    }
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HeaderSearchComponent(navController)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    ArrangeComponent(navController)
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(list) { item ->
                            ItemTypeRoom(
                                item,
                                isSelected = item == selectedRoom,
                                onClick = { selectedRoom = item })
                        }
                    }

                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Divider(color = colorInput, thickness = 1.dp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) {
                        LocationComponent(
                            enabled = !sheetState.isVisible,
                            onShowBottomSheet = {
                                scope.launch {
                                    showBottomSheet = true
                                    sheetState.show()
                                }
                            }
                        )
                    }
                    Divider(color = colorInput, thickness = 1.dp)
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(listPost) { item ->
                            ItemPost(item)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearch() {
    val navController = rememberNavController()
    PostRoomScreen(navController)
}