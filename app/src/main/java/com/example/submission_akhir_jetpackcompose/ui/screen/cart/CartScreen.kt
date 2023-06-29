package com.example.submission_akhir_jetpackcompose.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submission_akhir_jetpackcompose.R
import com.example.submission_akhir_jetpackcompose.di.Injection
import com.example.submission_akhir_jetpackcompose.ui.ViewModelFactory
import com.example.submission_akhir_jetpackcompose.ui.common.UIState
import com.example.submission_akhir_jetpackcompose.ui.components.CartItem
import com.example.submission_akhir_jetpackcompose.ui.components.OrderButton

import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked:  (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
        when (uiState) {
            is UIState.Loading -> {
                viewModel.getAddedOrderTicket()
            }
            is UIState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { movieId, count ->
                        viewModel.updateOrderTicket(movieId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UIState.Error -> {
                Text(
                    text = stringResource(R.string.error),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderTicket.count(),
        state.totalPrice
    )
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar {
            Text(
                text = stringResource(R.string.menu_cart),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderTicket, key = { it.movie.id }) { item ->
                CartItem(
                    movieId = item.movie.id,
                    photoUrl = item.movie.photoUrl,
                    title = item.movie.title,
                    totalPrice = item.movie.price,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider()
            }

        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderTicket.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}