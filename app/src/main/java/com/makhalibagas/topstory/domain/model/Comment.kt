package com.makhalibagas.topstory.domain.model

data class Comment(
    val parent: Int,
    val by: String,
    val id: Int,
    val text: String,
    val time: Int,
    val type: String
)
