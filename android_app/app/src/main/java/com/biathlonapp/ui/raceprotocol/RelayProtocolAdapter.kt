package com.biathlonapp.ui.raceprotocol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biathlonapp.data.model.RelayTeam
import com.biathlonapp.data.model.RelayTeamMember
import com.biathlonapp.databinding.ItemRelayTeamBinding

class RelayProtocolAdapter(
    private val onTeamClick: (String, List<RelayTeamMember>) -> Unit
) : RecyclerView.Adapter<RelayProtocolAdapter.ViewHolder>() {

    private var teams: List<RelayTeam> = emptyList()
    private var expandedPositions = mutableSetOf<Int>()

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
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams[position], position, expandedPositions.contains(position))
    }

    override fun getItemCount() = teams.size

    inner class ViewHolder(
        private val binding: ItemRelayTeamBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(team: RelayTeam, position: Int, isExpanded: Boolean) {
            binding.textPlace.text = team.finish_place?.toString() ?: "-"
            binding.textTeamName.text = team.team_name
            binding.textMissCount.text = "Промахи: ${team.total_miss_count ?: "-"}"
            binding.textFinishTime.text = team.finish_time ?: "-"

            // Раскрывающийся список участников
            if (isExpanded) {
                binding.expandedMembersContainer.removeAllViews()
                team.members.forEach { member ->
                    val memberView = createMemberView(member)
                    binding.expandedMembersContainer.addView(memberView)
                }
                binding.expandedMembersContainer.visibility = View.VISIBLE
                binding.imageExpand.setImageResource(android.R.drawable.arrow_up_float)
            } else {
                binding.expandedMembersContainer.visibility = View.GONE
                binding.imageExpand.setImageResource(android.R.drawable.arrow_down_float)
            }

            binding.root.setOnClickListener {
                if (isExpanded) {
                    expandedPositions.remove(position)
                } else {
                    expandedPositions.add(position)
                }
                notifyItemChanged(position)
            }
        }

        private fun createMemberView(member: RelayTeamMember): View {
            val textView = TextView(binding.root.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 8
                    leftMargin = 24
                }
                // Только этап, фамилия и имя
                text = "${member.leg_number}. ${member.full_name}"
                textSize = 14f
                setTextColor(binding.root.context.getColor(android.R.color.darker_gray))

                // При нажатии на участника открываем его детали
                setOnClickListener {
                    val intent = android.content.Intent(binding.root.context, com.biathlonapp.ui.athlete.AthleteDetailActivity::class.java)
                    intent.putExtra(com.biathlonapp.ui.athlete.AthleteDetailActivity.EXTRA_ATHLETE_ID, member.athlete_id.toString())
                    binding.root.context.startActivity(intent)
                }
            }
            return textView
        }
    }
}