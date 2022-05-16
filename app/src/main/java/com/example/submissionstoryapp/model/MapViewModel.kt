package com.example.submissionstoryapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.api.ApiConfig
import com.example.submissionstoryapp.data.ListItem
import com.example.submissionstoryapp.data.LocationResponse
import retrofit2.Call
import retrofit2.Response

class MapViewModel : ViewModel(){
    private val dataLoc = MutableLiveData<ArrayList<ListItem>>()
    private val location: LiveData<ArrayList<ListItem>> = dataLoc

    fun setLoc(token: String){
        val client = ApiConfig.getApiService().getLocation(token, 1)
        client.enqueue(object : retrofit2.Callback<LocationResponse>{
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>,
            ) {
                val listItem = ArrayList<ListItem>()
                if (response.isSuccessful){
                    if (response.body() != null){
                        val result = response.body()?.listStory
                        for (i in result!!){
                            val list = ListItem(
                                i.name,
                                i.lon,
                                i.id,
                                i.lat
                            )
                            listItem.add(list)
                        }
                        dataLoc.value = listItem
                    }
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getLoc(): LiveData<ArrayList<ListItem>>{
        return location
    }
}