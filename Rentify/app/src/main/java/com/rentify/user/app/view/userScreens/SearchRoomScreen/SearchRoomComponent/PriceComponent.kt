package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.utils.CheckUnit
import kotlin.math.round

@Composable
fun PriceComponent() {
    //luu tru gia tri cua thanh truot
    var price by remember { mutableStateOf(0f) }
    val priceFormat = CheckUnit.formattedPrice(price)
    val limitMoney = CheckUnit.formattedPrice(20000000f)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.White)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row {
                Text(
                    text = "Giá từ ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorBlack
                )

                Text(
                    text = priceFormat,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorBlack
                )

                Text(
                    text = " đến ${limitMoney}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorBlack
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Slider(
                    value = price,
                    onValueChange = { // Làm tròn giá trị của thanh trượt thành bội số 500,000
                        price = round(it / 500000) * 500000
                    },
                    valueRange = 0f..20000000f,
//                    steps = 40,
                    modifier = Modifier.width(screenWidth.dp / 1.3f),
                    colors = SliderDefaults.colors(
                        activeTrackColor = colorHeaderSearch,
                        activeTickColor = colorHeaderSearch,
                        thumbColor = colorHeaderSearch,
                        inactiveTrackColor = colorInput_2,
                        inactiveTickColor = colorHeaderSearch,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrice() {
    PriceComponent()
}