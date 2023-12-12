package com.example.chatapplication.data.repository

import android.net.Uri
import android.util.Log
import com.example.chatapplication.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    private lateinit var storageRef: StorageReference

    fun saveUserInfo(user: User, uid: String, imageUri: Uri) {
        firestore.collection("User").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("@@@", "saveUserInfo: User Information saved !!!")
                uploadPic(uid, imageUri)
            }
            .addOnFailureListener {
                Log.d("@@@", "saveUserInfo: Failed: Something went wrong !!!")
            }
    }

    private fun uploadPic(uid: String, imageUri: Uri) {
        storageRef = storage.reference

        val imageRef = storageRef.child("image/$uid.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                Log.d("@@@", "saveProfilePic: Profile pic saved !!!")
            }
            .addOnFailureListener {
                Log.d("@@@", "saveProfilePic: Upload failed !!!")
            }
    }
}