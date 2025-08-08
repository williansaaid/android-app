package com.example.movienow.data.repository

import com.example.movienow.data.model.Movie
import com.example.movienow.data.network.ApiClient
import com.example.movienow.util.Constants

/**
 * Repository class that works with remote data sources.
 * It's the single source of truth for the movie data.
 */
class MovieRepository {

    private val apiService = ApiClient.apiService

    /**
     * Fetches the list of popular movies from the API.
     * @return A list of Movie objects or null if the call fails.
     */
    suspend fun getPopularMovies(): List<Movie>? {
        return try {
            val response = apiService.getPopularMovies(apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                // Here you could handle specific error codes (401, 404, etc.)
                null
            }
        } catch (e: Exception) {
            // Here you could log the exception
            null
        }
    }

    /**
     * Fetches the list of top rated movies from the API.
     * @return A list of Movie objects or null if the call fails.
     */
    suspend fun getTopRatedMovies(): List<Movie>? {
        // This follows the same logic as getPopularMovies
        return try {
            val response = apiService.getTopRatedMovies(apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Searches for movies based on a query.
     * @param query The search term.
     * @return A list of Movie objects or null if the call fails.
     */
    suspend fun searchMovies(query: String): List<Movie>? {
        return try {
            val response = apiService.searchMovies(apiKey = Constants.API_KEY, query = query)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}