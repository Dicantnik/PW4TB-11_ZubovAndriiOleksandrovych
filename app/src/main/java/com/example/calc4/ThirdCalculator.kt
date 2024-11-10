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
import kotlin.math.pow
import kotlin.math.sqrt

class ThirdCalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.third_calculator)

        val go_back: Button = findViewById(R.id.back_button)
        go_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Отримання елементів
        val UK_MAX: EditText = findViewById(R.id.uk_max)
        val UV_N: EditText = findViewById(R.id.uv_n)
        val UN_N: EditText = findViewById(R.id.un_n)
        val SMOM_T: EditText = findViewById(R.id.smom_t)
        val RC_N: EditText = findViewById(R.id.rc_n)
        val RC_MIN: EditText = findViewById(R.id.rc_min)
        val XC_N: EditText = findViewById(R.id.xc_n)
        val XC_MIN: EditText = findViewById(R.id.xc_min)
        val L_I: EditText = findViewById(R.id.l_i)
        val R_O: EditText = findViewById(R.id.r_o)
        val X_O: EditText = findViewById(R.id.x_o)
        val calculate: Button = findViewById(R.id.submit_button)
        val result: TextView = findViewById(R.id.result)

        calculate.setOnClickListener {
            val uk_max = UK_MAX.text.toString()
            val uv_n = UV_N.text.toString()
            val un_n = UN_N.text.toString()
            val smom_t = SMOM_T.text.toString()
            val rc_n = RC_N.text.toString()
            val rc_min = RC_MIN.text.toString()
            val xc_n = XC_N.text.toString()
            val xc_min = XC_MIN.text.toString()
            val l_i = L_I.text.toString()
            val r_o = R_O.text.toString()
            val x_o = X_O.text.toString()
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
            val uk_maxVal = checkAndConvert(uk_max, "uk_max")
            val uv_nVal = checkAndConvert(uv_n, "uv_n")
            val un_nVal = checkAndConvert(un_n, "un_n")
            val smom_tVal = checkAndConvert(smom_t, "smom_t")
            val rc_nVal = checkAndConvert(rc_n, "rc_n")
            val rc_minVal = checkAndConvert(rc_min, "rc_min")
            val xc_nVal = checkAndConvert(xc_n, "xc_n")
            val xc_minVal = checkAndConvert(xc_min, "xc_min")
            val r_oVal = checkAndConvert(r_o, "r_o")
            val l_iVal = checkAndConvert(l_i, "l_i")
            val x_oVal = checkAndConvert(x_o, "x_o")

            if (errorMessage.isNotEmpty()) {
                // Відображення помилок, якщо вони є
                result.text = errorMessage
            } else {
                // first
                var xt = (uk_maxVal!! * (uv_nVal!! * uv_nVal)) / (100 * smom_tVal!!)
                var rsh = rc_nVal!!
                var xsh = xc_nVal!! + xt
                var zsh = sqrt(rsh * rsh + xsh * xsh)
                var rsh_min = rc_minVal!!
                var xsh_min = xc_minVal!! + xt
                var zsh_min = sqrt(rsh_min * rsh_min + xsh_min * xsh_min)
                var ish3 = uv_nVal * 1000 / sqrt(3.0) / zsh
                var ish2 = ish3 * sqrt(3.0) / 2
                var ish3_min = uv_nVal * 1000 / sqrt(3.0) / zsh_min
                var ish2_min = ish3_min * sqrt(3.0) / 2
                // second
                var kpr = (un_nVal!! * un_nVal) / (uv_nVal * uv_nVal)
                var rsh_n = kpr * rsh
                var xsh_n = kpr * xsh
                var zsh_n = sqrt(rsh_n * rsh_n + xsh_n * xsh_n)
                var rsh_n_min = kpr * rsh_min
                var xsh_n_min = kpr * xsh_min
                var zsh_n_min = sqrt(rsh_n_min * rsh_n_min + xsh_n_min * xsh_n_min)
                var ish_3 = un_nVal * 1000 / sqrt(3.0) / zsh_n
                var ish_2 = ish_3 * sqrt(3.0) / 2
                var ish_3_min = un_nVal * 1000 / sqrt(3.0) / zsh_n_min
                var ish_2_min = ish_3_min * sqrt(3.0) / 2
                // third
                var r_l = l_iVal!! * r_oVal!!
                var x_l = l_iVal * x_oVal!!
                val r_sum_n = r_l + rsh_n
                val x_sum_n = x_l + xsh_n
                val z_sum_n = sqrt(r_sum_n * r_sum_n + x_sum_n * x_sum_n)
                val r_sum_n_min = r_l + rsh_n_min
                val x_sum_n_min = x_l + xsh_n_min
                val z_sum_n_min = sqrt(r_sum_n_min * r_sum_n_min + x_sum_n_min * x_sum_n_min)
                val is_h_3 = un_nVal * 1000 / sqrt(3.0) / z_sum_n
                val is_h_2 = is_h_3 * sqrt(3.0) / 2
                val is_h_3_min = un_nVal * 1000 / sqrt(3.0) / z_sum_n_min
                val is_h_2_min = is_h_3_min * sqrt(3.0) / 2

                result.text = """
                        Струми трифазного та двофазного КЗ
                        на шинах 10 кВ в нормальному та мінімальному режимах,
                        приведені до напруги 110 кВ:
                        I3ш: ${"%.2f".format(ish3)}
                        I2ш: ${"%.2f".format(ish2)}
                        I3ш_min: ${"%.2f".format(ish3_min)}
                        I2ш_min:${"%.2f".format(ish2_min)}
                        Дійсні струми
                        трифазного та двофазного КЗ на шинах
                        10 кВ в нормальному та мінімальному режимах:
                        I3ш: ${"%.2f".format(ish_3)}
                        I2ш: ${"%.2f".format(ish_2)}
                        I3ш_min: ${"%.2f".format(ish_3_min)}
                        I2ш_min:${"%.2f".format(ish_2_min)}
                        Струми трифазного і двофазного КЗ
                        в точці 10 в нормальному та мінімальному режимах:
                        I3ш: ${"%.2f".format(is_h_3)}
                        I2ш: ${"%.2f".format(is_h_2)}
                        I3ш_min: ${"%.2f".format(is_h_3_min)}
                        I2ш_min:${"%.2f".format(is_h_2_min)}
                    """.trimIndent()


            }
        }
    }
}

