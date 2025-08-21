package com.example.movienow.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movienow.data.model.Movie
import com.example.movienow.databinding.FragmentDetailBinding
import com.example.movienow.util.Constants
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Locale

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        populateInitialUI(args.movie) // Mostramos los datos básicos al instante
        observeViewModel()
        viewModel.fetchMovieDetails(args.movie.id) // Pedimos los datos completos
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.detailToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.detailToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerContainer.startShimmer()
                binding.shimmerContainer.isVisible = true
                binding.realContentContainer.isVisible = false
            } else {
                binding.shimmerContainer.stopShimmer()
                binding.shimmerContainer.isVisible = false
                binding.realContentContainer.isVisible = true
            }
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) { movie ->
            populateFullUI(movie)
        }

        viewModel.trailerKey.observe(viewLifecycleOwner) { key ->
            binding.trailerButton.isVisible = key != null
            binding.trailerButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$key"))
                startActivity(intent)
            }
        }

        viewModel.director.observe(viewLifecycleOwner) { directorName ->
            binding.directorTitleTextView.isVisible = directorName != null
            binding.directorNameTextView.isVisible = directorName != null
            binding.directorNameTextView.text = directorName
        }

        viewModel.cast.observe(viewLifecycleOwner) { castList ->
            binding.castTitleTextView.isVisible = castList.isNotEmpty()
            binding.castRecyclerView.isVisible = castList.isNotEmpty()
            binding.castRecyclerView.adapter = CastAdapter(castList)
        }

        viewModel.images.observe(viewLifecycleOwner) { imageList ->
            binding.galleryTitleTextView.isVisible = imageList.isNotEmpty()
            binding.galleryRecyclerView.isVisible = imageList.isNotEmpty()
            binding.galleryRecyclerView.adapter = GalleryAdapter(imageList)
        }
    }

    // Muestra los datos que ya se tienen (póster y título)
    private fun populateInitialUI(movie: Movie) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = movie.title
        Glide.with(this).load(Constants.IMAGE_BASE_URL + movie.posterPath).into(binding.posterImageView)
        binding.titleTextView.text = movie.title
    }

    // Muestra los datos completos cuando llegan de la API
    private fun populateFullUI(movie: Movie) {
        binding.taglineTextView.text = if (movie.tagline.isNullOrBlank()) "" else "\"${movie.tagline}\""
        binding.taglineTextView.isVisible = !movie.tagline.isNullOrBlank()
        binding.overviewTextView.text = movie.overview
        binding.ratingTextView.text = String.format("%.1f", movie.voteAverage)
        binding.releaseDateTextView.text = "• ${formatDate(movie.releaseDate)}"
        binding.runtimeTextView.text = if (movie.runtime != null) "• ${formatRuntime(movie.runtime)}" else ""

        if (!movie.genres.isNullOrEmpty()) {
            binding.genresChipGroup.removeAllViews()
            movie.genres.forEach { genre ->
                val chip = Chip(requireContext())
                chip.text = genre.name
                binding.genresChipGroup.addView(chip)
            }
        }
        binding.genresTitleTextView.isVisible = !movie.genres.isNullOrEmpty()
    }

    private fun formatRuntime(minutes: Int?): String {
        if (minutes == null || minutes <= 0) return ""
        val hours = minutes / 60
        val mins = minutes % 60
        return "${hours}h ${mins}m"
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}