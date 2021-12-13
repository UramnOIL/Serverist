package com.uramnoil.serverist.infrastructure

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.type.OrderBy as ApolloOrderBy
import com.uramnoil.serverist.type.Sort as ApolloSort

fun Sort.toApollo(): ApolloSort {
    return when (this) {
        Sort.Asc -> {
            ApolloSort.ASC
        }
        Sort.Desc -> {
            ApolloSort.DESC
        }
    }
}

fun OrderBy.toApollo(): com.uramnoil.serverist.type.OrderBy {
    return when (this) {
        OrderBy.CreatedAt -> {
            ApolloOrderBy.CREATEDAT
        }
    }
}