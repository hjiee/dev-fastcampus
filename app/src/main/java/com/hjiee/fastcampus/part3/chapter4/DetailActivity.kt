package com.hjiee.fastcampus.part3.chapter4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter4DetailBinding
import com.hjiee.fastcampus.part3.chapter4.model.Book
import com.hjiee.fastcampus.part3.chapter4.model.Review

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPart3Chapter4DetailBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPart3Chapter4DetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "bookHistoryDB"
        ).build()

        val bookModel = intent.getParcelableExtra<Book>("bookModel")

        binding.titleTextView.text = bookModel?.title.orEmpty()

        Glide
            .with(binding.coverImageView.context)
            .load(bookModel?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)

        binding.descriptionTextView.text = bookModel?.description.orEmpty()

        Thread {
            val review = db.reviewDao().getOne(bookModel?.id?.toInt() ?: 0)
            runOnUiThread {
                binding.reviewEditText.setText(review?.review.orEmpty())
            }
        }.start()

        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        bookModel?.id?.toInt() ?: 0,
                        binding.reviewEditText.text.toString()
                    )
                )

            }.start()
        }

    }

}
