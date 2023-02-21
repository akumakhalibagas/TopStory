package com.makhalibagas.topstory.data.source.remote.network

import com.makhalibagas.topstory.data.source.remote.response.CommentResponse
import com.makhalibagas.topstory.data.source.remote.response.TopStoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TopStoryApiService {

    @GET("topstories.json")
    suspend fun getTopStory(): List<Int>

    @GET("item/{idItem}.json")
    suspend fun getTopStoryDetail(
        @Path("idItem") idItem: Int
    ): TopStoryResponse

    @GET("item/{idItem}.json")
    suspend fun getTopStoryComment(
        @Path("idItem") idItem: Int
    ): CommentResponse
}