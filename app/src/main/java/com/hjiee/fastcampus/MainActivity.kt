package com.hjiee.fastcampus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemClickListener {

    private val rvContents: RecyclerView by lazy { findViewById(R.id.rv_contents) }

    private val list by lazy {
        listOf(
            Contents("Part2 - Chapter1 : BMI 계산기", com.hjiee.fastcampus.part2.chapter1.Chapter1Activity::class),
            Contents("Part2 - Chapter2 : 로또번호 추첨기", com.hjiee.fastcampus.part2.chapter2.Chapter2Activity::class),
            Contents("Part2 - Chapter3 : 비밀 다이어리", com.hjiee.fastcampus.part2.chapter3.Chapter3Activity::class),
            Contents("Part2 - Chapter4 : 계산기", com.hjiee.fastcampus.part2.chapter4.Chapter4Activity::class),
            Contents("Part2 - Chapter5 : 전자액자", com.hjiee.fastcampus.part2.chapter5.Chapter5Activity::class),
            Contents("Part2 - Chapter6 : 뽀모도로 타이머", com.hjiee.fastcampus.part2.chapter6.Chapter6Activity::class),
            Contents("Part2 - Chapter7 : 녹음기", com.hjiee.fastcampus.part2.chapter7.Chapter7Activity::class),
            Contents("Part2 - Chapter8 : 심플 웹 브라우저", com.hjiee.fastcampus.part2.chapter8.Chapter8Activity::class),

            Contents("Part3 - Chapter1 : 푸시 알림 수신기", com.hjiee.fastcampus.part3.chapter1.Chapter1Activity::class),
            Contents("Part3 - Chapter2 : 오늘의 명언", com.hjiee.fastcampus.part3.chapter2.Chapter2Activity::class),
            Contents("Part3 - Chapter3 : 알림", com.hjiee.fastcampus.part3.chapter3.Chapter3Activity::class),
            Contents("Part3 - Chapter4 : 도서 리뷰", com.hjiee.fastcampus.part3.chapter4.Chapter4Activity::class),
            Contents("Part3 - Chapter5 : 틴더", com.hjiee.fastcampus.part3.chapter5.Chapter5Activity::class),
            Contents("Part3 - Chapter6 : 중고거래", com.hjiee.fastcampus.part3.chapter6.Chapter6Activity::class),
            Contents("Part3 - Chapter7 : 에어비엔비", com.hjiee.fastcampus.part3.chapter7.Chapter7Activity::class),

            Contents("Part4 - Chapter1 : 유튜브", com.hjiee.fastcampus.part4.chapter1.Chapter1Activity::class),
            Contents("Part4 - Chapter2 : 음악 스트리밍", com.hjiee.fastcampus.part4.chapter2.Chapter2Activity::class),
            Contents("Part4 - Chapter3 : 위치검색", com.hjiee.fastcampus.part4.chapter3.Chapter3Activity::class),
            Contents("Part4 - Chapter4 : OTT", com.hjiee.fastcampus.part4.chapter4.Chapter4Activity::class),
            Contents("Part4 - Chapter5 : 깃 레포지토리", com.hjiee.fastcampus.part4.chapter5.Chapter5Activity::class),
            Contents("Part4 - Chapter6 : 우리동네 미세먼지", com.hjiee.fastcampus.part4.chapter6.Chapter6Activity::class),
            Contents("Part4 - Chapter7 : 저작권 무료 이미지 검색 ", com.hjiee.fastcampus.part4.chapter7.Chapter7Activity::class),
        )
    }
    private val listAdapter by lazy { SimpleAdapter(clickListener = this@MainActivity) }
    private val chapterList by lazy { list }

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