package com.hjiee.fastcampus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemClickListener {

    private val rvContents: RecyclerView by lazy { findViewById(R.id.rv_contents) }

    private val listAdapter by lazy { SimpleAdapter(clickListener = this@MainActivity) }
    private val chapterList by lazy {
        listOf(
                Contents("Part2 - Chapter1", com.hjiee.fastcampus.part2.chapter1.Chapter1Activity::class),
                Contents("Part2 - Chapter2", com.hjiee.fastcampus.part2.chapter2.Chapter2Activity::class),
                Contents("Part2 - Chapter3", com.hjiee.fastcampus.part2.chapter3.Chapter3Activity::class),
                Contents("Part2 - Chapter4", com.hjiee.fastcampus.part2.chapter4.Chapter4Activity::class),
                Contents("Part2 - Chapter5", com.hjiee.fastcampus.part2.chapter5.Chapter5Activity::class),
                Contents("Part2 - Chapter6", com.hjiee.fastcampus.part2.chapter6.Chapter6Activity::class),
                Contents("Part2 - Chapter7", com.hjiee.fastcampus.part2.chapter7.Chapter7Activity::class),
                Contents("Part2 - Chapter8", com.hjiee.fastcampus.part2.chapter8.Chapter8Activity::class),
                Contents("Part3 - Chapter1", com.hjiee.fastcampus.part3.chapter1.Chapter1Activity::class),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        addContents()
    }

    private fun initView() {
        with(rvContents) {
            adapter = listAdapter
        }
    }

    private fun addContents() {
        listAdapter.addAll(chapterList)
        listAdapter.notifyDataSetChanged()
    }


    override fun onClick(position: Int) {
        val kClass = chapterList[position].kClass
        startActivity(Intent(this@MainActivity, kClass.java))
    }
}