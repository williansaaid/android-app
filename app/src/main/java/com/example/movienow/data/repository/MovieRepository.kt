package com.example.movienow.data.repository

import com.example.movienow.data.model.Movie
import com.example.movienow.data.network.ApiClient
import com.example.movienow.util.Constants

class MovieRepository {

    private val apiService = ApiClient.apiService

    suspend fun getPopularMovies(page: Int): List<Movie>? {
        return try {
            val response = apiService.getPopularMovies(apiKey = Constants.API_KEY, page = page)
            if (response.isSuccessful) {
                // Filtra las películas para asegurar que tengan póster, sinopsis y calificación
                response.body()?.results?.filter { movie ->
                    !movie.posterPath.isNullOrBlank() &&
                            movie.overview.isNotBlank() &&
                            movie.voteAverage > 0.0
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getTopRatedMovies(page: Int): List<Movie>? {
        return try {
            val response = apiService.getTopRatedMovies(apiKey = Constants.API_KEY, page = page)
            if (response.isSuccessful) {
                // Filtra las películas para asegurar que tengan póster, sinopsis y calificación
                response.body()?.results?.filter { movie ->
                    !movie.posterPath.isNullOrBlank() &&
                            movie.overview.isNotBlank() &&
                            movie.voteAverage > 0.0
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun searchMovies(query: String): List<Movie>? {
        return try {
            val response = apiService.searchMovies(apiKey = Constants.API_KEY, query = query)
            if (response.isSuccessful) {
                // Filtra las películas para asegurar que tengan póster, sinopsis y calificación
                response.body()?.results?.filter { movie ->
                    !movie.posterPath.isNullOrBlank() &&
                            movie.overview.isNotBlank() &&
                            movie.voteAverage > 0.0
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieDetails(movieId: Int): Movie? {
        return try {
            val response = apiService.getMovieDetails(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}