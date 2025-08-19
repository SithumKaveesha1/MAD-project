package com.example.quickloan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quickloan.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class Bu_Calculator : AppCompatActivity() {

    private lateinit var editTextLoanAmount: EditText
    private lateinit var editTextInterestRate: EditText
    private lateinit var editTextLoanTerm: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewMonthlyPayment: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bu_calculator)

        editTextLoanAmount = findViewById(R.id.loan_amount_edit_text)
        editTextInterestRate = findViewById(R.id.interest_rate_edit_text)
        editTextLoanTerm = findViewById(R.id.loan_term_edit_text)
        buttonCalculate = findViewById(R.id.calculate_button)
        textViewMonthlyPayment = findViewById(R.id.monthly_payment_result_text_view)

        buttonCalculate.setOnClickListener {
            calculateLoan()
        }
    }

    private fun calculateLoan() {
        val loanAmount = editTextLoanAmount.text.toString().toDouble()
        val interestRate = editTextInterestRate.text.toString().toDouble()
        val loanTerm = editTextLoanTerm.text.toString().toInt()

        val monthlyInterestRate = interestRate / 100 / 12
        val numPayments = loanTerm * 12

        val numerator = loanAmount * monthlyInterestRate * Math.pow((1 + monthlyInterestRate), numPayments.toDouble())
        val denominator = Math.pow((1 + monthlyInterestRate), numPayments.toDouble()) - 1
        val monthlyPayment = numerator / denominator

        textViewMonthlyPayment.text = String.format("%.2f", monthlyPayment)
    }
}