package com.example.submissionstoryapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.api.ApiConfig
import com.example.submissionstoryapp.data.LoginResponse
import com.example.submissionstoryapp.data.LoginResult
import retrofit2.Call
import retrofit2.Response

class LoginViewModel: ViewModel() {
    val userLogin = MutableLiveData<LoginResponse>()
    val dataLogin = MutableLiveData<LoginResult>()

    fun setLogin(email: String, password: String){
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val result = response.body()
                    if (result != null){
                        userLogin.postValue(result!!)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun setDataLogin(email: String, password: String){
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val result = response.body()?.loginResult
                    if (result != null){
                        dataLogin.postValue(result!!)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getLogin(): LiveData<LoginResponse>{
        return userLogin
    }

    fun getDataLogin(): LiveData<LoginResult>{
        return dataLogin
    }
}