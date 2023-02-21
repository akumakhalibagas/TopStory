package com.makhalibagas.topstory.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopStory(
    val score: Int,
    val by: String,
    val id: Int,
    val time: Int,
    val title: String,
    val type: String,
    val descendants: Int,
    val url: String,
    val kids: List<Int>
) : Parcelable
