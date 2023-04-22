package com.gmail.at.kosminaivan.notebook.ui.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmail.at.kosminaivan.notebook.App
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.databinding.FragmentDescriptionBinding
import com.gmail.at.kosminaivan.notebook.model.CardService

class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private val cardService: CardService
        get() = (requireContext().applicationContext as App).cardService

    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var viewModel: DescriptionViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentDescriptionBinding.bind(view)

        val note = cardService.getNoteById(requireArguments().getLong(ARG_ID))


        binding.extraTitle.text = note.title
        binding.extraStart.text = note.dateStart.toString()
        binding.extraEnd.text = note.dateFinish.toString()
        binding.extraDesc.text = note.description
    }

    companion object{
        const val ARG_ID = "name"
    }

}