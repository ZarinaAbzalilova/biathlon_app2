package com.biathlonapp.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.databinding.FragmentFavoritesBinding
import com.biathlonapp.ui.athlete.AthleteDetailActivity

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(
            onItemClick = { favorite ->
                openAthleteDetail(favorite)
            },
            onRemoveClick = { favorite ->
                viewModel.removeFromFavorites(favorite.athleteId)
            }
        )

        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadFavorites()
        }
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)

            if (favorites.isEmpty()) {
                showEmptyState()
            } else {
                showFavoritesList()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error)
            } else {
                hideError()
            }
        }
    }

    private fun openAthleteDetail(favorite: FavoriteAthlete) {
        val intent = Intent(requireContext(), AthleteDetailActivity::class.java).apply {
            putExtra(AthleteDetailActivity.EXTRA_ATHLETE_ID, favorite.athleteId)
            // Можно передать и другие данные, если нужно
            putExtra("athlete_name", "${favorite.name} ${favorite.surname}")
        }
        startActivity(intent)
    }

    private fun showEmptyState() {
        binding.recyclerFavorites.visibility = View.GONE
        binding.textEmpty.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE
        binding.textEmpty.text = "У вас пока нет избранных спортсменов"
    }

    private fun showFavoritesList() {
        binding.recyclerFavorites.visibility = View.VISIBLE
        binding.textEmpty.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            viewModel.loadFavorites()
        }
        binding.recyclerFavorites.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
    }

    private fun hideError() {
        binding.layoutError.visibility = View.GONE
        binding.recyclerFavorites.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список при возвращении на экран
        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}