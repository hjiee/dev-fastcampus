package com.hjiee.fastcampus.part4.chapter6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter6Binding

class Chapter6Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter6Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}