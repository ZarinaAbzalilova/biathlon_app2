package com.biathlonapp.ui.stats

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.data.local.FavoriteResult
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.databinding.ActivityAthleteStatsBinding
import com.biathlonapp.ui.raceprotocol.RaceProtocolActivity
import kotlinx.coroutines.launch

class AthleteStatsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athlete_id"
        const val EXTRA_ATHLETE_GENDER = "athlete_gender"
    }
    private var athleteGender: String? = null
    private lateinit var binding: ActivityAthleteStatsBinding
    private lateinit var viewModel: AthleteStatsViewModel
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var adapter: RaceResultsAdapter
    private var athleteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAthleteStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        athleteId = intent.getStringExtra(EXTRA_ATHLETE_ID)
        athleteGender = intent.getStringExtra(EXTRA_ATHLETE_GENDER)

        if (athleteId.isNullOrEmpty()) {
            Toast.makeText(this, "Ошибка: ID спортсмена не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        favoritesRepository = FavoritesRepository(this)
        viewModel = ViewModelProvider(this)[AthleteStatsViewModel::class.java]

        setupToolbar()
        setupRecyclerView()
        setupFilterSpinner()

        // Потом загружаем с сервера
        viewModel.loadAthleteResults(athleteId!!)
        observeViewModel()
        Log.d("StatsDebug", "AthleteId: $athleteId")
    }

    private fun loadCachedResults() {
        lifecycleScope.launch {
            val cached = favoritesRepository.getCachedResults(athleteId!!)
            if (cached.isNotEmpty()) {
                showCachedResults(cached)
            }
        }
    }

    private fun showCachedResults(results: List<FavoriteResult>) {
        val displayItems = results.map { result ->
            RaceResultDisplay(
                discipline = result.discipline ?: "Неизвестно",
                date = result.date ?: "",
                nameRace = result.nameRace ?: "",
                placeRace = result.placeRace ?: "",
                startNumber = result.startNumber,
                finishPlace = result.finishPlace,
                missCount = result.missCount,
                athleteGender = null,
                pdfUrl = null,
                raceId = null
            )
        }
        adapter.submitList(displayItems)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Результаты"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = RaceResultsAdapter(
            onPdfDownloadClick = { pdfUrl ->
                openPdf(pdfUrl)
            },
            onRaceClick = { raceId, gender ->
                val intent = Intent(this, RaceProtocolActivity::class.java).apply {
                    putExtra(RaceProtocolActivity.EXTRA_RACE_ID, raceId)
                    putExtra(RaceProtocolActivity.EXTRA_GENDER, gender ?: athleteGender)  // ← используем gender из результата или из activity
                }
                startActivity(intent)
            }
        )
        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(this@AthleteStatsActivity)
            adapter = this@AthleteStatsActivity.adapter
        }
    }

    private fun setupFilterSpinner() {
        val spinnerAdapter = ArrayAdapter<String>(
            this,
            R.layout.simple_spinner_item,
            mutableListOf("Загрузка...")
        )
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerDiscipline.adapter = spinnerAdapter

        binding.spinnerDiscipline.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = parent?.getItemAtPosition(position) as? String
                if (selected != null && selected != "Загрузка...") {
                    viewModel.setDisciplineFilter(if (selected == "Все дисциплины") null else selected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.setDisciplineFilter(null)
            }
        }
    }

    private fun openPdf(pdfUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
        startActivity(intent)
    }

    private fun observeViewModel() {

        // Наблюдаем за отфильтрованными результатами
        viewModel.filteredResults.observe(this) { results ->
            android.util.Log.d("FilterDebug", "Results received in UI: ${results.size}")
            if (results.isNotEmpty()) {
                android.util.Log.d("FilterDebug", "First result: ${results[0].nameRace}")
            }
            adapter.submitList(results)
            adapter.submitList(results)

            if (results.isEmpty()) {
                binding.textEmpty.visibility = View.VISIBLE
                binding.recyclerResults.visibility = View.GONE
            } else {
                binding.textEmpty.visibility = View.GONE
                binding.recyclerResults.visibility = View.VISIBLE
            }
        }

        // Наблюдаем за доступными дисциплинами
        viewModel.availableDisciplines.observe(this) { disciplines ->
            Log.d("FilterDebug", "Disciplines received: $disciplines")
            val spinnerAdapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_item,
                disciplines
            )
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerDiscipline.adapter = spinnerAdapter

            // Показываем фильтр только если есть дисциплины (больше чем "Все дисциплины")
            binding.layoutFilter.visibility = if (disciplines.size > 1) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error != null && adapter.itemCount == 0) {
                binding.layoutError.visibility = View.VISIBLE
                binding.textError.text = error
            } else {
                binding.layoutError.visibility = View.GONE
            }
        }

        viewModel.isFromCache.observe(this) { isFromCache ->
            // Можешь добавить индикатор кэша если нужно
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}