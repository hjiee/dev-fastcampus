package com.hjiee.fastcampus.part2.chapter5

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hjiee.fastcampus.databinding.ActivityPart2Chapter5Binding

class Chapter5Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart2Chapter5Binding.inflate(layoutInflater) }
    private val selectedUris by lazy { mutableListOf<Uri>() }
    private val imageViews: List<ImageView> by lazy {
        listOf(
                binding.ivPhoto11,
                binding.ivPhoto12,
                binding.ivPhoto13,
                binding.ivPhoto21,
                binding.ivPhoto22,
                binding.ivPhoto23
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            2000 -> {
                data?.data?.let {

                    if (selectedUris.size == 6) {
                        Toast.makeText(this, "이미 사진이 꽉 찼습니다", Toast.LENGTH_SHORT).show()
                        return
                    }

                    selectedUris.add(it)
                    imageViews[selectedUris.size - 1].setImageURI(it)
                }
                        ?: kotlin.run { Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show() }

            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    navigatePhotos()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }
    }

    private fun initAddPhotoButton() {
        binding.btnAdd.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                        this@Chapter5Activity,
                        READ_EXTERNAL_STORAGE
                ) == PERMISSION_GRANTED -> {
                    navigatePhotos()
                }

                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {
                    // TODO 교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                    showPermissionContextPopup()
                }

                else -> {
                    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }

    private fun initStartPhotoFrameModeButton() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, PhotoFrameActivity::class.java)
            selectedUris.forEachIndexed { index, uri ->
                intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoListSize", selectedUris.size)
            startActivity(intent)

        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }


    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
                .setTitle("권한이 필요합니다.")
                .setMessage("전자액자 앱에서 사진을 불러오기위해 권한이 필요 합니다.")
                .setPositiveButton("동의하기") { _, _ ->
                    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 1000)
                }
                .setNegativeButton("취소하기") { _, _ ->

                }
                .create()
                .show()
    }
}