package com.example.submissionstoryapp.data

import android.content.Context
import android.content.SharedPreferences

internal class UserPreference(context: Context) {

    private val sharedPref: SharedPreferences
    val putData: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        putData = sharedPref.edit()
    }

    fun setPref(key: String, value: String?){
        putData.putString(key, value).apply()
    }

    fun setPref(key: String, value: Boolean){
        putData.putBoolean(key, value).apply()
    }

    fun saveToken(token: String){
        putData.putString(PREF_TOKEN, token)
        putData.apply()
    }

    fun fetchToken(): String?{
        return sharedPref.getString(PREF_TOKEN, null)
    }

    fun getString(key: String): String?{
        return sharedPref.getString(key, null)
    }

    fun getBoolean(key: String): Boolean?{
        return sharedPref.getBoolean(key, false)
    }

    fun delete(){
        putData.clear().apply()
    }

    companion object {
        const val PREFS_NAME = "user_pref"
        const val PREF_IS_LOGIN = "Pref_Is_Login"
        const val PREF_USERNAME = "Pref_Username"
        const val PREF_TOKEN = "Pref_Token"
    }
}