package com.biathlonapp.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.FragmentTeamListBinding
import com.biathlonapp.ui.athletes.AthletesAdapter
import com.biathlonapp.ui.athletes.AthletesViewModel

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AthletesViewModel by viewModels()
    private lateinit var adapter: AthletesAdapter

    private var categoryTitle: String = ""
    private var gender: String = ""
    private var sportsRank: String = "" // "МСМК" или "МС"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryTitle = it.getString("category_title", "")
            gender = it.getString("gender", "")
            sportsRank = it.getString("sports_rank", "")
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
        loadTeamAthletes()
    }

    private fun setupToolbar() {
        binding.toolbar.title = categoryTitle
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = AthletesAdapter { athlete ->
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

    private fun loadTeamAthletes() {
// Загружаем спортсменов по полу и разряду
        viewModel.loadAthletesByGenderAndRank(gender, sportsRank)
    }
    private fun showEmptyState() {
        binding.recyclerTeamAthletes.visibility = View.GONE
        binding.textEmpty.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE

        val message = when (sportsRank) {
            "МСМК" -> "В основной команде пока нет спортсменов"
            "МС" -> "В резервной команде пока нет спортсменов"
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
        fun newInstance(categoryTitle: String, gender: String, sportsRank: String): TeamListFragment {
            val fragment = TeamListFragment()
            val args = Bundle().apply {
                putString("category_title", categoryTitle)
                putString("gender", gender)
                putString("sports_rank", sportsRank)
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