package com.example.jccv_1.secondary
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*
class DatePickerHasta(private val button: Button, private val minDate: Long? = null) :

    DatePickerDialog.OnDateSetListener {
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    init {
        button.setOnClickListener { showDatePickerDialog() }
    }
    fun getDate(): Date? {
        val dateString = button.text.toString()
        return try {
            dateFormatter.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        button.text = dateFormatter.format(calendar.time)
    }
    private fun showDatePickerDialog() {
        val currentDate = getDate() ?: Calendar.getInstance().time
        val calendar = Calendar.getInstance().apply {
            time = currentDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val dialog = DatePickerDialog(
            button.context,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        minDate?.let {
            dialog.datePicker.minDate = it
        }
        // Limitar la fecha máxima permitida para selección en el DatePicker a hoy
        dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dialog.show()
    }
    fun setMinDate(date: Date?) {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        minDate?.let {
            if (calendar.timeInMillis > it) {
                return
            }
        }
        showDatePickerDialog(calendar.timeInMillis)
    }
    private fun showDatePickerDialog(minDate: Long) {
        val dialog = DatePickerDialog(
            button.context,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        dialog.datePicker.minDate = minDate
        // Limitar la fecha máxima permitida para selección en el DatePicker a hoy
        dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dialog.show()
    }
}
