package com.bignerdranch.android.criminalintent.api

import retrofit2.http.GET
private const val API_KEY = "59eedea72cea4cce86806b9975cdbe39"

interface NewsApi {
    @GET(
        "v2/everything?q=bitcoin"+
        "&apiKey=$API_KEY"
    )
    suspend fun fetchNews(): NewsResponse
}
