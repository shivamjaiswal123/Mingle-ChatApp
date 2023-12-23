package com.example.chatapplication.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set bottom navigation bar
        binding.bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.chat_menu -> loadFragment(ChatFragment())
                R.id.people_menu -> loadFragment(PeopleFragment())
            }
            true
        }
        // Set default selection
        binding.bottomNav.selectedItemId = R.id.chat_menu
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}