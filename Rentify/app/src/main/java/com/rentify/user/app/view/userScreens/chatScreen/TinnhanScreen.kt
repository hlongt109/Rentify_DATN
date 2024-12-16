package com.rentify.user.app.view.userScreens.chatScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.userScreens.chatScreen.Component.MessageItem
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.ChatViewModel
import com.rentify.user.app.viewModel.UserViewmodel.Message


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TinnhanScreen(
    navController: NavHostController,
    receiverId: String,
    name: String
) {
    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModel.ChatViewModelFactory())
    val messages = remember { mutableStateListOf<Message>() }
//    val  receiverId: String, // ID người nhận
//    senderId: String   // ID người gửi
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val senderId = loginViewModel.getUserData().userId
    Log.d("CheckThongTin", "TinnhanScreen: $receiverId, $name")
    // Tạo chatId duy nhất cho cuộc trò chuyện
    val chatId = chatViewModel.addChatRelation(senderId, receiverId)
    //
    DisposableEffect(chatId) {
        val messageListener: (Message) -> Unit = { message ->
            // Ensure thread-safe addition to the list
            messages.add(message)
        }
        chatViewModel.listenForMessages(chatId.toString(), messageListener)
        onDispose {}
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .imePadding()
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color(0xff84d8ff))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .background(color = Color(0xff84d8ff)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Quay lại",
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        // Scrollable message area
        val scrollState = rememberScrollState()
        LaunchedEffect(messages.size) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            var lastMessageTime: Long? = null // Thời gian của tin nhắn trước đó
            messages.forEachIndexed { index, message ->
                val currentMessageTime = message.timestamp
                Log.d("Message", "TinnhanScreen: ${message.content}")
                Log.d(
                    "MessageSenderId",
                    "Message from: ${message.senderId}, Content: ${message.content}"
                )

                MessageItem(message = message, isSentByCurrentUser = message.senderId == senderId)
                lastMessageTime = currentMessageTime
            }
        }

        // Input area
        var sentText by remember { mutableStateOf(TextFieldValue("")) }

        Row(
            modifier = Modifier
                .fillMaxWidth( )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = sentText,
                onValueChange = { sentText = it },
                placeholder = { Text("Nhập tin nhắn ở đây", color = Color.Gray) },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp) // Kích thước lớn hơn icon
                                .background(
                                    color = Color(0xfffe9a00),
                                    shape = RoundedCornerShape(20.dp)
                                ), // Nền đỏ với góc tròn
                            contentAlignment = Alignment.Center // Căn giữa icon trong box
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.addgim),
                                contentDescription = "Thêm",
                                modifier = Modifier.size(24.dp) // Kích thước icon
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(40.dp) // Kích thước lớn hơn icon
                                .background(
                                    color = Color(0xFF44acfe),
                                    shape = RoundedCornerShape(20.dp)
                                ), // Nền đỏ với góc tròn
                            contentAlignment = Alignment.Center // Căn giữa icon trong box
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sent),
                                contentDescription = "Gửi",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (sentText.text.isNotEmpty()) {
                                            chatViewModel.sendMessage(
                                                chatId = chatId.toString(),
                                                senderId = senderId,
                                                messageContent = sentText.text
                                            )
                                            sentText = TextFieldValue("")
                                        }
                                    }
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xffdfdfdf),
                        shape = RoundedCornerShape(20.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun generateChatId(userId1: String, userId2: String): String {
    return if (userId1 < userId2) {
        "${userId1}_$userId2"
    } else {
        "${userId2}_$userId1"
    }
}
