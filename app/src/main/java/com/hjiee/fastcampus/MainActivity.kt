package com.hjiee.fastcampus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.fastcampus.part2.Chapter1Activity

class MainActivity : AppCompatActivity(), ItemClickListener {

    private val rvContents: RecyclerView by lazy { findViewById(R.id.rv_contents) }

    private val listAdapter by lazy { SimpleAdapter(clickListener = this@MainActivity) }

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
        listAdapter.addItem(Contents("Part2 - Chapter1"))
        listAdapter.notifyDataSetChanged()
    }


    override fun onClick(position: Int) {
        when (position) {
            0 -> startActivity(Intent(this@MainActivity, Chapter1Activity::class.java))
        }
    }
}