import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import io.ktor.websocket.Frame
import java.util.Calendar

@Composable
fun DatePickerField(

    selectedDate: String,
    onDateSelected: (String) -> Unit,
    placeholder: String
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Trạng thái lưu trữ ngày được chọn
    val year = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val month = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    // Hiển thị DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val formattedDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)

            onDateSelected(formattedDate)
            year.value = selectedYear
            month.value = selectedMonth
            day.value = selectedDayOfMonth
        },
        year.value,
        month.value,
        day.value
    )

    OutlinedTextField(
        value = selectedDate.ifEmpty { "" },
        onValueChange = {},
        readOnly = true, // Chỉ hiển thị, không cho phép người dùng chỉnh sửa trực tiếp
        placeholder = { Text(text = placeholder, color = Color.Gray,  style = TextStyle(fontSize = 14.sp)) }, // Hiển thị placeholder
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            //.clickable { datePickerDialog.show() },
        shape = RoundedCornerShape(8.dp), // Đặt bo tròn 12dp// Hiển thị DatePicker khi click
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.date),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { datePickerDialog.show()  }
            )
        }
    )
}
