package com.hjiee.fastcampus.part3.chapter7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter7Binding

class Chapter7Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart3Chapter7Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}