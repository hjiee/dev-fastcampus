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
                Contents("Part2 - Chapter1", 2),
                Contents("Part2 - Chapter2", 2),
                Contents("Part2 - Chapter3", 2),
                Contents("Part2 - Chapter4", 2),
                Contents("Part2 - Chapter5", 2),
                Contents("Part2 - Chapter6", 2),
                Contents("Part2 - Chapter7", 2),
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
        val part = chapterList[position].part
        val chapter = position + 1
        val classPath = "com.hjiee.fastcampus.part${part}.chapter${chapter}.Chapter${chapter}Activity"
        startActivity(Intent(this@MainActivity, Class.forName(classPath)))
    }
}