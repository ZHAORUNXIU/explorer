package com.crypted.explorer.common.repository

import org.springframework.data.domain.Sort

data class Paging(
    val page: Int,
    val size: Int,
    val direction: Sort.Direction,
    val property: String,
    val firstValue: Long
)