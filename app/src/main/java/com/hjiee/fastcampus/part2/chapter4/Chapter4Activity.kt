package com.hjiee.fastcampus.part2.chapter4

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.room.Room
import com.hjiee.fastcampus.R
import com.hjiee.fastcampus.part2.chapter4.model.History

class Chapter4Activity : AppCompatActivity() {


    private val tvExpression by lazy { findViewById<TextView>(R.id.tv_expression) }
    private val tvResult by lazy { findViewById<TextView>(R.id.tv_result) }
    private val clHistory by lazy { findViewById<ConstraintLayout>(R.id.cl_history) }
    private val llHistory by lazy { findViewById<LinearLayout>(R.id.ll_history) }
    private var isOperator = false
    private var hasOperator = false
    private lateinit var db: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2_chapter4)
        db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "historyDB"
        ).build()
    }

    fun buttonClicked(view: View) {
        when (view.id) {
            R.id.btn_0 -> numberButtonClick("0")
            R.id.btn_1 -> numberButtonClick("1")
            R.id.btn_2 -> numberButtonClick("2")
            R.id.btn_3 -> numberButtonClick("3")
            R.id.btn_4 -> numberButtonClick("4")
            R.id.btn_5 -> numberButtonClick("5")
            R.id.btn_6 -> numberButtonClick("6")
            R.id.btn_7 -> numberButtonClick("7")
            R.id.btn_8 -> numberButtonClick("8")
            R.id.btn_9 -> numberButtonClick("9")
            R.id.btn_mulit -> operatorButtonClick("x")
            R.id.btn_plus -> operatorButtonClick("+")
            R.id.btn_minus -> operatorButtonClick("-")
            R.id.btn_modulo -> operatorButtonClick("%")
            R.id.btn_divider -> operatorButtonClick("/")
        }

    }

    private fun operatorButtonClick(operator: String) {
        if (tvExpression.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = tvExpression.text.toString()
                tvExpression.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용 할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                tvExpression.append(" $operator")
            }
        }
        val ssb = SpannableStringBuilder(tvExpression.text)
        ssb.setSpan(
                ForegroundColorSpan(getColor(R.color.green7aaf3b)),
                tvExpression.text.length - 1,
                tvExpression.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvExpression.text = ssb
        isOperator = true
        hasOperator = true
    }

    private fun numberButtonClick(number: String) {
        if (isOperator) {
            tvExpression.append(" ")
        }
        isOperator = false

        val expressionText = tvExpression.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        tvExpression.append(number)
        tvResult.text = calculateExpression()
    }

    fun clearButtonClicked(view: View) {
        tvExpression.text = ""
        tvResult.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyButtonClicked(view: View) {
        clHistory.isVisible = true
        llHistory.removeAllViews()

        Thread(Runnable {
            db.historyDao().getAll().reversed().forEach {
                runOnUiThread {
                    val historyView = LayoutInflater.from(this@Chapter4Activity).inflate(R.layout.item_history_row, null, false)
                    historyView.findViewById<TextView>(R.id.tv_expression).text = it.expression
                    historyView.findViewById<TextView>(R.id.tv_result).text = "= ${it.result}"

                    llHistory.addView(historyView)
                }
            }
        }).start()

    }

    fun historyCloseButtonClicked(view: View) {
        clHistory.isVisible = false
    }

    fun historyClearButtonClicked(view: View) {
        llHistory.removeAllViews()
    }

    fun resultButtonClicked(view: View) {
        val expressionTexts = tvExpression.text.split(" ")

        if (tvExpression.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "아직 완성되지 않은 수식 입니다.", Toast.LENGTH_SHORT).show()
        }

        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }

        val expressText = tvExpression.text.toString()
        val resultText = calculateExpression()

        Thread(Runnable {
            db.historyDao().insertHistory(History(null, expressText, resultText))
        }).start()
        tvResult.text = ""
        tvExpression.text = resultText

        isOperator = false
        hasOperator = false

    }

    private fun calculateExpression(): String {
        val expressionTexts = tvExpression.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op.toLowerCase()) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "x" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }


    }

}

fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }

}
