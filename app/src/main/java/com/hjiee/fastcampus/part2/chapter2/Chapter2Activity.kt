package com.hjiee.fastcampus.part2.chapter2

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.hjiee.fastcampus.R

class Chapter2Activity : AppCompatActivity() {

    private val btnClear by lazy { findViewById<Button>(R.id.btn_clear) }
    private val btnAdd by lazy { findViewById<Button>(R.id.btn_add) }
    private val btnRun by lazy { findViewById<Button>(R.id.btn_run) }
    private val numberPicker by lazy { findViewById<NumberPicker>(R.id.np_number) }

    private val numberTextViewList: List<TextView> by lazy {
        listOf(
                findViewById(R.id.tv_number_1),
                findViewById(R.id.tv_number_2),
                findViewById(R.id.tv_number_3),
                findViewById(R.id.tv_number_4),
                findViewById(R.id.tv_number_5),
                findViewById(R.id.tv_number_6)
        )
    }

    private var didRun = false
    private val pickNumberSet = HashSet<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter2)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        btnRun.setOnClickListener {
            didRun = true
            val list = getRandomNumber()
            list.forEachIndexed { index, number ->
                numberTextViewList[index].apply {
                    setNumberBackground(number, this)
                    text = number.toString()
                    isVisible = true
                }
            }
        }
    }

    private fun setNumberBackground(number: Int, textView: TextView) {
        textView.background = when (number) {
            in 1..10 -> {
                ContextCompat.getDrawable(this@Chapter2Activity, R.drawable.circle_yello)
            }
            in 11..20 -> {
                ContextCompat.getDrawable(this@Chapter2Activity, R.drawable.circle_blue)
            }
            in 21..30 -> {
                ContextCompat.getDrawable(this@Chapter2Activity, R.drawable.circle_red)
            }
            in 31..40 -> {
                ContextCompat.getDrawable(this@Chapter2Activity, R.drawable.circle_gray)
            }
            else -> {
                ContextCompat.getDrawable(this@Chapter2Activity, R.drawable.circle_green)
            }
        }
    }

    private fun initAddButton() {
        btnAdd.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택 할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호 입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            setNumberBackground(numberPicker.value, textView)

            pickNumberSet.add(numberPicker.value)

        }
    }

    private fun initClearButton() {
        btnClear.setOnClickListener {
            didRun = false
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (pickNumberSet.contains(i)) {
                    continue
                }
                add(i)
            }
        }

        numberList.shuffle()
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted()
    }
}