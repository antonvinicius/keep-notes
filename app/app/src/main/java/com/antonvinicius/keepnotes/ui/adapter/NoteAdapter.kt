package com.antonvinicius.keepnotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonvinicius.keepnotes.databinding.ItemPostBinding
import com.antonvinicius.keepnotes.model.NoteDto

class NoteAdapter(
    private val onItemClicked: (NoteDto) -> Unit
) : RecyclerView.Adapter<NoteAdapter.PostAdapterViewHolder>() {
    private val notes = mutableListOf<NoteDto>()

    inner class PostAdapterViewHolder(
        private val binding: ItemPostBinding, private val onItemClicked: (NoteDto) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteDto) {
            binding.title.text = note.title
            binding.content.text = note.content

            binding.root.setOnClickListener {
                onItemClicked(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        return PostAdapterViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClicked
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    fun fillList(noteDtoList: List<NoteDto>) {
        notifyItemRangeRemoved(0, notes.size)
        notes.clear()
        notes.addAll(noteDtoList)
        notifyItemRangeInserted(0, notes.size)
    }
}