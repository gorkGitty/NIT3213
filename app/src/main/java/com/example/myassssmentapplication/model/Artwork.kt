package com.example.myassssmentapplication.model

import java.io.Serializable

data class Artwork(
    val artworkTitle: String,
    val artist: String,
    val medium: String,
    val year: Int,
    val description: String
) : Serializable 