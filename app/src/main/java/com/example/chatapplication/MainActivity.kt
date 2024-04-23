package com.example.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapplication.ui.screens.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //load LoginFragment screen in Fragment Container
//        if(savedInstanceState == null){
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container_view, LoginFragment())
//                .commit()
//        }
    }
}