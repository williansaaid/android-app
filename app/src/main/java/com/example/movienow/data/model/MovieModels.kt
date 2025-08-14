package com.example.movienow.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents the API response for a list of movies.
 */
data class MovieResponse(
    @SerializedName("results") val results: List<Movie>
)

/**
 * Represents a single Genre object.
 */
@Parcelize
data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable

/**
 * Represents a single Movie object.
 * We add @Parcelize to be able to pass this object between fragments.
 */
@Parcelize
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?, // For the detail screen background
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genres") val genres: List<Genre>? // List of genres for the detail screen
) : Parcelable
