package com.biathlonapp.data.model

data class RelayResultsResponse(
    val race_info: RelayRaceInfo,
    val results: List<RelayTeam>,
    val results_count: Int
)

data class RelayRaceInfo(
    val race_id: String,
    val name_race: String,
    val discipline: String,
    val date: String,
    val place_race: String,
    val is_relay: Boolean,
    val pdf_urls: List<PdfUrl>?
)

data class RelayTeam(
    val team_name: String,
    val finish_place: Int?,
    val total_miss_count: Int?,
    val finish_time: String?,
    val members: List<RelayTeamMember>
)

data class RelayTeamMember(
    val leg_number: Int,
    val athlete_id: Long,  // ← ИЗМЕНИТЕ С Int НА Long
    val full_name: String,
    val miss_count: Int,
    val start_number: Int,
    val gender: String
)

data class PdfUrl(
    val pdf_url: String,
    val gender: String
)