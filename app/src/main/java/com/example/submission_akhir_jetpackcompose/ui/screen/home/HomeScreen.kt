package com.example.submission_akhir_jetpackcompose.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submission_akhir_jetpackcompose.di.Injection
import com.example.submission_akhir_jetpackcompose.model.OrderTicket
import com.example.submission_akhir_jetpackcompose.ui.ViewModelFactory
import com.example.submission_akhir_jetpackcompose.ui.common.UIState
import com.example.submission_akhir_jetpackcompose.ui.components.MovieItem
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.submission_akhir_jetpackcompose.R
import com.example.submission_akhir_jetpackcompose.model.MovieDataSource
import com.example.submission_akhir_jetpackcompose.ui.components.CharacterHeader
import com.example.submission_akhir_jetpackcompose.ui.components.ScrollToTopButton
import com.example.submission_akhir_jetpackcompose.ui.components.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    val groupedMovies by viewModel.groupedMovies.collectAsState()
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
        when (uiState) {
            is UIState.Loading -> {
                viewModel.getAllMovies()
            }
            is UIState.Success -> {
                Box(modifier = modifier) {
                    val scope = rememberCoroutineScope()
                    val listState = rememberLazyListState()
                    val showButton: Boolean by remember {
                        derivedStateOf { listState.firstVisibleItemIndex > 0 }
                    }
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(bottom = 80.dp),
                        modifier = modifier.testTag("MovieList")
                    ) {
                        item {
                            SearchBar(
                                query = query,
                                onQueryChange = viewModel::search,
                                modifier = Modifier.background(MaterialTheme.colors.primary).testTag("SearchBar")
                            )
                        }
                        groupedMovies.forEach { (initial, movies) ->
                            stickyHeader {
                                CharacterHeader(initial)
                            }
                            items(movies) { data ->
                                MovieItem(
                                    photoUrl = data.photoUrl,
                                    title = data.title,
                                    price = data.price,
                                    modifier = Modifier.clickable {
                                            navigateToDetail(data.id)
                                        }
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = showButton,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically(),
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .align(Alignment.BottomCenter)
                            .testTag("ScrollToTop")
                    ) {
                        ScrollToTopButton(
                            onClick = {
                                scope.launch {
                                    listState.scrollToItem(index = 0)
                                }
                            }
                        )
                    }
                }
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
fun HomeContent(
    orderTicket: List<OrderTicket>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("MovieList")
    ) {
        items(orderTicket) { data ->
            MovieItem(
                photoUrl = data.movie.photoUrl,
                title = data.movie.title,
                price = data.movie.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.movie.id)
                }
            )
        }
    }
}
