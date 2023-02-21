package com.makhalibagas.topstory.presentation.main

import androidx.lifecycle.ViewModel
import com.makhalibagas.topstory.data.source.local.SharedPref
import com.makhalibagas.topstory.data.source.remote.Resource
import com.makhalibagas.topstory.domain.model.TopStory
import com.makhalibagas.topstory.domain.usecase.TopStoryDetailUseCase
import com.makhalibagas.topstory.domain.usecase.TopStoryUseCase
import com.makhalibagas.topstory.state.UiStateWrapper
import com.makhalibagas.topstory.utils.collectLifecycleFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val topStoryUseCase: TopStoryUseCase,
    private val topStoryDetailUseCase: TopStoryDetailUseCase,
    private val pref: SharedPref
) : ViewModel() {

    private val _topStoryState = MutableSharedFlow<UiStateWrapper<List<Int>>>()
    val topStoryState = _topStoryState.asSharedFlow()

    private val _topStoryDetailState = MutableSharedFlow<UiStateWrapper<TopStory>>()
    val topStoryDetailState = _topStoryDetailState.asSharedFlow()

    val listTopStory: MutableList<TopStory> = ArrayList()

    private val _sizeListTopStory = MutableStateFlow(0)
    val sizeListTopStory = _sizeListTopStory.asStateFlow()

    fun setSizeList(size: Int) {
        _sizeListTopStory.value = size
    }

    fun getFav(): String = pref.getString(SharedPref.Key.FAV_CLICKED)

    fun getTopStory() {
        collectLifecycleFlow(topStoryUseCase()) { resource ->
            when (resource) {
                is Resource.Loading -> _topStoryState.emit(
                    UiStateWrapper.Loading(true)
                )
                is Resource.Success -> {
                    _topStoryState.emit(UiStateWrapper.Loading(false))
                    _topStoryState.emit(UiStateWrapper.Success(resource.data))
                }
                is Resource.Error -> {
                    _topStoryState.emit(UiStateWrapper.Loading(false))
                    _topStoryState.emit(UiStateWrapper.Error(resource.msg))
                }
            }
        }
    }

    fun getTopStoryDetail(id: Int) {
        collectLifecycleFlow(topStoryDetailUseCase.invoke(id)) { resource ->
            when (resource) {
                is Resource.Loading -> _topStoryDetailState.emit(
                    UiStateWrapper.Loading(true)
                )
                is Resource.Success -> {
                    _topStoryDetailState.emit(UiStateWrapper.Loading(false))
                    _topStoryDetailState.emit(UiStateWrapper.Success(resource.data))
                }
                is Resource.Error -> {
                    _topStoryDetailState.emit(UiStateWrapper.Loading(false))
                    _topStoryDetailState.emit(UiStateWrapper.Error(resource.msg))
                }
            }
        }
    }

}