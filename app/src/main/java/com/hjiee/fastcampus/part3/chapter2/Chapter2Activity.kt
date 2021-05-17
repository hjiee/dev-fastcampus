package com.hjiee.fastcampus.part3.chapter2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.hjiee.fastcampus.databinding.ActivityPart3Chapter2Binding
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

class Chapter2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart3Chapter2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.vpQuote.setPageTransformer { page, position ->
            when {
                position.absoluteValue >= 1F -> {
                    page.alpha = 0F
                }
                position == 0F -> {
                    page.alpha = 1F
                }
                else -> {
                    page.alpha = 1F - (2 * position.absoluteValue)
                }
            }
        }
    }

    private fun initData() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
        )
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            binding.progressbar.visibility = View.GONE
            if (it.isSuccessful) {
                val quotes = parseQuotesJson(remoteConfig.getString("quotes"))
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")
                displayQuotesPager(quotes, isNameRevealed)
            }
        }
    }

    private fun displayQuotesPager(quotes: List<Quote>, isNameRevealed: Boolean) {
        val adapter = QuotePagerAdapter(quotes, isNameRevealed)
        binding.vpQuote.adapter = adapter
        binding.vpQuote.setCurrentItem(adapter.itemCount / 2, false)
    }

    private fun parseQuotesJson(json: String): List<Quote> {
        val jsonArray = JSONArray(json)
        val jsonLst = emptyList<JSONObject>().toMutableList()
        for (index in 0 until jsonArray.length()) {
            jsonArray.getJSONObject(index)?.let {
                jsonLst += it
            }
        }

        return jsonLst.map {
            Quote(
                it.getString("quote").orEmpty(),
                it.getString("name").orEmpty()
            )
        }
    }
}