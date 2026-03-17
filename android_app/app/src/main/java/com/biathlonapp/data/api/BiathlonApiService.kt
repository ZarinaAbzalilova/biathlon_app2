package com.biathlonapp.data.api

import com.biathlonapp.data.model.Athlete
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.biathlonapp.data.model.PdfUrlResponse
import com.biathlonapp.data.model.RaceEvent
import retrofit2.Retrofit
import com.biathlonapp.data.model.AthleteResultsResponse
import retrofit2.converter.gson.GsonConverterFactory

data class CalendarRaceResponse(
    val id: String,
    val title: String,
    val location: String,
    val discipline: String,
    val gender: String,
    val date: String,
    val pdf_url: String
)

data class DayEventsResponse(
    val date: String,
    val has_male: Int,
    val has_female: Int,
    val races: List<CalendarRaceResponse>
)

interface BiathlonApiService {

    @GET("api/athletes/by-team")
    suspend fun getAthletesByTeam(
        @Query("team") team: String
    ): Response<List<Athlete>>

    @GET("api/athletes")
    suspend fun getAthletes(): Response<List<Athlete>>

    @GET("api/athletes/search")
    suspend fun searchAthletes(@Query("q") query: String): Response<List<Athlete>>

    @GET("api/athletes/{athleteId}/results")
    suspend fun getAthleteResults(@Path("athleteId") athleteId: String): Response<AthleteResultsResponse>

    @GET("api/athletes/paginated")
    suspend fun getAthletesPaginated(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50
    ): Response<PaginatedResponse<Athlete>>

    @GET("races/{raceId}/pdf-url")
    suspend fun getRacePdfUrl(
        @Path("raceId") raceId: String,
        @Query("athlete_id") athleteId: String
    ): Response<PdfUrlResponse>

    // ===== ОБНОВЛЕННЫЕ МЕТОДЫ ДЛЯ КАЛЕНДАРЯ =====
    @GET("api/calendar/races")
    suspend fun getCalendarRaces(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<List<DayEventsResponse>>

    @GET("api/calendar/races/by-date")
    suspend fun getRacesByDate(
        @Query("date") date: String
    ): Response<List<CalendarRaceResponse>>

    @GET("api/race/{raceId}/{gender}")
    suspend fun getRaceDetails(
        @Path("raceId") raceId: String,
        @Path("gender") gender: String
    ): Response<CalendarRaceResponse>
    companion object {
        const val BASE_URL = "https://biathlon-app2.onrender.com"
        fun create(): BiathlonApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BiathlonApiService::class.java)
        }
    }

    data class PaginatedResponse<T>(
        val page: Int,
        val per_page: Int,
        val total: Int,
        val total_pages: Int,
        val data: List<T>
    )
}