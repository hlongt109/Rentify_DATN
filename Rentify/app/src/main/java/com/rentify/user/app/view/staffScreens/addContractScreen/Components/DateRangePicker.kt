import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.ktor.websocket.Frame
import java.util.Calendar

@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
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
            val formattedDate = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
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
        value = selectedDate,
        onValueChange = {},
        label = { Frame.Text(text = label) },
        readOnly = true, // Chỉ hiển thị, không cho phép người dùng chỉnh sửa trực tiếp
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }, // Hiển thị DatePicker khi click
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Select Date",
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        }
    )
}
