package com.example.chatapplication.ui.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentOtpBinding
import com.example.chatapplication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var verificationId: String
    lateinit var otpCode: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        //receive verificationId sent from LoginFragment
        setFragmentResultListener("rrr") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            verificationId = bundle.getString("bbb").toString()
            // Do something with the result.
        }

        binding.btnVerify.setOnClickListener {
            authViewModel.signInWithPhoneAuthCredential(verificationId, otpCode)
        }

        authViewModel.verificationStatus.observe(viewLifecycleOwner, Observer {
            if(it == "Success"){
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, ProfileFragment())
                    .commit()
            }else{
                Toast.makeText(requireContext(), "Something went wrong !!!", Toast.LENGTH_SHORT).show()
            }
        })

        //entered OTP
        binding.pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                otpCode = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return binding.root
    }
}