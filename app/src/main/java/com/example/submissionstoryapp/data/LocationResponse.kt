package com.example.submissionstoryapp.data

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("listStory")
    val listStory: ArrayList<ListItem>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ListItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)

