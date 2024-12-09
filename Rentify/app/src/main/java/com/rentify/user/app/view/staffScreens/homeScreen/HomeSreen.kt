package com.rentify.user.app.view.staffScreens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.MainActivity
import com.rentify.user.app.view.staffScreens.homeScreen.Components.HeaderSection
import com.rentify.user.app.view.staffScreens.homeScreen.Components.ListFunction
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scrollState = rememberScrollState()

    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    val onComfortableSelected: (String) -> Unit = { option ->
        selectedComfortable = if (selectedComfortable.contains(option)) {
            selectedComfortable - option
        } else {
            selectedComfortable + option
        }

        when (option) {
            "Tòa nhà & căn hộ" -> {
                navController.navigate(MainActivity.ROUTER.BUILDING.name)
            }

            "Hợp đồng" -> {
                navController.navigate(MainActivity.ROUTER.CONTRACT_STAFF.name)
            }

            "Hoá đơn" -> {
                navController.navigate(MainActivity.ROUTER.BILL_STAFF.name)
            }

            "Sự cố & bảo trì" -> {
                navController.navigate(MainActivity.ROUTER.REPORT_STAFF.name)
            }

            "Tin nhắn" -> {
                navController.navigate(MainActivity.ROUTER.ADDROOM.name)
            }

            "Bài đăng" -> {
                navController.navigate(MainActivity.ROUTER.POSTING_STAFF.name)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()

        ) {
            HeaderSection(navController)
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()

                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClickablePieChartDemo()
                }
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 20.dp),
                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ComfortableOptionsDemo(
                            selectedComfortable = selectedComfortable,
                            onComfortableSelected = onComfortableSelected,
                            navController = navController // Truyền NavController vào
                        )
                    }
                }
            }
        }
    }
}

data class Slice(val value: Float, val color: Int, val label: String)

@Composable
fun ClickablePieChartDemo() {
    val slices = arrayListOf(
        Slice(40f, 0xFF6200EE.toInt(), "Hoá đơn đã thu"),
        Slice(30f, 0xFF03DAC5.toInt(), "Hoá đơn chưa thu"),
    )

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            val pieChart = com.github.mikephil.charting.charts.PieChart(context)
            val entries = slices.map {
                com.github.mikephil.charting.data.PieEntry(it.value, it.label)
            }
            val dataSet = PieDataSet(entries, "")
            dataSet.colors = slices.map { it.color }
            dataSet.setDrawValues(true)
            dataSet.valueTextColor = 0xFFFFFFFF.toInt()
            dataSet.valueTextSize = 13f
            val valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
            dataSet.valueFormatter = valueFormatter
            val pieData = PieData(dataSet)
            pieChart.data = pieData
            pieChart.description.isEnabled = false
            pieChart.setUsePercentValues(false)
            pieChart.setDrawSliceText(false)
            pieChart.centerText = ""
            pieChart.invalidate()
            pieChart
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutHomeScreen() {
    HomeScreen(navController = rememberNavController())
}

data class ComfortableOptionData(val text: String, val imageRes: Int)

@Composable
fun ComfortableOptionsDemo(
    selectedComfortable: List<String>,
    onComfortableSelected: (String) -> Unit,
    navController: NavController // Nhận NavController từ phía ngoài
) {
    val comfortableOptions = listOf(
        ComfortableOptionData("Tòa nhà & căn hộ", R.drawable.building),
        ComfortableOptionData("Hợp đồng", R.drawable.contrac1),
        ComfortableOptionData("Hoá đơn", R.drawable.bill1),
        ComfortableOptionData("Sự cố & bảo trì", R.drawable.inciden),
        ComfortableOptionData("Tin nhắn", R.drawable.mess),
        ComfortableOptionData("Bài đăng", R.drawable.post)
    )

    FlowRow(
        modifier = Modifier,
        mainAxisSpacing = 20.dp,
        crossAxisSpacing = 10.dp
    ) {
        comfortableOptions.forEach { option ->
            ListFunction(
                text = option.text,
                imageRes = option.imageRes,
                isSelected = selectedComfortable.contains(option.text),
                onClick = {
                    onComfortableSelected(option.text)
                }
            )
        }
    }
}
