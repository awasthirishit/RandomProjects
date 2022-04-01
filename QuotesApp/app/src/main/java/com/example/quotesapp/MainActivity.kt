package com.example.quotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val author : TextView =findViewById(R.id.author)
        val quote : TextView = findViewById(R.id.quote)

        val quotesAPI = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        GlobalScope.launch {
            val result = quotesAPI.getQuotes()

            val quotesList = result.body()
            runOnUiThread(Runnable {
                if (quotesList != null) {
                    author.text = quotesList.author
                    quote.text = quotesList.en
                }
                 })
        }

    }
}