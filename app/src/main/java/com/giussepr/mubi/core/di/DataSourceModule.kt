/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.data.api.TmdbApi
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.airingtoday.AiringTodayTvShowDao
import com.giussepr.mubi.data.database.dao.airingtoday.AiringTodayTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowRemoteKeyDao
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSourceImpl
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSource
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSourceImpl
import com.giussepr.mubi.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

  @Provides
  fun provideTvShowRemoteDataSource(
    tmdbApi: TmdbApi,
    @Named(Constants.API_KEY) apiKey: String
  ): TvShowRemoteDataSource {
    return TvShowRemoteDataSourceImpl(tmdbApi, apiKey)
  }

  @Provides
  fun provideTvShowLocalDataSource(
    favoriteTvShowDao: FavoriteTvShowDao,
    topRatedTvShowDao: TopRatedTvShowDao,
    topRatedTvShowRemoteKeyDao: TopRatedTvShowRemoteKeyDao,
    popularTvShowDao: PopularTvShowDao,
    popularTvShowRemoteKeyDao: PopularTvShowRemoteKeyDao,
    onTvShowDao: OnTvShowDao,
    onTvShowRemoteKeyDao: OnTvShowRemoteKeyDao,
    airingTodayTvShowDao: AiringTodayTvShowDao,
    airingTodayTvShowRemoteKeyDao: AiringTodayTvShowRemoteKeyDao
  ): TvShowLocalDataSource {
    return TvShowLocalDataSourceImpl(
      favoriteTvShowDao,
      topRatedTvShowDao,
      topRatedTvShowRemoteKeyDao,
      popularTvShowDao,
      popularTvShowRemoteKeyDao,
      onTvShowDao,
      onTvShowRemoteKeyDao,
      airingTodayTvShowDao,
      airingTodayTvShowRemoteKeyDao
    )
  }
}
