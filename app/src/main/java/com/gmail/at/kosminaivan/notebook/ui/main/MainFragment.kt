package com.gmail.at.kosminaivan.notebook.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.at.kosminaivan.notebook.App
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.databinding.FragmentMainBinding
import com.gmail.at.kosminaivan.notebook.model.CardListener
import com.gmail.at.kosminaivan.notebook.model.CardService
import com.gmail.at.kosminaivan.notebook.ui.main.recycler_view.CardAdapter
import java.sql.Timestamp
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: CardAdapter

    private val cardService: CardService
        get() = (requireContext().applicationContext as App).cardService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        adapter = CardAdapter(findNavController())
        adapter.cards = cardService.getCardsForDate(Timestamp(System.currentTimeMillis() - System.currentTimeMillis() % CardService.dayInMillis))

        binding.idRV.adapter = adapter
        binding.idRV.layoutManager = LinearLayoutManager(context)

        binding.datePicker.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val cal = GregorianCalendar(year, month, dayOfMonth)
            val millis = cal.timeInMillis
            cardService.currentCalendarTimestamp = Timestamp(millis)
        }

        binding.floatingActionButton.setOnClickListener { view ->
            findNavController().navigate(R.id.action_mainFragment_to_createFragment)
        }

        cardService.addListener(taskListener)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cardService.removeListener(taskListener)
    }

    private val taskListener: CardListener = { adapter.cards = it }

}