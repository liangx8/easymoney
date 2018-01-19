package com.rcgreed.easymoney.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import java.util.*

/**
 * Created by arm on 18-1-19.
 */
class CalendarFragment :DialogFragment() {
    private val wrapperListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        listener?.onDateSet(datePicker, year, month, day)
    }
    var listener : DatePickerDialog.OnDateSetListener?=null
    /*
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Toast.makeText(activity,"change",Toast.LENGTH_LONG).show()
    }
    */

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val ymd: IntArray=
        if (arguments != null && arguments.containsKey(ARGS_YMD)) {
            arguments.getIntArray(ARGS_YMD)
        } else {
            val ymd = IntArray(3)
            val calendar = Calendar.getInstance()
            ymd[0] = calendar.get(Calendar.YEAR)
            ymd[1] = calendar.get(Calendar.MONTH)
            ymd[2] = calendar.get(Calendar.DAY_OF_MONTH)
            ymd
        }

        return DatePickerDialog(activity, wrapperListener,ymd[0],ymd[1],ymd[2])
    }
}
const val ARGS_YMD = "YMD"