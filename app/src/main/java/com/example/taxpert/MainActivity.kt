package com.example.taxpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_PERCENTAGE = 20
class MainActivity : AppCompatActivity() {
    private lateinit var etInitialAmount: EditText
    private lateinit var seekBarTax: SeekBar
    private lateinit var tvTaxPercentLabel: TextView
    private lateinit var tvTaxAmount: TextView
    private lateinit var tvTotalAmount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInitialAmount = findViewById(R.id.etInitialAmount)
        seekBarTax = findViewById(R.id.seekBarTax)
        tvTaxPercentLabel = findViewById(R.id.tvTaxPercentLabel)
        tvTaxAmount = findViewById(R.id.tvTaxAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        seekBarTax.progress = INITIAL_PERCENTAGE
        seekBarTax.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            //Define this class inside here.
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG,"Progress: $p1")
                tvTaxPercentLabel.text = "$p1%"
                computeTax()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etInitialAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"Text: $p0")
                computeTax()
            }

        })
    }

    private fun computeTax() {
        //If initAmount is blank (not 0.00), then the app would crash. To fix that 	problem, we do:
        if(etInitialAmount.text.isEmpty()){
            tvTaxAmount.text = "0.00"
            tvTotalAmount.text = "0.00"
            return
        }

        //1. Declare and initialize our variables
        var initAmount = 0.00
        var taxPercent = 0
        var taxAmount = 0.00
        var totalAmount = 0.00

        //2. Get values from our initialAmount and taxPercentLabel.
        initAmount = etInitialAmount.text.toString().toDouble()
        taxPercent = seekBarTax.progress

        //3. Compute the tax and total amount
        taxAmount = initAmount * taxPercent / 100
        totalAmount = initAmount + taxAmount

        //4. Reflect changes in the UI
        tvTaxAmount.text = "%.2f".format(taxAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}