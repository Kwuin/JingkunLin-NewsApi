package com.bignerdranch.android.photogallery.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Source(
    val id: String?,
    val name: String
)
