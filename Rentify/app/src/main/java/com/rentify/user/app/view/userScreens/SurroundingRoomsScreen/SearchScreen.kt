package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.rentify.user.app.MainActivity
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.location
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component.TextFieldMapSearch
import com.rentify.user.app.viewModel.UserViewmodel.AutofillViewModel

@Composable
fun SearchMapScreen(
    navController: NavController,
    viewModel: AutofillViewModel = viewModel(),
) {
    val context = LocalContext.current
    var placeholder by remember { mutableStateOf("Tìm kiếm vị trí") }
    var searchQuery by remember { mutableStateOf("") }
    val suggestion by viewModel.suggestions.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    val searchNull by viewModel.searchNull.observeAsState()
    val pointLocation by viewModel.pointLocation.observeAsState()
    val selectedLocation by viewModel.selectedLocation.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {

        Column(

        ) {
            HeaderBar(navController, "Tìm kiếm vị trí")
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                TextFieldMapSearch(
                    placeholder = placeholder,
                    value = searchQuery,
                    onValueChange = { newSearch ->
                        searchQuery = newSearch
                        Log.d("CheckSearch2", "MapboxMapView: $searchQuery")
                        viewModel.searchLocation(context, searchQuery)
                    },
                    isFocused = remember { mutableStateOf(false) },
                    onSearch = {
                        viewModel.searchLocation(context = context, searchQuery)
                    },
                    enable = true
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                if (isLoading == true) {
                    CircularProgressIndicator(color = colorLocation)
                } else {
                    if (searchResults != null) {
                        LazyColumn() {
                            items(searchResults) { item ->
                                var itemCoordinate by remember { mutableStateOf<Point?>(null) }
                                LaunchedEffect(item) {
                                    itemCoordinate = viewModel.getCoordinates(item)
                                }
                                Log.d("ItemCoordinate", "SearchMapScreen: $itemCoordinate")
                                ItemSearch(
                                    item = item,
                                    onClick = {
                                        itemCoordinate?.let { it1 ->
                                            viewModel.setSelectedLocation(
                                                it1
                                            ) {
                                                navController.navigate(MainActivity.ROUTER.ROOMMAP.name + "/${selectedLocation?.latitude()},${selectedLocation?.longitude()}")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        searchNull?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                maxLines = 2,
                                overflow = TextOverflow.Clip
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemSearch(
    item: PlaceAutocompleteSuggestion,
    viewModel: AutofillViewModel = viewModel(),
    onClick: (Point?) -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = ""
    )
    Log.d("CheckViTri", "ItemSearch: ${item.coordinate}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 10.dp, end = 10.dp)
            .drawBehind {
                // Vẽ border dưới
                // Vẽ border dưới
                drawLine(
                    color = Color.Gray,  // Màu của đường viền
                    start = Offset(0f, size.height),  // Bắt đầu từ góc dưới bên trái
                    end = Offset(size.width, size.height),  // Kết thúc tại góc dưới bên phải
                    strokeWidth = 1f  // Độ dày của đường viền
                )
            }
            .clickable(
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick(item.coordinate)
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when {
                            event.type == PointerEventType.Press -> isPressed = true
                            event.type == PointerEventType.Release -> isPressed = false
                        }
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(location),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Log.d("CheckName", "ItemSearch: ${item.name}")
        item.name?.let {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Clip
            )
        }
    }
}