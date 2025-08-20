package com.example.movienow.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.data.model.ImageFile
import com.example.movienow.databinding.ItemGalleryImageBinding
import com.example.movienow.util.Constants

class GalleryAdapter(private val imageList: List<ImageFile>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageFile: ImageFile) {
            val imageUrl = Constants.IMAGE_BASE_URL + imageFile.filePath
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.galleryImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGalleryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}