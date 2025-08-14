package com.example.movienow.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.model.Movie
import com.example.movienow.data.repository.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> = _movieDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchMovieDetails(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val details = repository.getMovieDetails(movieId)
            details?.let {
                _movieDetails.postValue(it)
            }
            _isLoading.value = false
        }
    }
}