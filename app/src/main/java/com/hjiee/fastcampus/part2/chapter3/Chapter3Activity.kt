package com.hjiee.fastcampus.part2.chapter3

import android.content.Context
import android.content.Intent
import android.graphics.Color.BLACK
import android.graphics.Color.RED
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import com.hjiee.fastcampus.R

class Chapter3Activity : AppCompatActivity() {

    private val numberPicker1 by lazy {
        findViewById<NumberPicker>(R.id.np_1).apply {
            maxValue = 0
            maxValue = 9
        }
    }
    private val numberPicker2 by lazy {
        findViewById<NumberPicker>(R.id.np_2).apply {
            maxValue = 0
            maxValue = 9
        }
    }
    private val numberPicker3 by lazy {
        findViewById<NumberPicker>(R.id.np_3).apply {
            maxValue = 0
            maxValue = 9
        }
    }

    private val btnOpen by lazy { findViewById<AppCompatButton>(R.id.btn_open) }
    private val btnChange by lazy { findViewById<AppCompatButton>(R.id.btn_change) }
    private var changePasswormMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter3)

        numberPicker1
        numberPicker2
        numberPicker3

        btnOpen.setOnClickListener {
            if (changePasswormMode) {
                Toast.makeText(this, "비밀번호를 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val password = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreference.getString("password", "000") == password) {
                startActivity(Intent(this@Chapter3Activity, DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }
        }

        btnChange.setOnClickListener {
            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val password = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswormMode) {
                passwordPreference.edit(true) {
                    putString("password", password)
                }
                changePasswormMode = false
                btnChange.setBackgroundColor(BLACK)
            } else {

                if (passwordPreference.getString("password", "000") == password) {
                    changePasswormMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                    btnChange.setBackgroundColor(RED)
                } else {
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle("실패")
                .setMessage("비밀번호가 잘못되었습니다.")
                .setPositiveButton("확인") { _, _ -> }
                .create()
                .show()
    }
}