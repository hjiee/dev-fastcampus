package com.hjiee.fastcampus.part4.chapter2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter2Binding

class Chapter2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}