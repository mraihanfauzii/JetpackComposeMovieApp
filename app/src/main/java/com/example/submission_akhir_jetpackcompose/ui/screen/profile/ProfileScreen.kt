package com.example.submission_akhir_jetpackcompose.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.submission_akhir_jetpackcompose.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    TopAppBar {
        Text(
            text = stringResource(R.string.menu_profile),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.creator),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.muhammadraihanfauzi),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(horizontal = 85.dp)
                    .size(250.dp)
                    .clip(CircleShape)
            )
            Box(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = stringResource(R.string.name),
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}