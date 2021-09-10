package com.example.android.qstack.search

import android.content.SearchRecentSuggestionsProvider

class RecentSuggestionAdapter: SearchRecentSuggestionsProvider() {


    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object{
        const val AUTHORITY = "com.example.android.qstack"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}