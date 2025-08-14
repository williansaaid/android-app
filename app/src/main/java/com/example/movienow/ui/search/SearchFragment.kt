package com.example.movienow.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movienow.data.model.Movie
import com.example.movienow.databinding.FragmentSearchBinding
import com.example.movienow.ui.home.MovieAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        searchAdapter = MovieAdapter(emptyList()) { movie ->
            navigateToDetail(movie)
        }
        binding.searchResultsRecyclerView.adapter = searchAdapter
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            viewModel.onSearchQueryChanged(editable.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            searchAdapter.updateData(movies)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            // Ocultar el teclado cuando la carga termina
            if (!isLoading) {
                hideKeyboard()
            }
        }

        viewModel.showNoResults.observe(viewLifecycleOwner) { show ->
            binding.noResultsTextView.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun navigateToDetail(movie: Movie) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }

    // --- Función para ocultar el teclado ---
    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}