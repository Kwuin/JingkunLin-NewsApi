package com.bignerdranch.android.criminalintent.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Source(
    val id: String?,
    val name: String
)
