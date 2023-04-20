package com.gmail.at.kosminaivan.notebook.ui.main.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.at.kosminaivan.notebook.databinding.ItemTimeCardBinding
import com.gmail.at.kosminaivan.notebook.databinding.ItemTimeCardNoteBinding
import com.gmail.at.kosminaivan.notebook.model.Card
import com.gmail.at.kosminaivan.notebook.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>(), View.OnClickListener {

    var notes: List<Note> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeCardNoteBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)


        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            title.text = note.title
            timeStart.text = note.dateStart
            timeEnd.text = note.dateFinish

        }
    }

    override fun onClick(v: View?) {
    }
}

class NoteViewHolder(
    val binding: ItemTimeCardNoteBinding
) : RecyclerView.ViewHolder(binding.root)