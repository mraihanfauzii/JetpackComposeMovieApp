package com.example.submission_akhir_jetpackcompose.ui.screen.cart

import com.example.submission_akhir_jetpackcompose.model.OrderTicket

data class CartState(
    val orderTicket: List<OrderTicket>,
    val totalPrice: Int
)