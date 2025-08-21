package com.example.movienow.data.repository

import com.example.movienow.data.model.CreditsResponse
import com.example.movienow.data.model.ImageFile
import com.example.movienow.data.model.Movie
import com.example.movienow.data.model.Video
import com.example.movienow.data.network.ApiClient
import com.example.movienow.util.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MovieRepository {

    private val apiService = ApiClient.apiService

    private fun filterMovies(movies: List<Movie>?): List<Movie>? {
        return movies?.filter { movie ->
            !movie.posterPath.isNullOrBlank() &&
                    movie.overview.isNotBlank() &&
                    movie.voteAverage > 0.0
        }
    }

    suspend fun getPopularMovies(page: Int): List<Movie>? {
        return try {
            val response = apiService.getPopularMovies(apiKey = Constants.API_KEY, page = page)
            if (response.isSuccessful) {
                filterMovies(response.body()?.results)
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
                filterMovies(response.body()?.results)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getNowPlayingMovies(page: Int): List<Movie>? {
        return try {
            val response = apiService.getNowPlayingMovies(apiKey = Constants.API_KEY, page = page)
            if (response.isSuccessful) {
                val initialFilter = filterMovies(response.body()?.results)

                val calendar = Calendar.getInstance()
                val today = calendar.time
                calendar.add(Calendar.DAY_OF_YEAR, -45)
                val fortyFiveDaysAgo = calendar.time

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                initialFilter?.filter { movie ->
                    try {
                        val releaseDate = dateFormat.parse(movie.releaseDate)
                        // Mantiene la película si su fecha de estreno está dentro del rango de los últimos 45 días
                        releaseDate != null && !releaseDate.before(fortyFiveDaysAgo) && !releaseDate.after(today)
                    } catch (e: Exception) {
                        false
                    }
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
                filterMovies(response.body()?.results)
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

    suspend fun getMovieVideos(movieId: Int): List<Video>? {
        return try {
            val response = apiService.getMovieVideos(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()?.results?.filter { it.site == "YouTube" && it.type == "Trailer" }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieCredits(movieId: Int): CreditsResponse? {
        return try {
            val response = apiService.getMovieCredits(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieImages(movieId: Int): List<ImageFile>? {
        return try {
            val response = apiService.getMovieImages(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                response.body()?.backdrops
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}