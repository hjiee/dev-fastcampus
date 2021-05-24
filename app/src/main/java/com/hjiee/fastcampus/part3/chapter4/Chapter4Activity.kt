package com.hjiee.fastcampus.part3.chapter4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter4Binding
import com.hjiee.fastcampus.part3.chapter4.adapter.BookAdapter
import com.hjiee.fastcampus.part3.chapter4.adapter.HistoryAdapter
import com.hjiee.fastcampus.part3.chapter4.api.BookAPI
import com.hjiee.fastcampus.part3.chapter4.model.BestSellerDto
import com.hjiee.fastcampus.part3.chapter4.model.BookHistory
import com.hjiee.fastcampus.part3.chapter4.model.SearchBooksDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Chapter4Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart3Chapter4Binding.inflate(layoutInflater) }
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var service: BookAPI

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "bookHistoryDB"
        ).build()

        adapter = BookAdapter(clickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("bookModel", it)
            startActivity(intent)
        })
        historyAdapter = HistoryAdapter(historyDeleteClickListener = {
            deleteSearchKeyword(it)
        })


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(BookAPI::class.java)
        service.getBestSeller((API_KEY))
            .enqueue(object : Callback<BestSellerDto> {
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    Log.e("error","$t")
                }

                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        adapter.submitList(it.books)
                    }
                }

            })



        binding.recyclerView.adapter = adapter

        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }

            return@setOnTouchListener false
        }


        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun search(text: String) {


        service.getBooksByName(API_KEY, text)
            .enqueue(object : Callback<SearchBooksDto> {
                override fun onFailure(call: Call<SearchBooksDto>, t: Throwable) {
                    hideHistoryView()
                }

                override fun onResponse(
                    call: Call<SearchBooksDto>,
                    response: Response<SearchBooksDto>
                ) {

                    hideHistoryView()
                    saveSearchKeyword(text)

                    if (response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        adapter.submitList(it.books)
                    }
                }

            })
    }

    private fun showHistoryView() {
        Thread(Runnable {
            db.historyDao().getAll().reversed().run {
                runOnUiThread {
                    binding.historyRecyclerView.isVisible = true
                    historyAdapter.submitList(this)
                }
            }

        }).start()

    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread(Runnable {
            db.historyDao().insertBookHistory(BookHistory(null, keyword))
        }).start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread(Runnable {
            db.historyDao().delete(keyword)
            showHistoryView()
        }).start()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://book.interpark.com/"
        private const val API_KEY =
            "D60B790C9F96D0F7742A90C2AF9CBBD708224F5E63AB26807104EECD57DD2ABA"
    }

}