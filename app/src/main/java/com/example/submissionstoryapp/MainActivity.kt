package com.example.submissionstoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submissionstoryapp.data.UserPreference
import com.example.submissionstoryapp.data.UserPreference.Companion.PREF_IS_LOGIN
import com.example.submissionstoryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(PREF_IS_LOGIN) == true){
            startActivity(Intent(this, ListStoryActivity::class.java))
            finish()
        }
    }
}