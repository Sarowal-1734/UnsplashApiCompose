package com.example.unsplashapicompose.data

data class PaginationResponse<T>(
    var count: Int = 0,
    var page: Int = 0,
    var pageSize: Int = 0,
    var results: ArrayList<T> = ArrayList()
)