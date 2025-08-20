package com.example.movienow.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.R
import com.example.movienow.data.model.Cast
import com.example.movienow.databinding.ItemCastBinding
import com.example.movienow.util.Constants

class CastAdapter(private val castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(castMember: Cast) {
            binding.actorNameTextView.text = castMember.name
            binding.characterNameTextView.text = castMember.character

            val imageUrl = Constants.IMAGE_BASE_URL + castMember.profilePath
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(binding.actorPhotoImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }
}