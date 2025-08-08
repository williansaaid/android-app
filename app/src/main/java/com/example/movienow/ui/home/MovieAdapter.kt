package com.example.movienow.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.data.model.Movie
import com.example.movienow.databinding.ItemMovieBinding
import com.example.movienow.util.Constants

class MovieAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Inner class for the ViewHolder
    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val imageUrl = Constants.IMAGE_BASE_URL + movie.posterPath
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.moviePosterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    // Helper function to update the data in the adapter
    fun updateData(newMovies: List<Movie>) {
        this.movies = newMovies
        notifyDataSetChanged() // Refreshes the list
    }
}