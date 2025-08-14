package com.example.movienow.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movienow.databinding.FragmentDetailBinding
import com.example.movienow.util.Constants
import com.google.android.material.chip.Chip

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
        populateUI()
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.detailToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "" // Dejamos el título de la toolbar vacío
        binding.detailToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun populateUI() {
        val movie = args.movie

        // Cargar imágenes
        Glide.with(this)
            .load(Constants.IMAGE_BASE_URL + movie.backdropPath)
            .into(binding.backdropImageView)

        Glide.with(this)
            .load(Constants.IMAGE_BASE_URL + movie.posterPath)
            .into(binding.posterImageView)

        // Establecer textos
        binding.titleTextView.text = movie.title
        binding.overviewTextView.text = movie.overview
        binding.ratingTextView.text = "${String.format("%.1f", movie.voteAverage)} / 10"

        // Crear chips para los géneros
        binding.genresChipGroup.removeAllViews()
        movie.genres?.forEach { genre ->
            val chip = Chip(requireContext())
            chip.text = genre.name
            binding.genresChipGroup.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}