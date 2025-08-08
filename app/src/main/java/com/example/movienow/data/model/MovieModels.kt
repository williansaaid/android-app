package com.example.movienow.data.model

import com.google.gson.annotations.SerializedName

/**
 * This data class represents the entire response from the TMDb API
 * when we request a list of movies.
 * @param results A list of Movie objects.
 */
data class MovieResponse(
    @SerializedName("results") val results: List<Movie>
)

/**
 * This data class represents a single Movie object with its details.
 * We use @SerializedName to map the JSON key from the API to our Kotlin property.
 * For example, the API sends "poster_path", we map it to our "posterPath" variable.
 */
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?, // Can be null
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double
)