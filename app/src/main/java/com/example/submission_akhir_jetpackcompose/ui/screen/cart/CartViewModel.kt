package com.example.submission_akhir_jetpackcompose.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_akhir_jetpackcompose.data.MovieRepository
import com.example.submission_akhir_jetpackcompose.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState<CartState>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<CartState>>
        get() = _uiState

    fun getAddedOrderTicket() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            repository.getAddedOrderTicket()
                .collect { orderTicket ->
                    val totalPrice =
                        orderTicket.sumOf { it.movie.price * it.count }
                    _uiState.value = UIState.Success(CartState(orderTicket, totalPrice))
                }
        }
    }

    fun updateOrderTicket(movieId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderTicket(movieId, count)
                .collect{ isUpdated ->
                    if (isUpdated) {
                        getAddedOrderTicket()
                    }
                }
        }
    }
}