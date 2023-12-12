package com.example.chatapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.User
import com.example.chatapplication.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(private val repository: StorageRepository): ViewModel() {
    fun saveUserInfo(user: User, uid: String, imageUri: Uri){
        viewModelScope.launch {
            repository.saveUserInfo(user, uid, imageUri)
        }
    }
}