package com.example.movienow.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.model.Cast
import com.example.movienow.data.model.ImageFile
import com.example.movienow.data.model.Movie
import com.example.movienow.data.repository.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> = _movieDetails

    private val _trailerKey = MutableLiveData<String?>()
    val trailerKey: LiveData<String?> = _trailerKey

    private val _director = MutableLiveData<String?>()
    val director: LiveData<String?> = _director

    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>> = _cast

    private val _images = MutableLiveData<List<ImageFile>>()
    val images: LiveData<List<ImageFile>> = _images

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchMovieDetails(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            // Pedimos todos los datos en paralelo
            val details = repository.getMovieDetails(movieId)
            val videos = repository.getMovieVideos(movieId)
            val credits = repository.getMovieCredits(movieId)
            val movieImages = repository.getMovieImages(movieId)

            details?.let {
                _movieDetails.postValue(it)
            }

            val officialTrailer = videos?.firstOrNull()
            _trailerKey.postValue(officialTrailer?.key)

            val directorName = credits?.crew?.find { it.job == "Director" }?.name
            _director.postValue(directorName)

            _cast.postValue(credits?.cast?.take(10) ?: emptyList())

            _images.postValue(movieImages ?: emptyList())

            _isLoading.value = false
        }
    }
}
