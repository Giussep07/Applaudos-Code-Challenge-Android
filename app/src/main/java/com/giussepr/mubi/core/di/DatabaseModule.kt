/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import android.content.Context
import androidx.room.Room
import com.giussepr.mubi.data.database.MubiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

  @Provides
  @Singleton
  fun provideMubiDatabase(@ApplicationContext appContext: Context): MubiDatabase {
    return Room.databaseBuilder(
      appContext,
      MubiDatabase::class.java,
      "mubi_database"
    )
      .addMigrations(*MubiDatabase.MIGRATIONS)
      .build()
  }

  @Provides
  fun provideFavoriteTvShowDao(mubiDatabase: MubiDatabase) = mubiDatabase.favoriteTvShowDao()
}
