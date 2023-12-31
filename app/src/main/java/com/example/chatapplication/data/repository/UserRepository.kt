package com.example.chatapplication.data.repository

import android.util.Log
import com.example.chatapplication.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    fun fetchUsers() = callbackFlow {
        firestore.collection("User").get()
            .addOnSuccessListener {
                //commonly used when you are iterating over the result of a query
                val user = it.documents.map { it.toObject(User::class.java)!! }
                trySend(user)
            }
            .addOnFailureListener {
                Log.d("@@@", "Error in fetching user list: $it")
            }
        awaitClose()
    }
}