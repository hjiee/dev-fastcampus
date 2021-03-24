package com.hjiee.fastcampus.part2.chapter1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.R
import kotlin.math.pow

class Chapter1ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter1_result)

        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)

        val bmi = weight / (height / 100.0).pow(2)
        val result = when {
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중정도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

        val tvBmi = findViewById<TextView>(R.id.tv_bmi)
        val tvResult = findViewById<TextView>(R.id.tv_result)

        tvBmi.text = bmi.toString()
        tvResult.text = result
    }
}