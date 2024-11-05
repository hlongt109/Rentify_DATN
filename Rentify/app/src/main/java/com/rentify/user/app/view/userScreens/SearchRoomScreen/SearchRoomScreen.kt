package com.rentify.user.app.view.userScreens.SearchRoomScreen

import BottomSheetLocation
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.colorTextSX
import com.rentify.user.app.ui.theme.down
import com.rentify.user.app.ui.theme.location
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ArrangeComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.HeaderSearchComponent
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemPost
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.ItemTypeRoom
import com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent.LocationComponent
import kotlinx.coroutines.launch


@Composable
fun ListPostRoomScreen(navController: NavController) {
    val list = FakeData().rooms
    val listPost = FakeData().listPost

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
            BottomSheetLocation(
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
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
                    ArrangeComponent()
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(list) { item ->
                            ItemTypeRoom(item)
                        }
                    }

                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Divider(color = colorInput, thickness = 1.dp)
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    LocationComponent(
                        enabled = !sheetState.isVisible,
                        onShowBottomSheet = {
                            scope.launch {
                                showBottomSheet = true
                                sheetState.show()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = 10.dp))
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
    ListPostRoomScreen(navController)
}