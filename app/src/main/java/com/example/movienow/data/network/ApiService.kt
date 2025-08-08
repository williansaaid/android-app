package com.example.movienow.data.network

import com.example.movienow.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface defines the API endpoints we will use with Retrofit.
 * Each function corresponds to a specific API call.
 */
interface ApiService {

    /**
     * Gets the list of popular movies.
     * @param apiKey The API key for authentication with TMDb.
     * @param language The language for the response data (e.g., "es-ES").
     * @return A Response object containing a MovieResponse.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<MovieResponse>

    /**
     * Gets the list of top rated movies.
     * @param apiKey The API key for authentication with TMDb.
     * @param language The language for the response data.
     * @return A Response object containing a MovieResponse.
     */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<MovieResponse>

    /**
     * Searches for movies by a query string.
     * @param apiKey The API key for authentication with TMDb.
     * @param query The search term.
     * @param language The language for the response data.
     * @return A Response object containing a MovieResponse.
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "es-ES"
    ): Response<MovieResponse>
}