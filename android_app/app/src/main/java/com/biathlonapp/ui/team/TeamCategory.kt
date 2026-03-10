package com.biathlonapp.ui.team

import com.biathlonapp.data.model.TeamType
import java.io.Serializable

data class TeamCategory(
    val title: String,
    val type: TeamType
) : Serializable