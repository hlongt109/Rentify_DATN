package com.rentify.user.app.viewModel.UserViewmodel

import android.content.Context
import android.util.Log
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
    val sender: String = "",
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

    //lay danh sach user
    fun getListUser(onUsersLoaded: (List<chatUser>) -> Unit, loginViewModel: LoginViewModel) {
        val userRef = database.getReference("users")
        val currentUserId = loginViewModel.getUserData().userId
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<chatUser>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(chatUser::class.java)
                    user?.let {
                        Log.d("ChatViewModel", "Current User ID: $currentUserId")
                        if (it.id != currentUserId) {
                            users.add(it)
                        }
                    }
                }
                onUsersLoaded(users)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //gui tin nhan
    fun sendMessage(chatId: String, senderId: String, messageContent: String) {
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

    //tai tin nhan
    fun listenForMessages(chatId: String, onMessageReceived: (Message) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val messageRef = database.getReference("chats/$chatId/messages")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                message?.let {
                    GlobalScope.launch(Dispatchers.Main) {
                        onMessageReceived(it)
                    }
                }
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

            override fun onCancelled(error: DatabaseError) {
                Log.e("Chat", "Failed to listen for messages", error.toException())
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