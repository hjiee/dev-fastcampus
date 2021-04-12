package com.hjiee.fastcampus.part2.chapter5

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPhotoFrameBinding
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPhotoFrameBinding.inflate(layoutInflater) }
    private val photoList by lazy { mutableListOf<Uri>() }
    private var currentPosition = 0
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getPhotoUriFromIntent()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 5 * 1000) {
            runOnUiThread {
                val current = currentPosition
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                binding.ivPhotoBackground.setImageURI(photoList[current])

                binding.ivPhotoForeground.alpha = 0f
                binding.ivPhotoForeground.setImageURI(photoList[next])
                binding.ivPhotoForeground.animate()
                        .alpha(1.0f)
                        .setDuration(1000)
                        .start()

                currentPosition = next
            }
        }
    }
}