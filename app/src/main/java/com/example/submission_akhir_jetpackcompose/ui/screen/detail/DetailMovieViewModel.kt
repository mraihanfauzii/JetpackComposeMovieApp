package com.example.submission_akhir_jetpackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_akhir_jetpackcompose.data.MovieRepository
import com.example.submission_akhir_jetpackcompose.model.MovieDetail
import com.example.submission_akhir_jetpackcompose.model.OrderTicket
import com.example.submission_akhir_jetpackcompose.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState<OrderTicket>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<OrderTicket>>
        get() = _uiState

    fun getMovieById(movieID: Long) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            _uiState.value = UIState.Success(repository.getOrderTicketById(movieID))
        }
    }

    fun addToCart(movie: MovieDetail, count: Int) {
        viewModelScope.launch {
            repository.updateOrderTicket(movie.id, count)
        }
    }
}