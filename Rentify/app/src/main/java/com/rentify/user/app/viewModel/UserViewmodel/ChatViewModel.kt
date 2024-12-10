package com.rentify.user.app.viewModel.UserViewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val content: String = "",
    val timestamp: Long = 0,
    val status: String = "sent"
)

data class chatUser(
    val id: String = "",
    val email: String = "",
    val isOnline: Boolean = false,
    val lastLogin: Long = 0,
    val name: String = ""
)

class ChatViewModel() : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val _chatList = MutableLiveData<MutableList<String>>()
    val chatList: LiveData<MutableList<String>> = _chatList
    //lay danh sach user

    fun getChatList(userId: String) {
        // Truy cập vào thư mục chats
        database.getReference("chats")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val chatUsers = mutableSetOf<String>() // Dùng Set để tránh trùng lặp
                    for (chatSnapshot in dataSnapshot.children) {
                        // Mỗi chatId là key của một cuộc trò chuyện
                        val chatId = chatSnapshot.key ?: continue

                        // Kiểm tra xem senderId có phải là userId không
                        // Kiểm tra tất cả các tin nhắn trong cuộc trò chuyện này
                        val messagesSnapshot = chatSnapshot.child("messages")
                        for (messageSnapshot in messagesSnapshot.children) {
                            val senderId = messageSnapshot.child("senderId").getValue(String::class.java)

                            if (senderId == userId) {
                                // Nếu senderId là userId, tìm người nhận
                                val participants = chatId.split("_") // Giả sử chatId có định dạng senderId_receiverId
                                val otherUserId = participants.first { it != userId }
                                chatUsers.add(otherUserId)
                            }
                        }
                    }

                    // Log danh sách ID người mà người dùng đã nhắn tin
                    Log.d("CheckList", "onDataChange: $chatUsers")

                    // Xử lý danh sách chat users (ID của người đã nhắn tin)
                    handleChatList(mutableListOf<String>().apply { addAll(chatUsers) })
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("ChatViewModel", "Error getting chat list: ${databaseError.message}")
                }
            })
    }


    private fun handleChatList(chatList: MutableList<String>) {
        // Ví dụ lưu danh sách chat IDs vào một biến LiveData
        _chatList.value = chatList
    }

    fun addChatRelation(senderId: String, receiverId: String): String {
        // Ví dụ logic tạo chatId
        return if (senderId < receiverId) "$senderId" + "_" + "$receiverId" else "$receiverId" + "_" + "$senderId"
    }

    //gui tin nhan
    fun sendMessage(
        chatId: String,
        senderId: String,
        messageContent: String) {
        val database = FirebaseDatabase.getInstance()
        val messageRef = database.getReference("chats/$chatId/messages")

        //tao tin nhan moi
        val messageId = messageRef.push().key ?: return
        val messageData = mapOf(
            "messageId" to UUID.randomUUID().toString(),
            "senderId" to senderId,
            "content" to messageContent,
            "timestamp" to System.currentTimeMillis()
        )

        messageRef.child(messageId).setValue(messageData)
            .addOnSuccessListener {
                Log.d("Chat", "Message sent successfully!")
            }
            .addOnFailureListener { exception ->
                Log.e("Chat", "Failed to send message", exception)
            }
    }
    fun listenForMessages(chatId: String, onMessageReceived: (Message) -> Unit) {
        database.getReference("chats/$chatId/messages")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(Message::class.java)
                    message?.let { onMessageReceived(it) }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                // Implement các callback khác nếu cần
                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatViewModel", "Error loading messages: ${error.message}")
                }
            })
    }


    class ChatViewModelFactory() :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChatViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}