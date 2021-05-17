package com.hjiee.fastcampus.part3.chapter6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter6Binding

class Chapter6Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart3Chapter6Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}