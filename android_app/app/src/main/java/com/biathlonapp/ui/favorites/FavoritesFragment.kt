package com.biathlonapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.FragmentFavoritesBinding
import com.biathlonapp.ui.athletes.AthletesAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AthletesAdapter

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
        loadFavorites()
    }

    private fun setupRecyclerView() {
        adapter = AthletesAdapter { athlete ->
            // Открываем детали спортсмена
            val intent = android.content.Intent(requireContext(),
                com.biathlonapp.ui.athlete.AthleteDetailActivity::class.java).apply {
                putExtra(com.biathlonapp.ui.athlete.AthleteDetailActivity.EXTRA_ATHLETE_ID, athlete.athleteId)
            }
            startActivity(intent)
        }

        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

    private fun loadFavorites() {
        // TODO: Загрузить избранных спортсменов из SharedPreferences или БД
        // Пока показываем заглушку
        binding.textEmpty.visibility = View.VISIBLE
        binding.recyclerFavorites.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}