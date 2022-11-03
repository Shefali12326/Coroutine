package com.example.coroutinedemo.network

import retrofit2.http.GET
import com.example.coroutinedemo.model.QuoteList
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

interface ApiInterface {

    @GET("/quotes")
    suspend fun getQuotes() : Response<JsonElement>

}

//https://blog.mindorks.com/using-retrofit-with-kotlin-coroutines-in-android