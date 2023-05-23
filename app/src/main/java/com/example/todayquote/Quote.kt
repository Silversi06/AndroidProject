package com.example.todayquote

import android.content.SharedPreferences

data class Quote(var idx: Int,
                 var text: String,
                 var from: String = "") {
    companion object {
        fun saveToPreferences(
            pref : SharedPreferences,
            idx: Int,
            text: String,
            from: String = "") {
            // Q) idx, text, from 정보를 이용해서 pref에 Quote의 모든 값을 저장
            // 키를 idx가 1이라면 "1.text", "1.from"에 저장
            val editor = pref.edit()
            editor.putString("${idx}.text", text)
            editor.putString("${idx}.from", from)
            editor.apply()
        }

        fun removeQuoteFromPreference(
            pref: SharedPreferences,
            idx: Int) {
            // editor.remove 메서드 사용 (키를 전달하면 해당 키, 값 쌍을 지울 수 있음)
            val editor = pref.edit()
            editor.remove("${idx}.text")
            editor.remove("${idx}.from")
            editor.apply()
        }

        fun getQuotesFromPreference(pref: SharedPreferences) : MutableList<Quote> {
            val quotes = mutableListOf<Quote>()
            // Q) 0부터 19까지 반복 돌면서 "0.text", "0.from" 이런식으로 모두 Quote 만들어서
            // quotes에 추가, 없으면 추가 안하면 됨
            for(idx in 0 .. 19) {
                val text = pref.getString("${idx}.text", null)
                val from = pref.getString("${idx}.from", null)
                if(!(text == null || from == null)) {
                    quotes.add(Quote(idx, text, from))
                }
            }

            return quotes
        }


        fun getQuoteFromPreference(
            pref: SharedPreferences,
            idx: Int) : Quote? {
            val text = pref.getString("${idx}.text", null)
            val from = pref.getString("${idx}.from", null)
            return if(text == null || from == null) null else Quote(idx, text, from)
            /*
            if(text == null || from == null) return null
            return Quote(idx, text, from)*/
        }
    }
}