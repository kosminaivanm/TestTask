package com.gmail.at.kosminaivan.notebook.ui.main.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.at.kosminaivan.notebook.databinding.ItemTimeCardBinding
import com.gmail.at.kosminaivan.notebook.model.Card

class CardAdapter() : RecyclerView.Adapter<CardsViewHolder>() {

    var cards: List<Card> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeCardBinding.inflate(inflater, parent, false)

        binding.hourRecycler.adapter = NoteAdapter()
        binding.hourRecycler.layoutManager = LinearLayoutManager(parent.context)

        return CardsViewHolder(binding)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = cards[position]

        holder.binding.apply {
            time.text = card.time
            (hourRecycler.adapter as NoteAdapter).notes = card.notes
        }
    }

}

class CardsViewHolder(
    val binding: ItemTimeCardBinding
) : RecyclerView.ViewHolder(binding.root)
