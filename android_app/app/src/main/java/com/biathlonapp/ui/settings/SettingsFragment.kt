package com.biathlonapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.biathlonapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardProfile.setOnClickListener {
            startActivity(SettingsActivity.newIntent(requireContext()))
        }

        binding.cardNotifications.setOnClickListener {
            // TODO: Настройки уведомлений
        }

        binding.cardTheme.setOnClickListener {
            // TODO: Настройки темы
        }

        binding.cardAbout.setOnClickListener {
            showAboutDialog()
        }
    }

    private fun showAboutDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("О приложении")
            .setMessage("Биатлон Приложение\nВерсия 1.0\n\nПриложение для отслеживания результатов биатлонистов и календаря гонок.")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}