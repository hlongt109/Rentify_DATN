package com.rentify.user.app.view.userScreens.DieuKhoanChinhSach

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.DieuKhoanChinhSach.Components.DKCSTopBar

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DieuKhoanChinhSachPreview() {
    DieuKhoanChinhSach(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DieuKhoanChinhSach(navController: NavHostController) {
    var isBottomSheetBaoMat by remember { mutableStateOf(false) }
    var selectedSection by remember { mutableStateOf("") }

    if (isBottomSheetBaoMat) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetBaoMat = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Điều khoản và chính sách",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
                PolicyBottomSheetContent(
                    onSelect = { section ->
                        selectedSection = section
                        isBottomSheetBaoMat = false
                    },
                    onClose = { isBottomSheetBaoMat = false }
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DKCSTopBar(navController)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                when (selectedSection) {
                    "PrivacyPolicy" -> {
                        Text("Điều 1: Chính Sách Bảo Mật", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("1.1. Mục đích thu thập thông tin:\n" +
                                "Chúng tôi thu thập thông tin cá nhân của người dùng, bao gồm nhưng không giới hạn ở tên, số điện thoại, địa chỉ email, địa chỉ cư trú, thông tin thanh toán, và các thông tin khác liên quan đến việc đăng ký và sử dụng dịch vụ cho thuê phòng trọ. Mục đích thu thập thông tin là để cung cấp các dịch vụ cho thuê phòng trọ, duy trì và nâng cao chất lượng dịch vụ, phục vụ nhu cầu hỗ trợ khách hàng và giao dịch thanh toán của người dùng, đồng thời đảm bảo tuân thủ các yêu cầu pháp lý.\n" +
                                "\n" +
                                "1.2. Cam kết bảo mật:\n" +
                                "Chúng tôi cam kết bảo vệ quyền lợi của người dùng trong việc bảo mật thông tin cá nhân. Mọi thông tin cá nhân của người dùng sẽ được bảo vệ bằng các biện pháp bảo mật tiên tiến, không tiết lộ cho bên thứ ba nếu không có sự đồng ý của người dùng, trừ khi có yêu cầu của cơ quan nhà nước có thẩm quyền hoặc khi có sự đồng ý của người dùng.\n" +
                                "\n" +
                                "1.3. Chia sẻ thông tin:\n" +
                                "Chúng tôi chỉ chia sẻ thông tin cá nhân của người dùng với các đối tác và nhà cung cấp dịch vụ khi có sự đồng ý của người dùng hoặc khi cần thiết để thực hiện nghĩa vụ theo hợp đồng. Các đối tác của chúng tôi cam kết bảo mật thông tin của người dùng và chỉ sử dụng thông tin đó vào mục đích phục vụ việc cung cấp dịch vụ cho thuê phòng trọ.\n" +
                                "\n" +
                                "1.4. Quyền của người dùng:\n" +
                                "Người dùng có quyền yêu cầu truy cập, chỉnh sửa hoặc yêu cầu xóa thông tin cá nhân của mình nếu thông tin đó không còn cần thiết hoặc không chính xác. Để thực hiện các quyền này, người dùng có thể liên hệ với chúng tôi qua các phương thức liên lạc đã được công khai trong ứng dụng.\n" +
                                "\n" +
                                "1.5. Thời gian lưu trữ thông tin:\n" +
                                "Chúng tôi chỉ lưu trữ thông tin cá nhân của người dùng trong thời gian cần thiết để cung cấp dịch vụ hoặc khi có yêu cầu pháp lý từ cơ quan nhà nước có thẩm quyền. Khi hết thời gian lưu trữ, chúng tôi sẽ thực hiện các biện pháp xóa hoặc ẩn thông tin cá nhân của người dùng.")
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                    "TermsOfService" -> {
                        Text("Điều 2: Điều khoản sử dụng", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("2.1. Đăng ký tài khoản:\n" +
                                "Để sử dụng dịch vụ cho thuê phòng trọ, người dùng phải đăng ký tài khoản trên ứng dụng và cung cấp thông tin cá nhân chính xác và đầy đủ. Người dùng có trách nhiệm cập nhật kịp thời thông tin khi có sự thay đổi. Mọi giao dịch liên quan đến tài khoản đăng ký của người dùng sẽ được xem là hợp lệ và có hiệu lực pháp lý.\n" +
                                "\n" +
                                "2.2. Quyền và nghĩa vụ của người thuê phòng:\n" +
                                "Người thuê phòng có quyền yêu cầu chủ trọ cung cấp dịch vụ theo đúng thỏa thuận, bao gồm các tiện ích phòng trọ như điện, nước, bảo vệ, an ninh và các dịch vụ khác. Người thuê có nghĩa vụ thanh toán đầy đủ và đúng hạn tiền thuê phòng, các chi phí sử dụng dịch vụ và các khoản phí khác theo quy định của hợp đồng. Người thuê không được phép sử dụng phòng trọ vào mục đích trái pháp luật, gây rối trật tự công cộng, hoặc gây thiệt hại cho tài sản của chủ trọ hoặc các bên liên quan.\n" +
                                "\n" +
                                "2.3. Quyền và nghĩa vụ của chủ trọ:\n" +
                                "Chủ trọ có trách nhiệm cung cấp thông tin chính xác về phòng trọ, đảm bảo chất lượng phòng trọ và các dịch vụ liên quan như điện, nước, bảo vệ an ninh. Chủ trọ có nghĩa vụ duy trì các điều kiện đảm bảo an toàn cho người thuê phòng, thực hiện bảo trì, sửa chữa kịp thời khi có yêu cầu từ người thuê. Chủ trọ không được phép thu thêm các khoản phí không thỏa thuận trong hợp đồng mà không có sự đồng ý của người thuê.\n" +
                                "\n" +
                                "2.4. Vi phạm và xử lý vi phạm:\n" +
                                "Nếu một trong hai bên vi phạm các điều khoản trong hợp đồng, bên còn lại có quyền yêu cầu xử lý vi phạm. Mọi vi phạm sẽ được giải quyết bằng các biện pháp thương lượng hoặc theo quy định của pháp luật. Trong trường hợp tranh chấp không thể giải quyết bằng thương lượng, các bên có thể yêu cầu trọng tài hoặc đưa ra cơ quan có thẩm quyền giải quyết theo quy định của pháp luật Việt Nam.\n" +
                                "\n" +
                                "2.5. Thay đổi và chấm dứt hợp đồng:\n" +
                                "Chúng tôi có quyền thay đổi, sửa đổi các điều khoản trong hợp đồng mà không cần thông báo trước. Các thay đổi sẽ có hiệu lực ngay khi được công khai trên ứng dụng hoặc website. Người dùng có quyền chấm dứt hợp đồng và ngừng sử dụng dịch vụ bất cứ lúc nào, tuy nhiên, người dùng vẫn có nghĩa vụ thanh toán đầy đủ các khoản phí chưa thanh toán.")
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                    "OperationalRegulation" -> {
                        Text("Điều 3: Quy chế hoạt động", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("3.1. Quy trình đặt phòng:\n" +
                                "Người dùng có thể tìm kiếm phòng trọ trên ứng dụng, chọn phòng và thực hiện thủ tục đặt phòng. Thông tin về phòng trọ, giá cả, các điều kiện dịch vụ sẽ được cung cấp rõ ràng trước khi người dùng xác nhận việc đặt phòng. Sau khi đặt phòng thành công, người dùng sẽ nhận được thông tin chi tiết về thời gian nhận phòng và các yêu cầu thanh toán.\n" +
                                "\n" +
                                "3.2. Quyền và nghĩa vụ của người thuê:\n" +
                                "Người thuê có quyền sử dụng phòng trọ trong thời gian đã thỏa thuận và theo các điều kiện của hợp đồng. Người thuê có nghĩa vụ bảo vệ tài sản của chủ trọ, tuân thủ các quy định về an toàn, bảo vệ môi trường và không làm ảnh hưởng đến sự an toàn của các người thuê khác. Nếu có yêu cầu sửa chữa hoặc bảo trì phòng trọ, người thuê phải thông báo kịp thời cho chủ trọ để được hỗ trợ.\n" +
                                "\n" +
                                "3.3. Quyền và nghĩa vụ của chủ trọ:\n" +
                                "Chủ trọ có quyền yêu cầu người thuê tuân thủ các điều khoản trong hợp đồng, bao gồm thanh toán đúng hạn và bảo vệ tài sản. Chủ trọ có nghĩa vụ đảm bảo chất lượng phòng trọ và các dịch vụ liên quan, bảo trì và sửa chữa khi cần thiết. Chủ trọ phải đảm bảo an toàn cho người thuê và không để xảy ra sự cố ảnh hưởng đến quyền lợi của người thuê.\n" +
                                "\n" +
                                "3.4. Giải quyết tranh chấp:\n" +
                                "Trong trường hợp có tranh chấp xảy ra, các bên sẽ cố gắng giải quyết bằng thương lượng và hòa giải. Nếu không thể giải quyết được, các bên có thể yêu cầu giải quyết thông qua trọng tài hoặc thông qua tòa án theo quy định của pháp luật Việt Nam. Mọi tranh chấp phát sinh sẽ được giải quyết theo luật pháp Việt Nam.\n" +
                                "\n" +
                                "3.5. Điều kiện và hạn chế sử dụng dịch vụ:\n" +
                                "Chúng tôi có quyền từ chối cung cấp dịch vụ cho thuê phòng trọ đối với những người vi phạm các quy định của hợp đồng hoặc có hành vi gây rối, phá hoại tài sản. Chúng tôi cũng có quyền tạm ngừng cung cấp dịch vụ trong trường hợp có sự cố kỹ thuật hoặc sự cố bất khả kháng.")
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                    else -> {
                        Text("Vui lòng chọn một mục để xem nội dung.")
                    }
                }
            }
        }

        Button(
            onClick = { isBottomSheetBaoMat = true },
            modifier = Modifier
                .align(Alignment.BottomCenter) // Đảm bảo nút ở cuối màn hình
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFd2f1ff))
        ) {
            Text("Chọn mục điều khoản", color = Color(0xff44acfe))
        }
    }
}


@Composable
fun PolicyBottomSheetContent(onSelect: (String) -> Unit, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onSelect("PrivacyPolicy") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1)),
        ) {
            Text("Chính sách bảo mật")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onSelect("TermsOfService") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 45.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1))
        ) {
            Text("Điều khoản sử dụng")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onSelect("OperationalRegulation") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1))
        ) {
            Text("Quy chế hoạt động")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("Đóng", color = Color.White)
        }
    }
}





