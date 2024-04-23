package com.example.chatapplication.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentLoginBinding
import com.example.chatapplication.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var authViewModel: AuthViewModel
    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnNext.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            authViewModel.sendOTP(phone, requireActivity())
        }

        //move to OTP screen when OTP is sent
        authViewModel.verificationId.observe(viewLifecycleOwner, Observer {
            //pass verificationId also to OTP fragment
            val action = LoginFragmentDirections.actionLoginFragmentToOtpFragment(it)
            findNavController().navigate(action)
        })

        //if user logged in already then move to home screen
        val user = auth.currentUser
        Log.d("@@@", "onCreateView: user: $user")
        if(user != null){
            val intent = Intent(this@LoginFragment.requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}