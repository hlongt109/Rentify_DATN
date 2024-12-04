package com.rentify.user.app.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.platform.LocalConfiguration
import com.google.gson.Gson
import com.rentify.user.app.model.Service
import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CheckUnit {

    fun Uri.toFilePath(context: Context): String? {
        return try {
            when (scheme) {
                "content" -> {
                    // Content URI handling (e.g., from Gallery)
                    val projection = arrayOf(MediaStore.Images.Media.DATA)
                    context.contentResolver.query(this, projection, null, null, null)?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            val path = cursor.getString(columnIndex)
                            Log.d("UriConversion", "Content URI path: $path")
                            return path
                        }
                    }
                    Log.w("UriConversion", "Unable to resolve content URI")
                    null
                }
                "file" -> {
                    // Direct file URI
                    Log.d("UriConversion", "File URI path: $path")
                    path
                }
                else -> {
                    // Alternative method for complex URIs (Google Drive, other apps)
                    val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
                    try {
                        context.contentResolver.openInputStream(this)?.use { inputStream ->
                            tempFile.outputStream().use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                        Log.d("UriConversion", "Copied URI to temp file: ${tempFile.absolutePath}")
                        tempFile.absolutePath
                    } catch (e: Exception) {
                        Log.e("UriConversion", "Error copying URI to temp file", e)
                        null
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UriConversion", "Comprehensive URI conversion error", e)
            null
        }
    }


    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val isDocumentUri = DocumentsContract.isDocumentUri(context, uri)

        return when {
            isDocumentUri -> {
                val documentId = DocumentsContract.getDocumentId(uri)
                val split = documentId.split(":")
                val type = split[0]

                if (type.equals("primary", ignoreCase = true)) {
                    return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }
                null
            }
            uri.scheme.equals("content", ignoreCase = true) -> {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    if (cursor.moveToFirst()) {
                        return cursor.getString(columnIndex)
                    }
                }
                null
            }
            uri.scheme.equals("file", ignoreCase = true) -> {
                uri.path
            }
            else -> null
        }
    }


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    fun formattedPrice(price: Float): String {
        val formatted = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).apply {
            currency = Currency.getInstance("VND")
            maximumFractionDigits = 0 // Không hiển thị phần thập phân
        }.format(price)
        return formatted.replace("₫", " VND") // Thay thế "₫" bằng " VND"
    }

    fun formatYear(date: Date): String {
        val format = SimpleDateFormat("yyyy", Locale.getDefault()) // Chỉ định định dạng năm
        return format.format(date)
    }
    fun formatMonth(date: Date): String {
        val format = SimpleDateFormat("MM/yyyy", Locale.getDefault()) // Chỉ định định dạng năm
        return format.format(date)
    }

    fun formatDay(date: Date): String{
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Chỉ định định dạng năm
        return format.format(date)
    }
    fun formatDay_2(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC") // Đảm bảo đúng định dạng ISO 8601
        return format.format(date)
    }

    fun formatTimeMessage(timestamp: Long): String{
        val date = Date(timestamp)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun parseDate(dateString: String): Date {
        return try {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ) // Hoặc định dạng ngày tháng tùy thuộc vào định dạng của bạn
            dateFormat.parse(dateString)
                ?: Date() // Trả về Date mặc định nếu parse không thành công
        } catch (e: Exception) {
            Date() // Trả về Date mặc định nếu gặp lỗi khi parse
        }
    }

    // 2. Tạo extension function để encode/decode Service
    fun Service.toJson(): String {
        return URLEncoder.encode(Gson().toJson(this), "UTF-8")
    }

    fun String.toService(): Service? {
        return try {
            Gson().fromJson(URLDecoder.decode(this, "UTF-8"), Service::class.java)
        } catch (e: Exception) {
            null
        }
    }
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp
//    val screenHeight = configuration.screenHeightDp
}