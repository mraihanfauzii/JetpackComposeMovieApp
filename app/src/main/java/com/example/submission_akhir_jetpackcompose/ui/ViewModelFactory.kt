package com.example.submission_akhir_jetpackcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission_akhir_jetpackcompose.data.MovieRepository
import com.example.submission_akhir_jetpackcompose.ui.screen.cart.CartViewModel
import com.example.submission_akhir_jetpackcompose.ui.screen.detail.DetailMovieViewModel
import com.example.submission_akhir_jetpackcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            return DetailMovieViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}