package com.biathlonapp.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.data.model.TeamType
import com.biathlonapp.databinding.FragmentTeamCategoriesBinding

class TeamCategoriesFragment : Fragment() {

    private var _binding: FragmentTeamCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: TeamCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCategories()
    }

    private fun setupRecyclerView() {
        categoriesAdapter = TeamCategoryAdapter { category ->
            openTeamList(category)
        }

        binding.recyclerCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoriesAdapter
        }
    }

    private fun loadCategories() {
        val categories = listOf(
            TeamCategory("Основная мужская сборная", TeamType.MEN_MAIN),
            TeamCategory("Основная женская сборная", TeamType.WOMEN_MAIN),
            TeamCategory("Резервная мужская сборная", TeamType.MEN_RESERVE),
            TeamCategory("Резервная женская сборная", TeamType.WOMEN_RESERVE)
        )
        categoriesAdapter.submitList(categories)
    }

    private fun openTeamList(category: TeamCategory) {
        val intent = android.content.Intent(requireContext(), TeamListActivity::class.java).apply {
            putExtra("category_title", category.title)
            putExtra("team_type", category.type)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}