package com.hjiee.fastcampus.part4.chapter3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter3Binding

class Chapter3Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter3Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}