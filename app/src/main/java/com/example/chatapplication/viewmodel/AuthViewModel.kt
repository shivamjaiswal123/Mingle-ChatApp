package com.example.chatapplication.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    private var _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String> get() = _verificationId

    private var _verificationStatus = MutableLiveData<String>()
    val verificationStatus: LiveData<String> get() = _verificationStatus

    fun sendOTP(phoneNumber:String, activity: Activity){
        viewModelScope.launch {
            repository.sendOTP(phoneNumber, activity).collectLatest {
                _verificationId.value = it
            }
        }
    }

    fun signInWithPhoneAuthCredential(verificationId: String, otpCode: String){
        viewModelScope.launch {
            repository.signInWithPhoneAuthCredential(verificationId, otpCode).collectLatest {
                _verificationStatus.value = it
            }
        }
    }
}