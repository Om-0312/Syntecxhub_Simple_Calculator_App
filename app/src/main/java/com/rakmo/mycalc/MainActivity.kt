package com.rakmo.mycalc

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController



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

        //Call the function to hide UI
        hideSystemUI()
        resultTextView = findViewById(R.id.resultTextView)
        previousCalculationTextView = findViewById(R.id.previousCalculationTextView)


        val btnRotate = findViewById<ImageButton>(R.id.btnRotate)

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

        buttonPercent.setOnClickListener {
            if (resultTextView.text.toString()
                    .isNotEmpty() && resultTextView.text.toString() != "Error"
            ) {
                try {
                    var number = resultTextView.text.toString().toDouble()
                    number = number / 100
                    resultTextView.text = number.toString()
                    isNewOperation = true
                } catch (e: Exception) {
                    resultTextView.text = "Error"
                }
            }
        }

        //operator buttons
        addButton.setOnClickListener{setOperation("+")}
        subButton.setOnClickListener{setOperation("-")}
        mulButton.setOnClickListener{setOperation("*")}
        divButton.setOnClickListener{setOperation("/")}

        equalButton.setOnClickListener{ calculateResult() }
        clearButton.setOnClickListener{ clearCalculator() }
        backspaceButton.setOnClickListener{ deleteNumber() }

        btnRotate.setOnClickListener {
            val currentOrientation = resources.configuration.orientation

            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }

    private fun deleteNumber() {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty() && currentText != "Error") {
            // Drop the last character
            resultTextView.text = currentText.dropLast(1)

            // If screen is empty after deleting, show "0"
            if (resultTextView.text.isEmpty()) {
                resultTextView.text = "0"
            }
        } else {
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

            val finalResultText = if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }

            val firstNumText = if (firstNumber % 1.0 == 0.0) firstNumber.toInt().toString() else firstNumber.toString()
            val secondNumText = if (secondNumber % 1.0 == 0.0) secondNumber.toInt().toString() else secondNumber.toString()

            previousCalculationTextView.text = "$firstNumText $operation $secondNumText ="
            resultTextView.text = finalResultText
            isNewOperation = true

        } catch (e: Exception) {
            resultTextView.text = "Error"
            return
        }
    }



    private fun clearCalculator() {
        firstNumber = 0.0
        operation = ""
        isNewOperation = true
        resultTextView.text = "0"
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

    // 1. Save the variables before the activity is destroyed
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("FIRST_NUMBER", firstNumber)
        outState.putString("OPERATION", operation)
        outState.putBoolean("IS_NEW_OP", isNewOperation)
        outState.putString("RESULT_TEXT", resultTextView.text.toString())
        outState.putString("PREV_TEXT", previousCalculationTextView.text.toString())
    }

    // 2. Restore the variables after the activity is recreated
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        firstNumber = savedInstanceState.getDouble("FIRST_NUMBER")
        operation = savedInstanceState.getString("OPERATION") ?: ""
        isNewOperation = savedInstanceState.getBoolean("IS_NEW_OP")
        resultTextView.text = savedInstanceState.getString("RESULT_TEXT")
        previousCalculationTextView.text = savedInstanceState.getString("PREV_TEXT")
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // New Android 11+ (API 30+) Method
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Old Android 10 and below Method
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }

}