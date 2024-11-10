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

class FirstCalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.first_calculator)

        // Налаштування Spinner
        val spinner1 = findViewById<Spinner>(R.id.selection_1)
        val adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options_1, android.R.layout.simple_spinner_item
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        val spinner2 = findViewById<Spinner>(R.id.selection_2)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options_2, android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        val go_back: Button = findViewById(R.id.back_button)
        go_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Отримання елементів
        val UNOM: EditText = findViewById(R.id.unom)
        val IK: EditText = findViewById(R.id.ik)
        val TF: EditText = findViewById(R.id.tf)
        val POWER_TP: EditText = findViewById(R.id.power_tp)
        val SM: EditText = findViewById(R.id.sm)
        val TM: EditText = findViewById(R.id.tm)
        val CT: EditText = findViewById(R.id.ct)
        val calculate1: Button = findViewById(R.id.submit_button)
        val calculate2: Button = findViewById(R.id.second_submit_button)
        val error1: TextView = findViewById(R.id.error1)
        val error2: TextView = findViewById(R.id.error2)
        val result1: TextView = findViewById(R.id.result_1)
        val result2: TextView = findViewById(R.id.result_2)
        val result3: TextView = findViewById(R.id.result_3)
        val resultsecond: TextView = findViewById(R.id.second_result)

        // Результати на основі вибору Spinner та діапазону
        val resultMap = mapOf(
            "Неізольовані проводи та шини-Мідні" to listOf(
                1000.0..3000.0 to 2.5,
                3001.0..5000.0 to 2.1,
                5001.0..Double.MAX_VALUE to 1.8
            ),
            "Неізольовані проводи та шини-Алюмінієві" to listOf(
                1000.0..3000.0 to 1.3,
                3001.0..5000.0 to 1.1,
                5001.0..Double.MAX_VALUE to 1.0
            ),
            "Кабелі з паперовою і проводи з гумовою та полівініхлоридною ізоляцією з жилами-Мідні" to listOf(
                1000.0..3000.0 to 3.0,
                3001.0..5000.0 to 2.5,
                5001.0..Double.MAX_VALUE to 2.0
            ),
            "Кабелі з паперовою і проводи з гумовою та полівініхлоридною ізоляцією з жилами-Алюмінієві" to listOf(
                1000.0..3000.0 to 1.6,
                3001.0..5000.0 to 1.4,
                5001.0..Double.MAX_VALUE to 1.2
            ),
            "Кабелі з гумовою та пластиковою ізоляцією з жилами-Мідні" to listOf(
                1000.0..3000.0 to 3.5,
                3001.0..5000.0 to 3.1,
                5001.0..Double.MAX_VALUE to 2.7
            ),
            "Кабелі з гумовою та пластиковою ізоляцією з жилами-Алюмінієві" to listOf(
                1000.0..3000.0 to 1.9,
                3001.0..5000.0 to 1.7,
                5001.0..Double.MAX_VALUE to 1.6
            )
        )

        calculate1.setOnClickListener {
            val unom = UNOM.text.toString()
            val sm = SM.text.toString()
            val tm = TM.text.toString()

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
            val unomVal = checkAndConvert(unom, "UNOM")
            val smVal = checkAndConvert(sm, "SM")
            val tmVal = checkAndConvert(tm, "TM")
            val spinner1Value = spinner1.selectedItem.toString()
            val spinner2Value = spinner2.selectedItem.toString()

            if (errorMessage.isNotEmpty()) {
                // Відображення помилок, якщо вони є
                error1.text = errorMessage
            } else {
                // Виконуємо обчислення тільки якщо tmVal не є null
                if (tmVal != null) {
                    val key = "$spinner1Value-$spinner2Value"

                    val result = resultMap[key]?.firstOrNull { (range, eyr) ->
                        // Перевіряємо, чи tmVal належить до range
                        tmVal in range
                    }?.second ?: 0.0

                    val im = (((smVal!! / 2.0) / (sqrt(3.0) * unomVal!!)) * 100) / 100.0
                    val impa = ((im * 2) * 100) / 100.0
                    val sek = ((im / result) * 100) / 100.0

                    result1.text = """${"%.2f".format(im)}"""
                    result2.text = """${"%.2f".format(impa)}"""
                    result3.text = """${"%.2f".format(sek)}"""
                } else {
                    error1.text = "TM value is not valid."
                }
            }
        }

        calculate2.setOnClickListener {
            val ik = IK.text.toString()
            val tf = TF.text.toString()
            val ct = CT.text.toString()

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
            val ikVal = checkAndConvert(ik, "LK")
            val tfVal = checkAndConvert(tf, "TF")
            val ctVal = checkAndConvert(ct, "CT")

            if (errorMessage.isNotEmpty()) {
                // Відображення помилок, якщо вони є
                error1.text = errorMessage
            } else {
                var smin = ((ikVal!! * 1000 * sqrt(tfVal!!) / ctVal!!) * 100) / 100.0
                resultsecond.text =  """${"%.2f".format(smin)}"""

            }

        }
    }
}

