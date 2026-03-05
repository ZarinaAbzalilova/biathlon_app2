package com.biathlonapp.ui.news

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.biathlonapp.data.model.News
import com.biathlonapp.data.model.NewsSource
import com.biathlonapp.databinding.ActivityNewsDetailBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var news: News
    private val dateFormat = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("ru"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем данные новости из Intent
        news = intent.getSerializableExtra("news") as News

        setupToolbar()
        displayNews()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Новость"
    }

    private fun displayNews() {
        // Заголовок и дата
        binding.textTitle.text = news.title
        binding.textDate.text = dateFormat.format(news.date)

        // Изображение (если есть)
        if (news.imageUrl != null) {
            binding.imageNews.visibility = android.view.View.VISIBLE
            Glide.with(this)
                .load(news.imageUrl)
                .centerCrop()
                .into(binding.imageNews)
        } else {
            binding.imageNews.visibility = android.view.View.GONE
        }

        // Текст новости (с поддержкой HTML, если есть)
        binding.textContent.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(news.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(news.content)
        }
        binding.textContent.movementMethod = LinkMovementMethod.getInstance()

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

        // Кнопка "Открыть в VK"
        binding.buttonOpenVk.setOnClickListener {
            openInVk()
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1000 -> "${count / 1000}K"
            else -> count.toString()
        }
    }

    private fun openInVk() {
        val vkUrl = "https://vk.com/wall${communities[news.source]}?w=wall${communities[news.source]}_${news.vkPostId}"
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(vkUrl))
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        // Нужно передать communities из репозитория или сделать отдельную map
        private val communities = mapOf(
            NewsSource.VK_SBR to "-636950"
        )
    }
}