package com.example.chatapplication.ui.screens

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.chatapplication.data.model.User
import com.example.chatapplication.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var imageUri: Uri
    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var firestore: FirebaseFirestore
    @Inject lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it!!
        binding.imgProfile.setImageURI(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        //choose image from gallery
        binding.imgProfile.setOnClickListener {
            contract.launch("image/*")
        }

        storageRef = storage.reference
        val uid = auth.uid!!

        binding.btnSetupProfile.setOnClickListener {
            val name = binding.etName.text.toString()
            val bio = binding.etBio.text.toString()
            val user = User(name, bio)

            saveUserInfo(user, uid)
        }


        return binding.root
    }

    private fun saveUserInfo(user: User, uid: String) {
        firestore.collection("User").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("@@@", "saveUserInfo: User Information saved !!!")
                uploadPic(uid)
            }
            .addOnFailureListener {
                Log.d("@@@", "saveUserInfo: Failed: Something went wrong !!!")
            }
    }

    private fun uploadPic(uid: String) {
        val imageRef = storageRef.child("image/$uid.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Photo Uploaded!!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Photo Upload Failed!!", Toast.LENGTH_SHORT).show()
            }
    }
}






//            firestore.collection("User").document(uid)
//                .update("name", user.name,
//                "bio", user.bio)
//                .addOnSuccessListener {
//                    Toast.makeText(requireContext(), "User Updated!!", Toast.LENGTH_SHORT).show()
//                    uploadPic(uid)
//                }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(), "User Failed!!", Toast.LENGTH_SHORT).show()
//                }