package com.example.submissionstoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.submissionstoryapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username =intent.getStringExtra(EXTRA_USER)
        val id = intent.getStringExtra(EXTRA_ID)
        val photo = intent.getStringExtra(EXTRA_PHOTO)

        with(binding){
            detailNama.text = username
            deskripsi.text = id
            Glide.with(this@DetailStoryActivity).load(photo).circleCrop().into(imageView)
        }
    }

    companion object{
        const val EXTRA_USER = "Extra_User"
        const val EXTRA_ID = "Extra_ID"
        const val EXTRA_PHOTO = "Extra_Photo"
    }
}