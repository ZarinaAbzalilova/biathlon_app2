package com.biathlonapp.ui.raceprotocol

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.RelayTeam
import com.biathlonapp.databinding.ItemRelayTeamBinding

class RelayProtocolAdapter(
    private val onTeamClick: (String) -> Unit
) : RecyclerView.Adapter<RelayProtocolAdapter.ViewHolder>() {

    private var teams: List<RelayTeam> = emptyList()

    fun submitList(newTeams: List<RelayTeam>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRelayTeamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onTeamClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount() = teams.size

    class ViewHolder(
        private val binding: ItemRelayTeamBinding,
        private val onTeamClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(team: RelayTeam) {
            binding.textPlace.text = team.finish_place?.toString() ?: "-"
            binding.textTeamName.text = team.team_name
            binding.textMissCount.text = team.total_miss_count?.toString() ?: "-"
            binding.textFinishTime.text = team.finish_time ?: "-"

            // Показываем первых трех участников
            val membersText = team.members.take(3).joinToString { it.full_name.split(" ")[0] }
            binding.textMembers.text = membersText

            if (team.members.size > 3) {
                binding.textMembersCount.text = "+${team.members.size - 3}"
                binding.textMembersCount.visibility = android.view.View.VISIBLE
            } else {
                binding.textMembersCount.visibility = android.view.View.GONE
            }

            binding.root.setOnClickListener {
                onTeamClick(team.team_name)
            }
        }
    }
}