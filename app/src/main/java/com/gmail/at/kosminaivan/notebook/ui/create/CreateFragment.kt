package com.gmail.at.kosminaivan.notebook.ui.create

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gmail.at.kosminaivan.notebook.App
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.databinding.FragmentCreateBinding
import com.gmail.at.kosminaivan.notebook.model.CardService
import java.sql.Timestamp
import java.util.*

@SuppressLint("SetTextI18n")
class CreateFragment : Fragment(R.layout.fragment_create) {

    private val cardService: CardService
        get() = (requireContext().applicationContext as App).cardService

    private lateinit var binding: FragmentCreateBinding

    private lateinit var calendarStart: Calendar
    private lateinit var calendarEnd: Calendar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)
        binding.button.setOnClickListener(createNote())

        calendarStart = Calendar.getInstance().clearMinutesSeconds()
        calendarEnd = Calendar.getInstance().apply {
            timeInMillis = calendarStart.timeInMillis
            add(Calendar.HOUR_OF_DAY, 1)
        }

        binding.dateStart.apply {
            text = "${format(calendarStart.get(Calendar.DATE))}.${format(calendarStart.get(Calendar.MONTH) + 1)}.${calendarStart.get(Calendar.YEAR)}"
            setOnClickListener { openDatePicker(binding.dateStart, calendarStart) }
        }
        binding.dateEnd.apply {
            text = "${format(calendarEnd.get(Calendar.DATE))}.${format(calendarEnd.get(Calendar.MONTH) + 1)}.${calendarEnd.get(Calendar.YEAR)}"
            setOnClickListener { openDatePicker(binding.dateEnd, calendarEnd) }
        }
        binding.timeStart.apply {
            text = "${format(calendarStart.get(Calendar.HOUR_OF_DAY))}:${format(calendarStart.get(Calendar.MINUTE))}"
            setOnClickListener { openTimePicker(binding.timeStart, calendarStart) }
        }
        binding.timeEnd.apply {
            text = "${format(calendarEnd.get(Calendar.HOUR_OF_DAY))}:${format(calendarEnd.get(Calendar.MINUTE))}"
            setOnClickListener { openTimePicker(binding.timeEnd, calendarEnd) }
        }

    }

    private fun createNote(): (v: View) -> Unit = { _ ->
        val startTimestamp = Timestamp(calendarStart.timeInMillis)
        val endTimestamp = Timestamp(calendarEnd.timeInMillis)

        if (startTimestamp > endTimestamp) {
            Toast.makeText(context, "Finish date cant before start date", Toast.LENGTH_SHORT).show()
        } else {
            cardService.createNoteForTimeStampRange(
                startTimestamp,
                endTimestamp,
                binding.title.editText?.text.toString(),
                binding.description.editText?.text.toString()
            )
            findNavController().popBackStack()
        }
    }

    private fun openDatePicker(btn: Button, cld: Calendar) {

        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, date ->
                val dateFormat = format(date)
                val monthFormat = format(month)
                btn.text = "$dateFormat.$monthFormat.$year"
                cld.set(Calendar.DATE, date)
                cld.set(Calendar.MONTH, month - 1)
                cld.set(Calendar.YEAR, year)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


    private fun openTimePicker(btn: Button, cld: Calendar) {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                val hourFormat = format(hour)
                val minuteFormat = format(minute)
                btn.text = "$hourFormat:$minuteFormat"
                cld.set(Calendar.HOUR_OF_DAY, hour)
                cld.set(Calendar.MINUTE, minute)
            }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true
        )
        timePickerDialog.show()
    }

    private fun format(date: Int) = if (date < 10) "0$date" else "$date"

    private fun Calendar.clearMinutesSeconds() : Calendar
    {
        this.set(Calendar.MILLISECOND, 0)
        this.set(Calendar.SECOND, 0)
        return this
    }

}