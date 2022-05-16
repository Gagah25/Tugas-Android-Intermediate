package com.example.submissionstoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionstoryapp.adapter.LoadingStateAdapter
import com.example.submissionstoryapp.adapter.StoryListAdapter
import com.example.submissionstoryapp.data.UserPreference
import com.example.submissionstoryapp.databinding.ActivityListStoryBinding
import com.example.submissionstoryapp.model.ListStoriesViewModel
import com.example.submissionstoryapp.model.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var sharedPref: UserPreference
    private val listStoriesViewModel: ListStoriesViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this@ListStoryActivity, AddStoryActivity::class.java))
        }
        getData()
    }

    private fun getData(){
        val adapter = StoryListAdapter()

        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        listStoriesViewModel.story.observe(this, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu -> {
                Toast.makeText(this, "Berhasil Log Out", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    delay(1000L)
                    sharedPref.delete()
                    startActivity(Intent(this@ListStoryActivity, MainActivity::class.java))
                    finish()
                }
            }
            R.id.maps -> {
                startActivity(Intent(this@ListStoryActivity, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}