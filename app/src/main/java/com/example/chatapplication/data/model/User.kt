package com.example.chatapplication.data.model

data class User(
    val name: String,
    val bio: String,
    var profilePicUrl: String? = ""
){
    constructor() : this("", "", "")
}
