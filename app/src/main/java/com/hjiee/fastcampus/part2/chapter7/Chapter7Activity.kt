package com.hjiee.fastcampus.part2.chapter7


import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart2Chapter7Binding

class Chapter7Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart2Chapter7Binding.inflate(layoutInflater) }
    private var state: State = State.BEFORE_RECORDING
        set(value) {
            field = value
            binding.btnReset.isEnabled = (value == State.AFTER_RECORDING || value == State.ON_PLAYING)
            binding.ivRecord.updateIconWithState(value)
        }
    private val requiredPermissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val recodingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestAudioPermission()
        initView()
        initVariables()
    }

    private fun initView() {
        binding.apply {
            with(ivRecord) {
                updateIconWithState(state)
                setOnClickListener {
                    when (state) {
                        State.BEFORE_RECORDING -> {
                            startRecording()
                        }
                        State.ON_RECORDING -> {
                            stopRecording()
                        }
                        State.AFTER_RECORDING -> {
                            startPlaying()
                        }
                        State.ON_PLAYING -> {
                            stopPlaying()
                        }
                    }
                }
            }

            with(btnReset) {
                setOnClickListener {
                    stopPlaying()
                    soundVisualizer.clearVisualization()
                    tvRecordTime.clearTime()
                    state = State.BEFORE_RECORDING
                }
            }
            with(soundVisualizer) {
                onRequestCurrentAmplitude = {
                    recorder?.maxAmplitude ?: 0
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUIDIO_PERMISSION && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            finish()
        }
    }

    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUIDIO_PERMISSION)
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recodingFilePath)
            prepare()
        }
        recorder?.start()
        binding.soundVisualizer.startVisualizing(false)
        binding.tvRecordTime.startCountUp()
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        binding.soundVisualizer.stopVisualizing()
        binding.tvRecordTime.stopCountUp()
        state = State.AFTER_RECORDING
    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            setDataSource(recodingFilePath)
            prepare()
        }
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }
        player?.start()
        binding.soundVisualizer.startVisualizing(true)
        binding.tvRecordTime.startCountUp()
        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        binding.soundVisualizer.stopVisualizing()
        binding.tvRecordTime.stopCountUp()
        state = State.AFTER_RECORDING
    }

    companion object {
        const val REQUEST_RECORD_AUIDIO_PERMISSION = 201
    }

}