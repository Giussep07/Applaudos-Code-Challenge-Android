/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

data class SeasonDetail(
  val seasonName: String,
  val episodes: List<Episode>
)
