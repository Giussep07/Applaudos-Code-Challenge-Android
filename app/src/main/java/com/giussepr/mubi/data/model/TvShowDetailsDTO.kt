/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.giussepr.mubi.domain.model.TvShowDetail

data class TvShowDetailsDTO(
  val id: Int,
  val seasons: List<SeasonDTO>
) {
  fun toDomainTvShowDetail() = TvShowDetail(
    id = id,
    seasons = seasons.map { it.toDomainSeason() }
  )
}
