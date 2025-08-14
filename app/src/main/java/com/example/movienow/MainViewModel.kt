package com.example.movienow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.model.Movie
import com.example.movienow.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> = _topRatedMovies

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    var isFetchingPopular = false
    var isFetchingTopRated = false

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            val popular = repository.getPopularMovies(page = popularMoviesPage)
            val topRated = repository.getTopRatedMovies(page = topRatedMoviesPage)

            _popularMovies.postValue(popular ?: emptyList())
            _topRatedMovies.postValue(topRated ?: emptyList())

            _isReady.value = true
        }
    }

    fun loadMorePopularMovies() {
        if (isFetchingPopular) return
        viewModelScope.launch {
            isFetchingPopular = true
            popularMoviesPage++
            val newMovies = repository.getPopularMovies(page = popularMoviesPage)
            if (!newMovies.isNullOrEmpty()) {
                val currentMovies = _popularMovies.value ?: emptyList()
                _popularMovies.postValue(currentMovies + newMovies)
            }
            isFetchingPopular = false
        }
    }

    fun loadMoreTopRatedMovies() {
        if (isFetchingTopRated) return
        viewModelScope.launch {
            isFetchingTopRated = true
            topRatedMoviesPage++
            val newMovies = repository.getTopRatedMovies(page = topRatedMoviesPage)
            if (!newMovies.isNullOrEmpty()) {
                val currentMovies = _topRatedMovies.value ?: emptyList()
                _topRatedMovies.postValue(currentMovies + newMovies)
            }
            isFetchingTopRated = false
        }
    }
}