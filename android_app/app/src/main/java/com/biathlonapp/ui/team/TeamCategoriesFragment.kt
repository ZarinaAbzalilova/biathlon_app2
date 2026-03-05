package com.biathlonapp.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.MainActivity
import com.biathlonapp.R
import com.biathlonapp.data.model.TeamItem
import com.biathlonapp.databinding.FragmentTeamCategoriesBinding

class TeamCategoriesFragment : Fragment() {

    private var _binding: FragmentTeamCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TeamSectionAdapter

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
        loadTeamSections()
    }

    private fun setupRecyclerView() {
        adapter = TeamSectionAdapter { category ->
            openTeamList(category)
        }

        binding.recyclerCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TeamCategoriesFragment.adapter
        }
    }

    private fun loadTeamSections() {
        val items = listOf(
            // Мужская секция
            TeamItem.Header("Мужская"),
            TeamItem.Category(
                id = 1,
                title = "Основная команда",
                gender = "male",
                teamType = "main"
            ),
            TeamItem.Category(
                id = 2,
                title = "Резервная команда",
                gender = "male",
                teamType = "reserve"
            ),

            // Женская секция
            TeamItem.Header("Женская"),
            TeamItem.Category(
                id = 3,
                title = "Основная команда",
                gender = "female",
                teamType = "main"
            ),
            TeamItem.Category(
                id = 4,
                title = "Резервная команда",
                gender = "female",
                teamType = "reserve"
            )
        )
        adapter.submitList(items)
    }

    private fun openTeamList(category: TeamItem.Category) {
        val sportsRank = when {
            category.title.contains("Основная") || category.teamType == "main" -> "МСМК"
            category.title.contains("Резервная") || category.teamType == "reserve" -> "МС"
            else -> ""
        }

        val fragment = TeamListFragment.newInstance(
            categoryTitle = category.title,
            gender = category.gender,
            sportsRank = sportsRank
        )

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack("team_list")
            .commit()

        // Скрываем нижнюю навигацию
        //val mainActivity = requireActivity() as? MainActivity
        //mainActivity?.hideBottomNavigation(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}