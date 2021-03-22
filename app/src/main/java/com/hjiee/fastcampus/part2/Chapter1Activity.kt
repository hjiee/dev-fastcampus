package com.hjiee.fastcampus.part2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.R

class Chapter1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter1)

        val edtHeight: EditText = findViewById(R.id.edt_height)
        val edtWeight: EditText = findViewById(R.id.edt_weight)
        val btnResult: Button = findViewById(R.id.btn_ok)

        btnResult.setOnClickListener {

            if (edtHeight.text.isEmpty() || edtWeight.text.isEmpty()) {
                Toast.makeText(this@Chapter1Activity, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val weight = edtWeight.text.toString().toInt()
            val height = edtHeight.text.toString().toInt()

            val intent = Intent(this@Chapter1Activity, Chapter1ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}