import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetExample() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true // Set true to skip half expanded state
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Main Content
    Box(modifier = Modifier.fillMaxSize()) {
        // Button to show bottom sheet
        Button(
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text("Show Bottom Sheet")
        }

        // Bottom Sheet
//        if (showBottomSheet) {
//            ModalBottomSheetLayout(
//                sheetState = sheetState,
////                sheetContent = {
////                    BottomSheetContent(
//////                        onDismiss = {
//////                            scope.launch {
//////                                sheetState.hide()
//////                                showBottomSheet = false
//////                            }
//////                        }
////                    )
////                },
//                modifier = Modifier.fillMaxSize(),
//                sheetBackgroundColor = MaterialTheme.colors.surface,
//                scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.32f)
//            ) {
//                // Content behind the bottom sheet
//                Box(modifier = Modifier.fillMaxSize())
//            }
//
//            // Launch bottom sheet when showBottomSheet becomes true
//            LaunchedEffect(showBottomSheet) {
//                scope.launch {
//                    sheetState.show()
//                }
//            }
//        }
    }
}

@Composable
fun BottomSheetContent(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Handle or Drag indicator
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .padding(bottom = 16.dp)
                .background(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(2.dp)
                )
        )
        //hien thi noi dung duoc truyen vao
        content()
    }
}

@Composable
fun BottomSheetLocation(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxHeight(0.6f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Handle or Drag indicator
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .padding(bottom = 16.dp)
                .background(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        // Content
        Text(
            text = "Bottom Sheet Title",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "This is the content of the bottom sheet. You can add any composable here.",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Example buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle action */ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text("Confirm")
            }

            OutlinedButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    }
}


// Usage example in your screen
@Composable
fun MyScreen() {
    BottomSheetExample()
}