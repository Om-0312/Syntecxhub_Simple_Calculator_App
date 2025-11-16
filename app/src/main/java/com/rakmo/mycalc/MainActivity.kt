package com.rakmo.mycalc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var previousCalculationTextView: TextView

    private var firstNumber = 0.0
    private var operation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        resultTextView = findViewById(R.id.resultTextView)
        previousCalculationTextView = findViewById(R.id.previousCalculationTextView)



        val button0:Button=findViewById<Button>(R.id.btn0)
        val button1:Button=findViewById<Button>(R.id.btn1)
        val button2:Button=findViewById<Button>(R.id.btn2)
        val button3:Button=findViewById<Button>(R.id.btn3)
        val button4:Button=findViewById<Button>(R.id.btn4)
        val button5:Button=findViewById<Button>(R.id.btn5)
        val button6:Button=findViewById<Button>(R.id.btn6)
        val button7:Button=findViewById<Button>(R.id.btn7)
        val button8:Button=findViewById<Button>(R.id.btn8)
        val button9:Button=findViewById<Button>(R.id.btn9)

        val addButton:Button=findViewById<Button>(R.id.btnAdd)
        val subButton:Button=findViewById<Button>(R.id.btnSub)
        val mulButton:Button=findViewById<Button>(R.id.btnMultiply)
        val divButton:Button=findViewById<Button>(R.id.btnDivide)

        val equalButton:Button=findViewById<Button>(R.id.btnEqual)
        val clearButton:Button=findViewById<Button>(R.id.btnClear)
        val backspaceButton:Button=findViewById<Button>(R.id.btnBackspace)


        val buttonPoint:Button=findViewById<Button>(R.id.btnPoint)
        val buttonPercent:Button=findViewById<Button>(R.id.btnPercent)

        //number buttons
        button0.setOnClickListener{appendNumber(number = "0")}
        button1.setOnClickListener{appendNumber(number = "1")}
        button2.setOnClickListener{appendNumber(number = "2")}
        button3.setOnClickListener{appendNumber(number = "3")}
        button4.setOnClickListener{appendNumber(number = "4")}
        button5.setOnClickListener{appendNumber(number = "5")}
        button6.setOnClickListener{appendNumber(number = "6")}
        button7.setOnClickListener{appendNumber(number = "7")}
        button8.setOnClickListener{appendNumber(number = "8")}
        button9.setOnClickListener{appendNumber(number = "9")}
        buttonPoint.setOnClickListener{appendNumber(number = ".")}

        buttonPercent.setOnClickListener{appendNumber(number = "%")}

        //operator buttons
        addButton.setOnClickListener{setOperation("+")}
        subButton.setOnClickListener{setOperation("-")}
        mulButton.setOnClickListener{setOperation("*")}
        divButton.setOnClickListener{setOperation("/")}

        equalButton.setOnClickListener{ calculateResult() }
        clearButton.setOnClickListener{ clearCalculator() }
        backspaceButton.setOnClickListener{ deleteNumber() }
    }

    private fun deleteNumber() {
        if (resultTextView.text.isNotEmpty() && resultTextView.text != "0.0" && resultTextView.text != "Error") {
            resultTextView.text = "0"
            resultTextView.text = resultTextView.text.toString().dropLast(1)
        }else {
            Toast.makeText(this, "Invalid Choice", Toast.LENGTH_SHORT).show()
        }
    }
    private fun calculateResult() {

        try {
            val secondNumber: Double = resultTextView.text.toString().toDouble()
            val result: Double = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> firstNumber / secondNumber
                else -> secondNumber
            }
            previousCalculationTextView.text = "$firstNumber $operation $secondNumber ="
            resultTextView.text = result.toString()
            isNewOperation = true

        } catch (e: Exception) {
            resultTextView.text = "Error"
            return
        }
    }

    private fun clearCalculator() {
        resultTextView.text = "0"
        previousCalculationTextView.text = ""
        firstNumber = 0.0
        operation = ""
        isNewOperation = true
        resultTextView.text = "0.0"
        previousCalculationTextView.text = ""
    }


    private fun setOperation(operator: String) {
        firstNumber = resultTextView.text.toString().toDouble()
        operation = operator
        isNewOperation = true
        previousCalculationTextView.text = "$firstNumber $operation"
    }
    private fun appendNumber(number: String) {
        if(isNewOperation){
            resultTextView.text = number
            isNewOperation = false
        } else {
            resultTextView.text="${resultTextView.text}$number"
        }

    }
}