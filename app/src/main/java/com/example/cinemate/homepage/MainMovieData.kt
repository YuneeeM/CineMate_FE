package com.example.cinemate.homepage

import android.graphics.Bitmap

data class MainMovieData(
    val movieImage: String?,
    val movieTitle: String
)

data class MainMovieDataBitmap(
    val movieImage: Bitmap?,
    val movieTitle: String
)
