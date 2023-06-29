package com.example.submission_akhir_jetpackcompose.model

data class MovieDetail(
    val id: Long,
    val title: String,
    val photoUrl: String,
    val price: Int,
    val cinema: String,
    val synopsis: String
)