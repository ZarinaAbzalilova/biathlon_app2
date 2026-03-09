package com.biathlonapp.ui.stats

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.biathlonapp.databinding.ActivityAthleteStatsBinding
import com.biathlonapp.data.model.RaceResult
import com.biathlonapp.utils.DisciplineFormatter
import java.text.SimpleDateFormat
import java.util.Locale

class AthleteStatsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athlete_id"
    }

    private lateinit var binding: ActivityAthleteStatsBinding
    private val viewModel: AthleteStatsViewModel by viewModels()
    private lateinit var adapter: RaceResultsAdapter
    private lateinit var disciplineAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAthleteStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val athleteId = intent.getStringExtra(EXTRA_ATHLETE_ID)
        if (athleteId != null) {
            setupRecyclerView()
            setupDisciplineFilter()
            loadAthleteStats(athleteId)
            observeViewModel()
        } else {
            finish()
        }
    }

    private fun setupRecyclerView() {
        // В setupRecyclerView()
        adapter = RaceResultsAdapter { raceResult ->
            downloadPdf(raceResult)
        }
        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(this@AthleteStatsActivity)
            adapter = this@AthleteStatsActivity.adapter
        }
    }

    private fun downloadPdf(raceResult: RaceResult) {
        // Используем pdfUrl который уже пришел из API
        val pdfUrl = raceResult.pdfUrl

        if (!pdfUrl.isNullOrEmpty()) {
            try {
                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val fileName = generateFileName(raceResult)

                val request = DownloadManager.Request(Uri.parse(pdfUrl))
                    .setTitle("Результаты гонки: ${raceResult.raceId}")
                    .setDescription("Загрузка протокола результатов")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

                val downloadId = downloadManager.enqueue(request)
                Toast.makeText(this, "Загрузка начата: $fileName", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка загрузки: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "PDF недоступен для этой гонки", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPdfDownload(pdfUrl: String, raceResult: RaceResult) {
        try {
            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val fileName = generateFileName(raceResult)

            val request = DownloadManager.Request(Uri.parse(pdfUrl))
                .setTitle("Результаты гонки: ${raceResult.raceId}")
                .setDescription("Загрузка протокола результатов")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val downloadId = downloadManager.enqueue(request)
            Toast.makeText(this, "Загрузка начата: $fileName", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка загрузки: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun generateFileName(raceResult: RaceResult): String {
        val datePart = raceResult.raceInfo?.date?.let { date ->
            try {
                val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                val outputFormat = SimpleDateFormat("yyyyMMdd", Locale.US)
                val parsedDate = inputFormat.parse(date)
                parsedDate?.let { outputFormat.format(it) } ?: ""
            } catch (e: Exception) {
                ""
            }
        } ?: ""

        val disciplinePart = DisciplineFormatter.format(raceResult.raceInfo?.discipline)

        return "Biathlon_${datePart}_${disciplinePart}.pdf"
    }

    private fun setupDisciplineFilter() {
        disciplineAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        disciplineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDiscipline.adapter = disciplineAdapter

        binding.spinnerDiscipline.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDiscipline = parent?.getItemAtPosition(position) as? String
                viewModel.setDisciplineFilter(selectedDiscipline)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.setDisciplineFilter(null)
            }
        }
    }

    private fun loadAthleteStats(athleteId: String) {
        viewModel.loadAthleteResults(athleteId)
    }

    private fun observeViewModel() {
        viewModel.filteredResults.observe(this) { results ->
            adapter.submitList(results)

            if (results.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }

        viewModel.athleteResults.observe(this) { response ->
            // ИСПРАВЛЕНИЕ: формируем fullName из lastName и firstName
            val fullName = "${response.athlete.lastName ?: ""} ${response.athlete.firstName ?: ""}".trim()
            supportActionBar?.title = "$fullName - Статистика"
        }

        viewModel.availableDisciplines.observe(this) { disciplines ->
            disciplineAdapter.clear()
            disciplineAdapter.addAll(disciplines)

            // Show filter only if there are multiple disciplines
            if (disciplines.size > 2) { // More than just "Все дисциплины" + one discipline
                binding.layoutFilter.visibility = View.VISIBLE
            } else {
                binding.layoutFilter.visibility = View.GONE
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressLoading.visibility = View.VISIBLE
                binding.recyclerResults.visibility = View.GONE
                binding.layoutError.visibility = View.GONE
                binding.textEmpty.visibility = View.GONE
                binding.layoutFilter.visibility = View.GONE
            } else {
                binding.progressLoading.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                showError(it)
            } ?: run {
                hideError()
            }
        }
    }

    private fun showError(message: String) {
        binding.layoutError.visibility = View.VISIBLE
        binding.textError.text = message
        binding.buttonRetry.setOnClickListener {
            val athleteId = intent.getStringExtra(EXTRA_ATHLETE_ID)
            athleteId?.let { viewModel.loadAthleteResults(it) }
        }
        binding.recyclerResults.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE
        binding.layoutFilter.visibility = View.GONE
    }

    private fun hideError() {
        binding.layoutError.visibility = View.GONE
        binding.recyclerResults.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.textEmpty.visibility = View.VISIBLE
        binding.recyclerResults.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.textEmpty.visibility = View.GONE
        binding.recyclerResults.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}