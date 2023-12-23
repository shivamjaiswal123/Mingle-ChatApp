package com.example.chatapplication.ui.screens


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.R
import com.example.chatapplication.data.model.User
import com.example.chatapplication.databinding.FragmentProfileBinding
import com.example.chatapplication.viewmodel.StorageViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var imageUri: Uri
    @Inject lateinit var auth: FirebaseAuth
    lateinit var viewModel: StorageViewModel

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it!!
        binding.imgProfile.setImageURI(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[StorageViewModel::class.java]

        //choose image from gallery
        binding.imgProfile.setOnClickListener {
            contract.launch("image/*")
        }

        val uid = auth.uid!!

        binding.btnSetupProfile.setOnClickListener {
            val name = binding.etName.text.toString()
            val bio = binding.etBio.text.toString()
            val user = User(name, bio)

            viewModel.saveUserInfo(user, uid, imageUri)
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            when(it){
                "Success" -> {
                    val intent = Intent(this@ProfileFragment.requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                "Failure" -> {
                    Toast.makeText(requireContext(), "Something went wrong !!!" , Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }
}