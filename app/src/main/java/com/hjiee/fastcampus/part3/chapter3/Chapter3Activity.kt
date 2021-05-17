package com.hjiee.fastcampus.part3.chapter3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter3Binding

class Chapter3Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart3Chapter3Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}