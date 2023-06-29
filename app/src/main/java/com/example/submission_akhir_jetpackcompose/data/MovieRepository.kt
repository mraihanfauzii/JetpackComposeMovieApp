package com.example.submission_akhir_jetpackcompose.data

import com.example.submission_akhir_jetpackcompose.model.MovieDataSource
import com.example.submission_akhir_jetpackcompose.model.MovieDetail
import com.example.submission_akhir_jetpackcompose.model.OrderTicket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository {

    private val orderTickets = mutableListOf<OrderTicket>()

    init {
        if (orderTickets.isEmpty()) {
            MovieDataSource.movieData.forEach {
                orderTickets.add(OrderTicket(it,0))
            }
        }
    }

    fun getAllTickets(): Flow<List<OrderTicket>> {
        return flowOf(orderTickets)
    }

    fun getOrderTicketById(movieId: Long): OrderTicket {
        return orderTickets.first {
            it.movie.id == movieId
        }
    }

    fun updateOrderTicket(MovieId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderTickets.indexOfFirst { it.movie.id == MovieId }
        val result = if (index >= 0) {
            val orderTicket = orderTickets[index]
            orderTickets[index] = orderTicket.copy(movie =  orderTicket.movie, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderTicket(): Flow<List<OrderTicket>> {
        return getAllTickets()
            .map { orderTickets ->
                orderTickets.filter { orderTicket ->
                    orderTicket.count != 0
                }
            }
    }

    fun getMovies(): List<MovieDetail> {
        return MovieDataSource.movieData
    }

    fun searchMovies(query: String): List<MovieDetail> {
        return MovieDataSource.movieData.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository = instance ?: synchronized(this) {
            MovieRepository().apply {
                instance = this
            }
        }
    }
}