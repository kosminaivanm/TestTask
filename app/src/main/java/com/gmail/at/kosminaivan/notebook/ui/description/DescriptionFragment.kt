package com.gmail.at.kosminaivan.notebook.ui.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmail.at.kosminaivan.notebook.App
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.databinding.FragmentDescriptionBinding
import com.gmail.at.kosminaivan.notebook.model.CardService
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private val cardService: CardService
        get() = (requireContext().applicationContext as App).cardService

    private lateinit var binding: FragmentDescriptionBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentDescriptionBinding.bind(view)

        val note = cardService.getNoteById(requireArguments().getLong(ARG_ID))

        val formatTime = DateTimeFormatter.ofPattern("HH:mm")
        val formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.title.text = note.title

        binding.dateStart.text = note.dateStart.toInstant().atZone(ZoneId.systemDefault()).format(formatDate)
        binding.timeStart.text = note.dateStart.toInstant().atZone(ZoneId.systemDefault()).format(formatTime)

        binding.dateEnd.text   = note.dateFinish.toInstant().atZone(ZoneId.systemDefault()).format(formatDate)
        binding.timeEnd.text = note.dateFinish.toInstant().atZone(ZoneId.systemDefault()).format(formatTime)

        binding.description.text = note.description
    }

    companion object{
        const val ARG_ID = "name"
    }

}