package com.example.android.qstack.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.qstack.databinding.ItemSearchBinding
import com.example.android.qstack.model.Questions
import com.example.android.qstack.ui.question.generalQuestions.WebViewClickListener

class SearchAdapter(private val listener: WebViewClickListener):
    ListAdapter<Questions, SearchAdapter.SearchViewHolder>(SearchViewHolder.diff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onQuestionClick(getItem(position))
        }
    }

    class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Questions) {
            binding.searchTitle.text = question.title
//            binding.imageView3.bindImageToView(question.owner.profileImage)
        }
        private fun ImageView.bindImageToView(imageLink: String?){
            Glide.with(this).load(imageLink).into(this)
        }

        companion object{
            fun from(parent: ViewGroup): SearchViewHolder{
                return SearchViewHolder(ItemSearchBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
            val diff = object : DiffUtil.ItemCallback<Questions>() {
                override fun areItemsTheSame(oldItem: Questions, newItem: Questions): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: Questions, newItem: Questions): Boolean {
                    return oldItem.questionId == newItem.questionId
                }

            }
        }
    }
}