package com.hjiee.fastcampus.part2.chapter3

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.hjiee.fastcampus.R

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter3_diary)

        val edtDiary = findViewById<EditText>(R.id.edt_diary)
        val detailPreference = getSharedPreferences("diary", Context.MODE_PRIVATE)

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", edtDiary.text.toString())
            }
        }


        edtDiary.setText(detailPreference.getString("detail", ""))
        edtDiary.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}