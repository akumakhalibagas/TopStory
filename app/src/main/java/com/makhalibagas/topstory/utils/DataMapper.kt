package com.makhalibagas.topstory.utils

import com.makhalibagas.topstory.data.source.remote.response.CommentResponse
import com.makhalibagas.topstory.data.source.remote.response.TopStoryResponse
import com.makhalibagas.topstory.domain.model.Comment
import com.makhalibagas.topstory.domain.model.TopStory

object DataMapper {

    fun TopStoryResponse.toTopStory() = TopStory(
        score, by, id, time, title, type, descendants, url ?: "", kids ?: emptyList()
    )

    fun CommentResponse.toComment() = Comment(
        parent, by.toString(), id, text.toString(), time, type
    )

}