package com.biathlonapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.data.model.News
import com.biathlonapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel  // ← исправлено
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        viewModel = NewsViewModel(requireContext())  // ← исправлено: создаем после inflate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
        viewModel.loadNews()
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter { news ->
            openNewsDetail(news)
        }

        binding.recyclerNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NewsFragment.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshNews()  // ← исправлено: refreshNews() вместо refresh()
        }
    }

    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)

            if (newsList.isEmpty()) {
                showEmptyState()
            } else {
                showNewsList()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading && adapter.itemCount == 0) {
                binding.progressLoading.visibility = View.VISIBLE
                binding.recyclerNews.visibility = View.GONE
                binding.layoutError.visibility = View.GONE
                binding.textEmpty.visibility = View.GONE
            } else {
                binding.progressLoading.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error)
            } else {
                hideError()
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun openNewsDetail(news: News) {
        val intent = android.content.Intent(requireContext(), NewsDetailActivity::class.java).apply {
            putExtra("news", news)
        }
        startActivity(intent)
    }

    private fun showEmptyState() {
        binding.recyclerNews.visibility = View.GONE
        binding.textEmpty.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE
        binding.textEmpty.text = "Новости пока не загрузились"
    }

    private fun showNewsList() {
        binding.recyclerNews.visibility = View.VISIBLE
        binding.textEmpty.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            viewModel.loadNews()
        }
        binding.recyclerNews.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE
    }

    private fun hideError() {
        binding.layoutError.visibility = View.GONE
        binding.recyclerNews.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}