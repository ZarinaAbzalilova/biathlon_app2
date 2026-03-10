package com.biathlonapp.ui.stats

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.data.local.FavoriteResult
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.databinding.ActivityAthleteStatsBinding
import kotlinx.coroutines.launch

class AthleteStatsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athlete_id"
    }

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
        if (athleteId == null) {
            finish()
            return
        }

        favoritesRepository = FavoritesRepository(this)
        viewModel = androidx.lifecycle.ViewModelProvider(this)[AthleteStatsViewModel::class.java]

        setupToolbar()
        setupRecyclerView()

        // Сначала показываем кэш (если есть)
        loadCachedResults()

        // Потом загружаем с сервера
        viewModel.loadAthleteResults(athleteId!!)
        observeViewModel()
        android.util.Log.d("StatsDebug", "AthleteId: $athleteId")
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
                pdfUrl = null,  // У кэшированных результатов нет pdfUrl
                raceId = null
            )
        }
        adapter.submitList(displayItems)
        //binding.textCacheHint.visibility = View.VISIBLE
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
                openPdf(pdfUrl)  // ← обработчик клика
            }
        )
        binding.recyclerResults.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@AthleteStatsActivity)
            adapter = this@AthleteStatsActivity.adapter
        }
    }

    private fun openPdf(pdfUrl: String) {
        // Открываем PDF в браузере или скачиваем
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(pdfUrl))
        startActivity(intent)
    }

    private fun observeViewModel() {
        viewModel.athleteResults.observe(this) { response ->
            val resultsList = response.races ?: emptyList()
            val displayItems = resultsList.map { race ->
                RaceResultDisplay(
                    discipline = race.raceInfo?.discipline ?: "Неизвестно",
                    date = race.raceInfo?.date ?: "",
                    nameRace = race.raceInfo?.nameRace ?: "",
                    placeRace = race.raceInfo?.placeRace ?: "",
                    startNumber = race.athletePerformance?.startNumber,
                    finishPlace = race.athletePerformance?.finishPlace,
                    missCount = race.athletePerformance?.missCount,
                    pdfUrl = race.pdfUrl,  // ← ПЕРЕДАЕМ pdfUrl
                    raceId = race.raceId    // ← ПЕРЕДАЕМ raceId
                )
            }
            adapter.submitList(displayItems)
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
            //binding.textCacheHint.visibility = if (isFromCache && adapter.itemCount > 0)
                //View.VISIBLE else View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}