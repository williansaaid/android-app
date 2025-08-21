package com.example.movienow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.MainViewModel
import com.example.movienow.data.model.Movie
import com.example.movienow.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var popularAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var nowPlayingAdapter: MovieAdapter

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
        (activity as? AppCompatActivity)?.supportActionBar?.title = ""

        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        popularAdapter = MovieAdapter(mutableListOf()) { movie -> navigateToDetail(movie) }
        topRatedAdapter = MovieAdapter(mutableListOf()) { movie -> navigateToDetail(movie) }
        nowPlayingAdapter = MovieAdapter(mutableListOf()) { movie -> navigateToDetail(movie) }

        binding.popularMoviesRecyclerView.adapter = popularAdapter
        binding.topRatedMoviesRecyclerView.adapter = topRatedAdapter
        binding.nowPlayingMoviesRecyclerView.adapter = nowPlayingAdapter

        addInfiniteScrollListener(binding.popularMoviesRecyclerView) { viewModel.loadMorePopularMovies() }
        addInfiniteScrollListener(binding.topRatedMoviesRecyclerView) { viewModel.loadMoreTopRatedMovies() }
        addInfiniteScrollListener(binding.nowPlayingMoviesRecyclerView) { viewModel.loadMoreNowPlayingMovies() }
    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            popularAdapter.updateData(movies)
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            topRatedAdapter.updateData(movies)
        }
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { movies ->
            nowPlayingAdapter.updateData(movies)
        }
    }

    private fun addInfiniteScrollListener(recyclerView: RecyclerView, loadMore: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMore()
                }
            }
        })
    }

    private fun navigateToDetail(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}