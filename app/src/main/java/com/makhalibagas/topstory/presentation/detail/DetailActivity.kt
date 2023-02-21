package com.makhalibagas.topstory.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.makhalibagas.topstory.databinding.ActivityDetailBinding
import com.makhalibagas.topstory.domain.model.TopStory
import com.makhalibagas.topstory.state.UiStateWrapper
import com.makhalibagas.topstory.utils.TOP_STORY
import com.makhalibagas.topstory.utils.collectLifecycleFlow
import com.makhalibagas.topstory.utils.convertLongToDate
import com.makhalibagas.topstory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDetailBinding::inflate)
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    private fun initObserver() {
        val data = intent.getParcelableExtra<TopStory>(TOP_STORY)
        viewModel.setSizeList(data?.kids?.size ?: 0)
        data?.kids?.forEach {
            viewModel.getTopStoryComment(it)
        }

        collectLifecycleFlow(viewModel.topStoryCommentState) { state ->
            when (state) {
                is UiStateWrapper.Loading -> binding.pb.visibility = View.VISIBLE
                is UiStateWrapper.Success -> {
                    viewModel.listComment.add(state.data)
                    if (viewModel.listComment.size == viewModel.sizeListComment.value) {
                        adapter.setData(viewModel.listComment)
                        binding.pb.visibility = View.GONE
                    } else {
                        binding.pb.visibility = View.VISIBLE
                    }
                }
                is UiStateWrapper.Error -> Toast.makeText(this, state.msg, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initListener() {
        val data = intent.getParcelableExtra<TopStory>(TOP_STORY)
        binding.btFav.setOnClickListener {
            viewModel.setFav(data?.title.toString())
            Toast.makeText(this@DetailActivity, "Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        val data = intent.getParcelableExtra<TopStory>(TOP_STORY)
        adapter = CommentAdapter()
        binding.apply {
            tvJudul.text = data?.title
            tvBy.text = data?.by
            tvDate.text = convertLongToDate(data?.time?.toLong() ?: 0)
            tvDeskripsi.text = data?.title
            rvComment.adapter = adapter
        }
    }
}