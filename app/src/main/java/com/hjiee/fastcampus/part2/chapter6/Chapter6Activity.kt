package com.hjiee.fastcampus.part2.chapter6

import android.annotation.SuppressLint
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.R
import com.hjiee.fastcampus.databinding.ActivityPart2Chapter6Binding

class Chapter6Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart2Chapter6Binding.inflate(layoutInflater) }
    private var currentCountDownTimer: CountDownTimer? = null
    private val soundPool = SoundPool.Builder().build()
    private var tickingSoundId: Int? = null
    private var bellSoundId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindView()
        initSound()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    private fun bindView() {
        binding.seekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) {
                            updateRemainTime(progress * 60 * 1000L)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                        stopCountDown()
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        seekBar ?: return
                        if (seekBar.progress == 0) {

                        } else {
                            startCountDown(seekBar.progress * 60 * 1000L)
                        }
                    }
                }
        )
    }

    private fun initSound() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
            object : CountDownTimer(initialMillis, 1000L) {
                override fun onTick(millisUnit: Long) {
                    updateRemainTime(millisUnit)
                    updateSeekBar(millisUnit)
                }

                override fun onFinish() {
                    completeCountDown()
                }
            }

    private fun startCountDown(mills: Long) {
        currentCountDownTimer = createCountDownTimer(mills)
        currentCountDownTimer?.start()
        tickingSoundId?.let {
            soundPool.play(it, 1F, 1F, 0, -1, 1F)
        }
    }

    private fun stopCountDown() {
        currentCountDownTimer?.cancel()
        currentCountDownTimer = null
        soundPool.autoPause()
    }

    private fun completeCountDown() {
        updateRemainTime(0)
        updateSeekBar(0)
        soundPool.autoPause()
        bellSoundId?.let {
            soundPool.play(it, 1F, 1F, 0, 0, 1F)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        binding.tvRemainMinutes.text = "%02d'".format(remainSeconds / 60)
        binding.tvRemainSeconds.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        binding.seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}