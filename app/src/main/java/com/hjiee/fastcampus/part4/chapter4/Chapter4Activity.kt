package com.hjiee.fastcampus.part4.chapter4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart4Chapter4Binding

class Chapter4Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart4Chapter4Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}