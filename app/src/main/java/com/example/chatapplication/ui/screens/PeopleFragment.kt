package com.example.chatapplication.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.databinding.FragmentPeopleBinding
import com.example.chatapplication.viewmodel.StorageViewModel
import com.example.chatapplication.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PeopleFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentPeopleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        //fetch users from firebase
        viewModel.fetchUsers()

        // Inflate the layout for this fragment
        return binding.root
    }
}