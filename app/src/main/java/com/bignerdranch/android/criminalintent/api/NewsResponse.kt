package com.bignerdranch.android.photogallery.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)



//data class Source(
//    val id: String?,
//    val name: String
//)
