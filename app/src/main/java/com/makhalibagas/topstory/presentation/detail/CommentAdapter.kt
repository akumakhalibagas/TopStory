package com.makhalibagas.topstory.presentation.detail

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.topstory.databinding.ItemCommentBinding
import com.makhalibagas.topstory.domain.model.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private val listData = ArrayList<Comment>()

    fun setData(newListData: List<Comment>) {
        val previousContentSize = listData.size
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeRemoved(0, previousContentSize)
        notifyItemRangeInserted(0, newListData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Comment) {
            initView(data)
        }

        private fun initView(data: Comment) {
            binding.apply {
                tvComment.text = Html.fromHtml(data.text)
            }
        }
    }
}