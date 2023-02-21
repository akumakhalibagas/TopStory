package com.makhalibagas.topstory.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(

    @field:SerializedName("parent")
    val parent: Int,

    @field:SerializedName("by")
    val by: String? = "",

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("text")
    val text: String? = "",

    @field:SerializedName("time")
    val time: Int,

    @field:SerializedName("type")
    val type: String
)
