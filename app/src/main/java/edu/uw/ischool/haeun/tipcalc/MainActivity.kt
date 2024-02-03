package edu.uw.ischool.haeun.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.text.DecimalFormat
import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountEditText: EditText = findViewById(R.id.amountEditText)
        val tipButton: Button = findViewById(R.id.tipButton)
        val tipSpinner: Spinner = findViewById(R.id.spinner)

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Enable the tip button if the text is not empty
                tipButton.isEnabled = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {
                // Not used
            }
        })

        ArrayAdapter.createFromResource(
            this,
            R.array.tip_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tipSpinner.adapter = adapter
        }

        tipButton.setOnClickListener {
            val amountText = amountEditText.text.toString()
            if (amountText.isNotEmpty()) {
                val amount = amountText.toDouble()
                val selectedTipPercentage = when (tipSpinner.selectedItem.toString()) {
                    "10%" -> 0.1
                    "15%" -> 0.15
                    "18%" -> 0.18
                    "20%" -> 0.2
                    else -> 0.15
                }
                val tip = calculateTip(amount, selectedTipPercentage)
                val formattedTip = DecimalFormat("#.##").format(tip)
                Toast.makeText(this, "$$formattedTip", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun calculateTip(amount: Double, tipPercentage: Double): Double {
        return amount * tipPercentage
    }
}