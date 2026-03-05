package com.biathlonapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.News
import com.biathlonapp.data.model.NewsSource
import com.biathlonapp.databinding.ItemNewsBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val onNewsClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var news = listOf<News>()
    private val dateFormat = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("ru"))

    fun submitList(newList: List<News>) {
        news = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding, onNewsClick, dateFormat)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount() = news.size

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onNewsClick: (News) -> Unit,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.textTitle.text = news.title
            binding.textDate.text = dateFormat.format(news.date)

            // Обрезаем текст для превью (первые 200 символов)
            val previewText = news.content.take(200).trim() + if (news.content.length > 200) "..." else ""
            binding.textPreview.text = previewText

            // Загружаем изображение, если есть
            if (news.imageUrl != null) {
                binding.imageNews.visibility = android.view.View.VISIBLE
                Glide.with(binding.imageNews.context)
                    .load(news.imageUrl)
                    .centerCrop()
                    .into(binding.imageNews)
            } else {
                binding.imageNews.visibility = android.view.View.GONE
            }

            // Статистика
            binding.textLikes.text = formatCount(news.likesCount)
            binding.textComments.text = formatCount(news.commentsCount)
            binding.textReposts.text = formatCount(news.repostsCount)

            // Источник
            binding.textSource.text = when (news.source) {
                NewsSource.VK_SBR -> "СБР"
                NewsSource.VK_BIATHLON_ONLINE -> "Биатлон онлайн"
                NewsSource.VK_NORWAY_TEAM -> "Норвежская сборная"
                else -> "VK"
            }

            binding.cardNews.setOnClickListener {
                onNewsClick(news)
            }
        }

        private fun formatCount(count: Int): String {
            return when {
                count >= 1000 -> "${count / 1000}K"
                else -> count.toString()
            }
        }
    }
}