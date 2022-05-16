package com.example.submissionstoryapp.api

import com.example.submissionstoryapp.data.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiConfig {

    private const val baseUrl = "https://story-api.dicoding.dev/v1/"

    fun getApiService(): ApiInterface {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

}

interface ApiInterface{
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") name: String,
        @Field("password") password: String
    ): Call<LoginResponse>
    
    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesResponseItem

    @GET("stories")
    fun getLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): Call<LocationResponse>

    @Multipart
    @POST("stories")
    fun uploadImg(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<FileUploadResponse>
}