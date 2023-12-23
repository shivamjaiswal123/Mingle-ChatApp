package com.example.chatapplication.data.repository

import android.net.Uri
import android.util.Log
import com.example.chatapplication.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    private lateinit var storageRef: StorageReference

    fun saveUserInfo(user: User, uid: String, imageUri: Uri) = callbackFlow {
        firestore.collection("User").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("@@@", "saveUserInfo: User Information saved !!!")
                //upload profile picture in firebase storage
                uploadPic(uid, imageUri)
                trySend("Success")
            }
            .addOnFailureListener {
                Log.d("@@@", "saveUserInfo: Failed: $it")
            }
        awaitClose()
    }

    private fun uploadPic(uid: String, imageUri: Uri) {
        storageRef = storage.reference

        val imageRef = storageRef.child("image/$uid.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                //download image url for later use
                imageRef.downloadUrl
                    .addOnSuccessListener {
                        val imgUrl = it.toString()
                        Log.d("@@@", "imgUri: $imgUrl")
                        updateImgUrl(uid, imgUrl)
                    }
                    .addOnFailureListener {
                        Log.d("@@@", "imgUrl download failed: $it")
                    }
                Log.d("@@@", "saveProfilePic: Profile pic saved !!!")
            }
            .addOnFailureListener {
                Log.d("@@@", "saveProfilePic: Upload failed: $it")
            }
    }

    private fun updateImgUrl(uid: String, imgUrl: String) {
        firestore.collection("User").document(uid)
            .update("profilePicUrl", imgUrl)
            .addOnSuccessListener {
                Log.d("@@@", "Download URL updated successfully!")
            }
            .addOnFailureListener {
                Log.d("@@@", "Download URL updated failed!: ${it.message}")
            }
    }
}