package com.makhalibagas.topstory.domain.repository

import com.makhalibagas.topstory.data.source.remote.Resource
import com.makhalibagas.topstory.domain.model.Comment
import com.makhalibagas.topstory.domain.model.TopStory
import kotlinx.coroutines.flow.Flow

interface ITopStoryRepository {

    fun getTopStory(): Flow<Resource<List<Int>>>

    fun getTopStoryDetail(id: Int): Flow<Resource<TopStory>>

    fun getTopStoryComment(id: Int): Flow<Resource<Comment>>

}