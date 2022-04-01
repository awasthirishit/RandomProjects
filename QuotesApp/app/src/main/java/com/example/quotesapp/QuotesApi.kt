package com.example.quotesapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {

    @GET("/Quotes/random")
    suspend fun getQuotes(): Response<Quotes>
}