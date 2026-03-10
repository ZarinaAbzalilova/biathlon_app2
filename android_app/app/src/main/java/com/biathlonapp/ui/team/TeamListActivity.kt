package com.biathlonapp.ui.team

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.TeamType
import com.biathlonapp.databinding.ActivityTeamListBinding
import com.biathlonapp.ui.athlete.AthleteDetailActivity

class TeamListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamListBinding
    private lateinit var viewModel: TeamListViewModel
    private lateinit var adapter: TeamAthleteAdapter

    private var categoryTitle: String = ""
    private var teamType: TeamType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryTitle = intent.getStringExtra("category_title") ?: ""
        teamType = intent.getSerializableExtra("team_type") as? TeamType

        setupToolbar()
        setupRecyclerView()
        setupViewModel()
        setupObservers()

        loadTeamAthletes()
    }

    private fun setupToolbar() {
        binding.toolbar.title = categoryTitle
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = TeamAthleteAdapter { athlete ->
            val intent = android.content.Intent(this, AthleteDetailActivity::class.java).apply {
                putExtra(AthleteDetailActivity.EXTRA_ATHLETE_ID, athlete.athleteId)
            }
            startActivity(intent)
        }

        binding.recyclerTeamAthletes.apply {
            layoutManager = LinearLayoutManager(this@TeamListActivity)
            adapter = this@TeamListActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[TeamListViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.athletes.observe(this) { athletes ->
            if (athletes.isNotEmpty()) {
                adapter.submitList(athletes)
                showAthletesList()
            } else {
                showEmptyState()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(this) { error ->
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
        binding.recyclerTeamAthletes.visibility = android.view.View.GONE
        binding.textEmpty.visibility = android.view.View.VISIBLE
        binding.layoutError.visibility = android.view.View.GONE

        val message = when (teamType) {
            TeamType.MEN_MAIN, TeamType.WOMEN_MAIN -> "В основной команде пока нет спортсменов"
            TeamType.MEN_RESERVE, TeamType.WOMEN_RESERVE -> "В резервной команде пока нет спортсменов"
            else -> "Спортсмены не найдены"
        }
        binding.textEmpty.text = message
    }

    private fun showAthletesList() {
        binding.recyclerTeamAthletes.visibility = android.view.View.VISIBLE
        binding.textEmpty.visibility = android.view.View.GONE
        binding.layoutError.visibility = android.view.View.GONE
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = android.view.View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            loadTeamAthletes()
        }
        binding.recyclerTeamAthletes.visibility = android.view.View.GONE
        binding.textEmpty.visibility = android.view.View.GONE
    }
}