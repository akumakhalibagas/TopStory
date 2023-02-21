package com.makhalibagas.topstory.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.topstory.databinding.ItemTopstoryBinding
import com.makhalibagas.topstory.domain.model.TopStory

class TopStoryAdapter : RecyclerView.Adapter<TopStoryAdapter.ViewHolder>() {
    private val listData = ArrayList<TopStory>()

    var onClick: ((TopStory) -> Unit)? = null

    fun setData(newListData: List<TopStory>) {
        val previousContentSize = listData.size
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeRemoved(0, previousContentSize)
        notifyItemRangeInserted(0, newListData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTopstoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(private val binding: ItemTopstoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TopStory) {
            initView(data)
            initListener(data)
        }

        private fun initView(data: TopStory) {
            binding.apply {
                tvJudul.text = data.title
                tvScore.text = "Score ${data.score}"
                tvJumlahKomentar.text = "${data.kids.size} Komentar"
            }
        }

        private fun initListener(data: TopStory) {
            binding.root.setOnClickListener {
                onClick?.invoke(data)
            }
        }
    }
}