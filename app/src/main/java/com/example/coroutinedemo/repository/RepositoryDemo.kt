package com.example.coroutinedemo.repository

import com.androidmvvmdatabindingrecyclerviewkotlin.network.RetrofitClient

class RepositoryDemo {
    suspend fun getProfileList() = RetrofitClient.service.getQuotes()


}