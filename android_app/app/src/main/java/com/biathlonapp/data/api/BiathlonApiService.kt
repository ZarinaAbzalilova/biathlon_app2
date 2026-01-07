package com.biathlonapp.data.api

import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.AthleteResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.biathlonapp.data.model.PdfUrlResponse

interface BiathlonApiService {

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

    companion object {
        //const val BASE_URL = "http://10.90.223.101:5000/" // Для эмулятора Android Studio
        const val BASE_URL = "http://192.168.114.23:5000/" // Для реального устройства (замените на IP вашего компьютера)
    }
}

data class PaginatedResponse<T>(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<T>
)
