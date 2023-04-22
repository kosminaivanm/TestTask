package com.gmail.at.kosminaivan.notebook.ui.main.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.databinding.ItemTimeCardNoteBinding
import com.gmail.at.kosminaivan.notebook.model.Note
import com.gmail.at.kosminaivan.notebook.ui.description.DescriptionFragment

class NoteAdapter(val navController: NavController) : RecyclerView.Adapter<NoteViewHolder>(), View.OnClickListener {

    private lateinit var binding:ItemTimeCardNoteBinding

    var notes: List<Note> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemTimeCardNoteBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)


        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.itemView.tag = note.id
        holder.binding.apply {
            title.text = note.title
            timeStart.text = note.dateStart.toString()
            timeEnd.text = note.dateFinish.toString()
            }
        }


    override fun onClick(v: View) {

        navController.navigate(R.id.action_mainFragment_to_descriptionFragment,
        bundleOf(
            DescriptionFragment.ARG_ID to (v.tag as Long)
            )
        )

        Toast.makeText(
            v.context,"Я вроде нажал ${binding.title.text} ${binding.timeStart.text}" +
                    " ${binding.timeEnd.text}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

class NoteViewHolder(
    val binding: ItemTimeCardNoteBinding
) : RecyclerView.ViewHolder(binding.root)