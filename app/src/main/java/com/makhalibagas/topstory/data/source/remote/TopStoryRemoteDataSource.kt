package com.makhalibagas.topstory.data.source.remote

import android.content.Context
import com.makhalibagas.topstory.R
import com.makhalibagas.topstory.data.source.remote.network.ApiResponse
import com.makhalibagas.topstory.data.source.remote.network.TopStoryApiService
import com.makhalibagas.topstory.data.source.remote.response.CommentResponse
import com.makhalibagas.topstory.data.source.remote.response.TopStoryResponse
import com.makhalibagas.topstory.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoryRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: TopStoryApiService
) {

    fun getTopStory(): Flow<ApiResponse<List<Int>>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }
            try {
                val response = apiService.getTopStory()
                if (response != null) {
                    emit(ApiResponse.Success(response))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)


    fun getTopStoryDetail(id: Int): Flow<ApiResponse<TopStoryResponse>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }
            try {
                val response = apiService.getTopStoryDetail(id)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getTopStoryComment(id: Int): Flow<ApiResponse<CommentResponse>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }
            try {
                val response = apiService.getTopStoryComment(id)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}
