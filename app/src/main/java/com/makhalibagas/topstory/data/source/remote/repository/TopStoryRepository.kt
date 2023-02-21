package com.makhalibagas.topstory.data.source.remote.repository

import com.makhalibagas.topstory.data.source.remote.Resource
import com.makhalibagas.topstory.data.source.remote.TopStoryRemoteDataSource
import com.makhalibagas.topstory.data.source.remote.network.ApiResponse
import com.makhalibagas.topstory.domain.model.Comment
import com.makhalibagas.topstory.domain.model.TopStory
import com.makhalibagas.topstory.domain.repository.ITopStoryRepository
import com.makhalibagas.topstory.utils.DataMapper.toComment
import com.makhalibagas.topstory.utils.DataMapper.toTopStory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoryRepository @Inject constructor(
    private val remoteDataSource: TopStoryRemoteDataSource
) : ITopStoryRepository {

    override fun getTopStory(): Flow<Resource<List<Int>>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getTopStory().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }

    override fun getTopStoryDetail(id: Int): Flow<Resource<TopStory>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getTopStoryDetail(id).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data.toTopStory()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }

    override fun getTopStoryComment(id: Int): Flow<Resource<Comment>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getTopStoryComment(id).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data.toComment()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }
}