package com.hjiee.fastcampus.part4.chapter1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter1Binding

class Chapter1Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}