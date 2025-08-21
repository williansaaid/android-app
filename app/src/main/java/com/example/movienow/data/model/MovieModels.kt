package com.example.movienow.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    @SerializedName("results") val results: List<Movie>
)

@Parcelize
data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable

@Parcelize
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("runtime") val runtime: Int?
) : Parcelable

data class VideoResponse(
    @SerializedName("results") val results: List<Video>
)

data class Video(
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String
)

data class CreditsResponse(
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Crew>
)

data class Cast(
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("character") val character: String
)

data class Crew(
    @SerializedName("name") val name: String,
    @SerializedName("job") val job: String
)

data class ImageResponse(
    @SerializedName("backdrops") val backdrops: List<ImageFile>
)

data class ImageFile(
    @SerializedName("file_path") val filePath: String
)