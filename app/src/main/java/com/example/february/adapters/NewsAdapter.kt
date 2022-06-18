package com.example.february.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.february.databinding.ItemArticlePreviewBinding
import com.example.february.models.Article

class NewsAdapter(private var clickListener : OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View, article: Article)
    }

    class ArticleViewHolder (private var binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root){
        val ivArticleImage = binding.ivArticleImage
        val tvSource = binding.tvSource
        val tvTitle = binding.tvTitle
        val tvDescription = binding.tvDescription
        val tvPublishedAt = binding.tvPublishedAt
        val root = binding.root
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        Glide.with(holder.ivArticleImage).load(article.urlToImage).into(holder.ivArticleImage)
        holder.tvSource.text = article.source?.name
        holder.tvTitle.text = article.title
        holder.tvDescription.text = article.description
        holder.tvPublishedAt.text = article.publishedAt
        holder.root.setOnClickListener {
            clickListener.onItemClick(position, it, article)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}