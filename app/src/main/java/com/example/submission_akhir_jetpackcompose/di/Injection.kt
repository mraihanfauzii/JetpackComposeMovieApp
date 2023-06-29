package com.example.submission_akhir_jetpackcompose.di

import com.example.submission_akhir_jetpackcompose.data.MovieRepository

object Injection {
    fun provideRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}