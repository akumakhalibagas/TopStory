package com.makhalibagas.topstory.presentation.detail

import androidx.lifecycle.ViewModel
import com.makhalibagas.topstory.data.source.local.SharedPref
import com.makhalibagas.topstory.data.source.remote.Resource
import com.makhalibagas.topstory.domain.model.Comment
import com.makhalibagas.topstory.domain.usecase.TopStoryCommentUseCase
import com.makhalibagas.topstory.state.UiStateWrapper
import com.makhalibagas.topstory.utils.collectLifecycleFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val topStoryUseCase: TopStoryCommentUseCase,
    private val pref: SharedPref
) : ViewModel() {

    private val _topStoryCommentState = MutableSharedFlow<UiStateWrapper<Comment>>()
    val topStoryCommentState = _topStoryCommentState.asSharedFlow()

    val listComment: MutableList<Comment> = ArrayList()

    private val _sizeListComment = MutableStateFlow(0)
    val sizeListComment = _sizeListComment.asStateFlow()

    fun setSizeList(size: Int) {
        _sizeListComment.value = size
    }

    fun setFav(data: String) {
        pref.put(SharedPref.Key.FAV_CLICKED, data)
    }

    fun getTopStoryComment(id: Int) {
        collectLifecycleFlow(topStoryUseCase.invoke(id)) { resource ->
            when (resource) {
                is Resource.Loading -> _topStoryCommentState.emit(
                    UiStateWrapper.Loading(true)
                )
                is Resource.Success -> {
                    _topStoryCommentState.emit(UiStateWrapper.Loading(false))
                    _topStoryCommentState.emit(UiStateWrapper.Success(resource.data))
                }
                is Resource.Error -> {
                    _topStoryCommentState.emit(UiStateWrapper.Loading(false))
                    _topStoryCommentState.emit(UiStateWrapper.Error(resource.msg))
                }
            }
        }
    }

}