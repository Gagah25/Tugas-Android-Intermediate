package com.example.submissionstoryapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.api.ApiConfig
import com.example.submissionstoryapp.data.Register
import com.example.submissionstoryapp.data.SignupResponse
import retrofit2.Call
import retrofit2.Response

class SignupViewModel: ViewModel() {
    val userRegister = MutableLiveData<Register>()

    fun setRegister(name: String, email: String, pass: String){
        val client = ApiConfig.getApiService().postRegister(name, email, pass)
        client.enqueue(object : retrofit2.Callback<Register>{
            override fun onResponse(
                call: Call<Register>,
                response: Response<Register>
            ) {
                if (response.isSuccessful){
                    val result = response.body()
                    if (result != null){
                        userRegister.postValue(result!!)
                    }
                }
            }

            override fun onFailure(call: Call<Register>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getRegister(): LiveData<Register>{
        return userRegister
    }
}