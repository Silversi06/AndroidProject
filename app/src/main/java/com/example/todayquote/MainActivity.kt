package com.example.todayquote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var quotes : MutableList<Quote>
    lateinit var pref : SharedPreferences

    fun initializeQuotes() {
        // Q) 처음 사용자가 앱을 키면 아무 명언이 없으니까 5개의 명언을 추가하는데,
        // 그 이후에 켰을때는 아무것도 안하게 하고 싶음
        // "initialized"라는 키가 있어서 boolean 타입 값을 저장하는데
        // 이게 false 면 지금 처음 켜는거, true면 그 이후킨거
        // val pref = getSharedPreferences("quotes", Context.MODE_PRIVATE)
        val initialized = pref.getBoolean("initialized", false)
        if(!initialized) {
            // 임시 명언 5개를 추가
            for(i in 0..5) {
                Quote.saveToPreferences(pref, i, "명언 ${i}", "출처 ${i}")
            }
        }
        val editor = pref.edit()
        editor.putBoolean("initialized", true)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("quotes", Context.MODE_PRIVATE)
        initializeQuotes()

        quotes = Quote.getQuotesFromPreference(pref)
        val index = Random.nextInt(0, quotes.size)
        val randomQuote = quotes[index]
        val quoteText = findViewById<TextView>(R.id.quote_text)
        val quoteFrom = findViewById<TextView>(R.id.quote_from)
        quoteText.text = randomQuote.text
        quoteFrom.text = randomQuote.from

        val toList = findViewById<Button>(R.id.quote_list)
        toList.setOnClickListener{
            val intent = Intent(this,
                QuoteListActivity::class.java)
            intent.putExtra("key1",1)
            startActivity(intent)
        }

    }
}