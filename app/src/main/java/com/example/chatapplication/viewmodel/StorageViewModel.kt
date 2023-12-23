package com.example.chatapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.User
import com.example.chatapplication.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(private val repository: StorageRepository): ViewModel() {
    private var _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status
    fun saveUserInfo(user: User, uid: String, imageUri: Uri){
        viewModelScope.launch {
            repository.saveUserInfo(user, uid, imageUri).collectLatest {
                _status.value = it
            }
        }
    }
}