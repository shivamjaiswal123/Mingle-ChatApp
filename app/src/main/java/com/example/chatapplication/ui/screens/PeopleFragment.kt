package com.example.chatapplication.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.databinding.FragmentPeopleBinding
import com.example.chatapplication.ui.adapter.UserAdapter
import com.example.chatapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentPeopleBinding
    private lateinit var userAdapter: UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        setupRecyclerView()

        viewModel.userList.observe(viewLifecycleOwner, Observer {
            userAdapter.differ.submitList(it)
        })

        //fetch users from firebase
        viewModel.fetchUsers()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter{
            Toast.makeText(requireContext(), "User clicked", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }
}