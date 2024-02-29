package com.bignerdranch.android.criminalintent.api
import com.squareup.moshi.JsonClass

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)