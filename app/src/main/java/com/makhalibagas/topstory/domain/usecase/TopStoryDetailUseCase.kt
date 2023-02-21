package com.makhalibagas.topstory.domain.usecase

import com.makhalibagas.topstory.data.source.remote.Resource
import com.makhalibagas.topstory.domain.model.TopStory
import com.makhalibagas.topstory.domain.repository.ITopStoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopStoryDetailUseCase @Inject constructor(
    private val repository: ITopStoryRepository
) {
    operator fun invoke(id: Int): Flow<Resource<TopStory>> = repository.getTopStoryDetail(id)
}