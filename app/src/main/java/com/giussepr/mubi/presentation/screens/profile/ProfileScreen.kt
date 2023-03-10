/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.giussepr.mubi.R
import com.giussepr.mubi.domain.model.FavoriteTvShow
import com.giussepr.mubi.presentation.navigation.AppScreens
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiRatingBar
import com.giussepr.mubi.presentation.widgets.MubiTopAppBar
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun ProfileScreenPreview() {
  ProfileScreen(rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Background), topBar = {
    MubiTopAppBar(
      navController = navController,
      title = stringResource(id = R.string.profile),
      onSearchClicked = {},
      onProfileClicked = {})
  }) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Profile photo
      item {
        Box(
          modifier = Modifier
            .padding(top = 24.dp)
            .size(142.dp)
            .border(16.dp, color = Purple.copy(alpha = 0.1f), shape = CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Image(
            modifier = Modifier
              .clip(CircleShape)
              .size(100.dp),
            painter = painterResource(id = R.drawable.giussep_photo_profile),
            contentDescription = stringResource(
              id = R.string.profile_photo
            )
          )
        }
      }

      // User name
      item {
        Text(
          text = stringResource(id = R.string.giussep_ricardo),
          style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
          color = MaterialTheme.colors.onSurface,
          modifier = Modifier.padding(top = 12.dp)
        )
      }

      // Username
      item {
        Text(
          text = stringResource(id = R.string.giussep_ricardo_username),
          style = MaterialTheme.typography.caption,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        )
      }

      // Favorite tv shows
      item {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = stringResource(id = R.string.favorite_tv_shows),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp)
          )
          Spacer(Modifier.size(16.dp))
          when (val state = viewModel.uiState.collectAsState().value) {
            is ProfileViewModel.ProfileUiState.Loading -> {
              Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
              }
            }
            is ProfileViewModel.ProfileUiState.Success -> {
              if (state.favoriteTvShows.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                  Text(
                    text = stringResource(id = R.string.no_favorite_tv_shows),
                    style = MaterialTheme.typography.subtitle1,
                    color = SubtleText,
                  )
                }
              } else {
                LazyRow(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                  contentPadding = PaddingValues(end = 16.dp),
                  horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  items(state.favoriteTvShows) { favoriteTvShow ->
                    FavoriteTvShowListItem(tvShow = favoriteTvShow, onTvShowItemClicked = {
                      viewModel.onTvShowItemClicked(favoriteTvShow)
                    })
                  }
                }
              }
            }
            is ProfileViewModel.ProfileUiState.Error -> {
              Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                  text = state.message,
                  style = MaterialTheme.typography.subtitle1,
                  color = Red,
                )
              }
            }
          }
          Spacer(Modifier.size(16.dp))
        }
      }
    }

    LaunchedEffect(key1 = Unit) {
      viewModel.navigateToTvShowDetails.collectLatest { value ->
        if (value.isNotEmpty()) {
          viewModel.navigateToTvShowDetailsHandled()
          navController.navigate(AppScreens.TvShowDetail.withArg(value))
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTvShowListItem(tvShow: FavoriteTvShow, onTvShowItemClicked: () -> Unit) {
  Card(
    modifier = Modifier
      .width(180.dp)
      .height(240.dp),
    elevation = 4.dp,
    shape = MaterialTheme.shapes.large,
    onClick = { onTvShowItemClicked() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      SubcomposeAsyncImage(
        model = tvShow.imageUrl,
        contentDescription = tvShow.name,
        loading = {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
          }
        },
        error = {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
              modifier = Modifier.size(64.dp),
              painter = painterResource(id = R.drawable.no_image_available),
              contentDescription = stringResource(
                id = R.string.no_image_available,
              )
            )
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(160.dp),
        contentScale = ContentScale.Crop,
      )
      // Tv Show Title
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 12.dp, end = 12.dp, top = 16.dp),
        text = tvShow.name,
        style = MaterialTheme.typography.subtitle2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSurface
      )
      // Tv Show Rating
      MubiRatingBar(
        voteAverage = tvShow.voteAverage,
        modifier = Modifier.padding(start = 12.dp, bottom = 16.dp)
      )
    }
  }
}
