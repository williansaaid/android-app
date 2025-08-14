package com.example.movienow.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.model.Movie
import com.example.movienow.data.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showNoResults = MutableLiveData<Boolean>()
    val showNoResults: LiveData<Boolean> = _showNoResults

    // StateFlow to handle search query changes
    private val searchQuery = MutableStateFlow("")

    init {
        searchQuery
            .debounce(500) // Espera 500ms después de que el usuario deja de escribir
            .filter { query ->
                return@filter query.isNotBlank() // Solo busca si el texto no está vacío
            }
            .onEach { query ->
                performSearch(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        if (query.isBlank()) {
            // Limpia los resultados si la búsqueda está vacía
            _searchResults.value = emptyList()
            _showNoResults.value = false
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _showNoResults.postValue(false)
            val results = repository.searchMovies(query)
            _searchResults.postValue(results ?: emptyList())
            _showNoResults.postValue(results.isNullOrEmpty())
            _isLoading.postValue(false)
        }
    }
}