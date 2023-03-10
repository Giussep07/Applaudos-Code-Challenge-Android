/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource

import com.giussepr.mubi.data.model.SeasonDetailsDTO
import com.giussepr.mubi.data.model.TvShowDetailsDTO
import com.giussepr.mubi.data.model.TvShowResponseDTO
import retrofit2.Response

interface TvShowRemoteDataSource {
  suspend fun getTopRatedTvShows(page: Int): Response<TvShowResponseDTO>
  suspend fun getPopularTvShows(page: Int): Response<TvShowResponseDTO>
  suspend fun getOnTvShows(page: Int): Response<TvShowResponseDTO>
  suspend fun getAiringTodayTvShows(page: Int): Response<TvShowResponseDTO>
  suspend fun searchTvShowsByTerm(searchTerm: String, page: Int): Response<TvShowResponseDTO>
  suspend fun getTvShowDetails(tvShowId: Int): Response<TvShowDetailsDTO>
  suspend fun getTvShowSeasonDetails(tvShowId: Int, seasonNumber: Int): Response<SeasonDetailsDTO>
}
