package com.example.movienow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.model.Movie
import com.example.movienow.data.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    // LiveData for popular movies
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    // LiveData for top rated movies
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> = _topRatedMovies

    // LiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        // Set loading to true before starting the network call
        _isLoading.value = true
        viewModelScope.launch {
            // Fetch popular movies
            val popular = repository.getPopularMovies()
            _popularMovies.postValue(popular ?: emptyList())

            // Fetch top rated movies
            val topRated = repository.getTopRatedMovies()
            _topRatedMovies.postValue(topRated ?: emptyList())

            // Set loading to false after both calls are complete
            _isLoading.value = false
        }
    }
}