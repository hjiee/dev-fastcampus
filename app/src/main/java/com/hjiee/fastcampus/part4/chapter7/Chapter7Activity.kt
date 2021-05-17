package com.hjiee.fastcampus.part4.chapter7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter7Binding

class Chapter7Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter7Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}