package com.gmail.at.kosminaivan.notebook.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.at.kosminaivan.notebook.app.App
import com.gmail.at.kosminaivan.notebook.databinding.FragmentMainBinding
import com.gmail.at.kosminaivan.notebook.model.CardListener
import com.gmail.at.kosminaivan.notebook.model.CardService
import com.gmail.at.kosminaivan.notebook.ui.main.recycler_view.CardAdapter

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val adapter = CardAdapter()

    private val cardService: CardService
        get() = (requireContext().applicationContext as App).cardService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        cardService.addListener(cardListener)

        val layoutManager = LinearLayoutManager(context)
        binding.idRV.adapter = adapter
        binding.idRV.layoutManager = layoutManager
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        cardService.removeListener(cardListener)
    }

    private val cardListener: CardListener = {
        adapter.cards = it
    }
}