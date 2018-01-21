package com.rcgreed.easymoney

import android.app.Activity
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.rcgreed.easymoney.fragment.CalendarFragment
import kotlinx.android.synthetic.main.activity_transaction_entry.*
import java.util.*

class TransactionEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_entry)
        transaction_entry_ok.setOnClickListener(this::okButton)
        transaction_entry_pick_date.setOnClickListener(this::pickDate)
    }
    private fun okButton(v : View){

        setResult(Activity.RESULT_OK)
        finish()
    }
    private fun pickDate(v :View){
        val fgm=CalendarFragment()
        fgm.listener=DatePickerDialog.OnDateSetListener{ _, year: Int, month: Int, day: Int ->

            //v as Button?.text =
            (v as Button).text = String.format(Locale.getDefault(),"%d-%02d-%02d",year,month+1,day)
        }
        fgm.show(fragmentManager,null)
    }
}
