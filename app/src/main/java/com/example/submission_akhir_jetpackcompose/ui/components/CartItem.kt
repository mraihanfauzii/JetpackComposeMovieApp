package com.example.submission_akhir_jetpackcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submission_akhir_jetpackcompose.R
import com.example.submission_akhir_jetpackcompose.ui.theme.Shapes
import com.example.submission_akhir_jetpackcompose.ui.theme.Submission_Akhir_JetpackComposeTheme
import coil.compose.AsyncImage

@Composable
fun CartItem(
    movieId: Long,
    photoUrl: String,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_point,
                    totalPrice
                ),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        ProductCounter(
            orderId = movieId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(movieId, count + 1) },
            onProductDecreased = { onProductCountChanged(movieId, count -1 )},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    Submission_Akhir_JetpackComposeTheme {
        CartItem(
            movieId = 1,
            photoUrl = "https://raw.githubusercontent.com/mraihanfauzii/JetpackComposeMovieApp/main/Movie%20Poster/Avengers%20Infinity%20War.jpg",
            title = "Avengers : Infinity War",
            totalPrice = 40000,
            count = 0,
            onProductCountChanged = { movieId, count -> }
        )
    }
}