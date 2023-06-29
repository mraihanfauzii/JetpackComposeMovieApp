package com.example.submission_akhir_jetpackcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_akhir_jetpackcompose.data.MovieRepository
import com.example.submission_akhir_jetpackcompose.model.MovieDetail
import com.example.submission_akhir_jetpackcompose.model.OrderTicket
import com.example.submission_akhir_jetpackcompose.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState<List<OrderTicket>>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<List<OrderTicket>>>
        get() = _uiState

    private val _groupedMovie = MutableStateFlow(
        repository.getMovies()
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    )
    val groupedMovies: StateFlow<Map<Char, List<MovieDetail>>> get() = _groupedMovie

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedMovie.value = repository.searchMovies(_query.value)
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllTickets()
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }
                .collect { orderMovies ->
                    _uiState.value = UIState.Success(orderMovies)
                }
        }
    }

}