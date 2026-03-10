package com.biathlonapp.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.FragmentTeamListBinding
import com.biathlonapp.data.model.TeamType

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamListViewModel by viewModels()  // ← ИСПРАВЛЕНО
    private lateinit var adapter: TeamAthleteAdapter  // ← ИСПРАВЛЕНО

    private var categoryTitle: String = ""
    private var teamType: TeamType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryTitle = it.getString("category_title", "")
            teamType = it.getSerializable("team_type") as TeamType?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        loadTeamAthletes()
    }

    private fun setupToolbar() {
        binding.toolbar.title = categoryTitle
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = TeamAthleteAdapter { athlete ->
            val intent = android.content.Intent(requireContext(),
                com.biathlonapp.ui.athlete.AthleteDetailActivity::class.java).apply {
                putExtra(com.biathlonapp.ui.athlete.AthleteDetailActivity.EXTRA_ATHLETE_ID, athlete.athleteId)
            }
            startActivity(intent)
        }

        binding.recyclerTeamAthletes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TeamListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.athletes.observe(viewLifecycleOwner) { athletes ->
            if (athletes.isNotEmpty()) {
                adapter.submitList(athletes)
                showAthletesList()
            } else {
                showEmptyState()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showError(it)
            }
        }
    }

    private fun loadTeamAthletes() {
        teamType?.let {
            viewModel.loadTeamAthletes(it)
        }
    }

    private fun showEmptyState() {
        binding.recyclerTeamAthletes.visibility = View.GONE
        binding.textEmpty.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE

        val message = when (teamType) {
            TeamType.MEN_MAIN, TeamType.WOMEN_MAIN -> "В основной команде пока нет спортсменов"
            TeamType.MEN_RESERVE, TeamType.WOMEN_RESERVE -> "В резервной команде пока нет спортсменов"
            else -> "Спортсмены не найдены"
        }
        binding.textEmpty.text = message
    }

    private fun showAthletesList() {
        binding.recyclerTeamAthletes.visibility = View.VISIBLE
        binding.textEmpty.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            loadTeamAthletes()
        }
        binding.recyclerTeamAthletes.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE
    }

    companion object {
        fun newInstance(categoryTitle: String, teamType: TeamType): TeamListFragment {
            val fragment = TeamListFragment()
            val args = Bundle().apply {
                putString("category_title", categoryTitle)
                putSerializable("team_type", teamType)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}