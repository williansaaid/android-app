package com.example.movienow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movienow.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var popularAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.mainToolbar.toolbar)

        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Initialize adapters with empty lists
        popularAdapter = MovieAdapter(emptyList())
        topRatedAdapter = MovieAdapter(emptyList())

        // Set adapters to RecyclerViews
        binding.popularMoviesRecyclerView.adapter = popularAdapter
        binding.topRatedMoviesRecyclerView.adapter = topRatedAdapter
    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            popularAdapter.updateData(movies)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            topRatedAdapter.updateData(movies)
        }

        // Here we could observe the isLoading LiveData to show a progress bar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}