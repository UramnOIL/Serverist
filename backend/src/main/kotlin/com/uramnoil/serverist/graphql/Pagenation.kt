package com.uramnoil.serverist.graphql

data class PageRequest(val limit: Int, val offset: Long)

data class PageInfo(val limit: Int, val offset: Long)