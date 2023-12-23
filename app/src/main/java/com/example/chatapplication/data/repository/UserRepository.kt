package com.example.chatapplication.data.repository

import android.util.Log
import com.example.chatapplication.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    fun fetchUsers() {
        val userList = mutableListOf<User>()
        firestore.collection("User").get()
            .addOnSuccessListener {
                for(document in it){
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }
                Log.d("@@@", "User List: $userList")
            }
            .addOnFailureListener {
                Log.d("@@@", "Error in fetching user list: $it")
            }
    }
}