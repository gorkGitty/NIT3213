package com.example.myassssmentapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myassssmentapplication.databinding.ItemArtworkBinding

class EntityAdapter(
    private val onItemClick: (Map<String, Any>) -> Unit
) : ListAdapter<Map<String, Any>, EntityAdapter.EntityViewHolder>(EntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemArtworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EntityViewHolder(
        private val binding: ItemArtworkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(entity: Map<String, Any>) {
            // Get the first two fields from the map
            val fields = entity.entries.take(2).toList()
            
            // Display first field in title
            if (fields.isNotEmpty()) {
                binding.artworkTitle.text = fields[0].value.toString()
            }
            
            // Display second field in artist field
            if (fields.size > 1) {
                binding.artworkArtist.text = fields[1].value.toString()
            }

            // Hide unused TextViews
            binding.artworkMedium.visibility = android.view.View.GONE
            binding.artworkYear.visibility = android.view.View.GONE
        }
    }
}

class EntityDiffCallback : DiffUtil.ItemCallback<Map<String, Any>>() {
    override fun areItemsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        // Compare the first field as an identifier
        return oldItem.entries.firstOrNull()?.value == newItem.entries.firstOrNull()?.value
    }

    override fun areContentsTheSame(oldItem: Map<String, Any>, newItem: Map<String, Any>): Boolean {
        return oldItem == newItem
    }
} 