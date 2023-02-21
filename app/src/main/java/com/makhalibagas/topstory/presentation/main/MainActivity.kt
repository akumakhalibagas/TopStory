package com.makhalibagas.topstory.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.makhalibagas.topstory.databinding.ActivityMainBinding
import com.makhalibagas.topstory.presentation.detail.DetailActivity
import com.makhalibagas.topstory.state.UiStateWrapper
import com.makhalibagas.topstory.utils.TOP_STORY
import com.makhalibagas.topstory.utils.collectLifecycleFlow
import com.makhalibagas.topstory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: TopStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.getTopStory()
        collectLifecycleFlow(viewModel.topStoryState) { state ->
            when (state) {
                is UiStateWrapper.Loading ->
                    binding.pb.visibility = View.VISIBLE
                is UiStateWrapper.Success -> {
                    state.data.forEach {
                        viewModel.getTopStoryDetail(it)
                    }
                    viewModel.setSizeList(state.data.size)
                }
                is UiStateWrapper.Error -> Toast.makeText(this, state.msg, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        collectLifecycleFlow(viewModel.topStoryDetailState) { state ->
            when (state) {
                is UiStateWrapper.Loading -> {}
                is UiStateWrapper.Success -> {
                    viewModel.listTopStory.add(state.data)
                    if (viewModel.listTopStory.size == viewModel.sizeListTopStory.value) {
                        adapter.setData(viewModel.listTopStory)
                        binding.pb.visibility = View.GONE
                        populateFav(true)
                    } else {
                        binding.pb.visibility = View.VISIBLE
                        populateFav(false)
                    }
                }
                is UiStateWrapper.Error -> Toast.makeText(this, state.msg, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initListener() {
        adapter.onClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(TOP_STORY, it)
            startActivity(intent)
        }
    }

    private fun initView() {
        adapter = TopStoryAdapter()
        binding.rvTopStory.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.tvFavValue.text = viewModel.getFav()
    }

    private fun populateFav(isVisible: Boolean) {
        binding.apply {
            tvFav.isVisible = isVisible
            tvFavValue.isVisible = isVisible
        }
    }
}