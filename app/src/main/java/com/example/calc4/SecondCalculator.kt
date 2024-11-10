package com.example.calc4

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class SecondCalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.second_calculator)

        val go_back: Button = findViewById(R.id.back_button)
        go_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Отримання елементів
        val UCN: EditText = findViewById(R.id.ucn)
        val SK: EditText = findViewById(R.id.sk)
        val UK_PER: EditText = findViewById(R.id.uk_per)
        val S_NOM: EditText = findViewById(R.id.s_mom)
        val calculate1: Button = findViewById(R.id.submit_button)
        val error1: TextView = findViewById(R.id.error1)
        val result1: TextView = findViewById(R.id.result_1)
        val result2: TextView = findViewById(R.id.result_2)
        val result3: TextView = findViewById(R.id.result_3)
        val result4: TextView = findViewById(R.id.result_4)

        calculate1.setOnClickListener {
            val ucn = UCN.text.toString()
            val sk = SK.text.toString()
            val uk_per = UK_PER.text.toString()
            val s_mom = S_NOM.text.toString()

            var errorMessage = ""

            // Функція, що повертає число, якщо воно коректне, або null з повідомленням про помилку
            fun checkAndConvert(value: String, fieldName: String): Double? {
                return if (value.isEmpty()) {

                    errorMessage += "$fieldName is empty.\n "
                    null
                } else {
                    try {
                        value.toDouble()
                    } catch (e: NumberFormatException) {
                        errorMessage += "$fieldName is not a valid number.\n "
                        null
                    }
                }
            }

            // Перевірка і конвертація значень
            val ucnVal = checkAndConvert(ucn, "UNOM")
            val skVal = checkAndConvert(sk, "SM")
            val uk_perVal = checkAndConvert(uk_per, "TM")
            val s_momVal = checkAndConvert(s_mom, "TM")

            if (errorMessage.isNotEmpty()) {
                // Відображення помилок, якщо вони є
                error1.text = errorMessage
            } else {
                var xc = ((ucnVal!! * ucnVal / skVal!!) * 100) / 100.0
                var xt = (uk_perVal!! / 100) * (ucnVal * ucnVal / s_momVal!!)
                var xe = xc + xt
                var ip =ucnVal / (sqrt(3.0) * xe)
                // Виконуємо обчислення тільки якщо tmVal не є null
                result1.text = """${"%.2f".format(xc)}"""
                result2.text = """${"%.2f".format(xt)}"""
                result3.text = """${"%.2f".format(xe)}"""
                result4.text = """${"%.2f".format(ip)}"""

            }
        }
    }
}

