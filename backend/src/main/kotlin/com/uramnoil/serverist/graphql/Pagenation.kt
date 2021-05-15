package com.uramnoil.serverist.graphql

data class PageRequest(val first: Int?, val after: String?, val last: Int?, val before: Int?)

data class PageInfo(
    val endCursor: String,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val startCursor: String
)