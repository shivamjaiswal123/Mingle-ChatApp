package com.example.chatapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.User
import com.example.chatapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList
    fun fetchUsers() {
        viewModelScope.launch {
            repository.fetchUsers().collectLatest {
                _userList.value = it
            }
        }
    }
}