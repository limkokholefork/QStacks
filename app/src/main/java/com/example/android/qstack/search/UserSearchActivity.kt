package com.example.android.qstack.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.qstack.R
import com.example.android.qstack.ui.users.SEARCH_USER_KEY
import timber.log.Timber

class UserSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        val query = intent?.getStringExtra(SEARCH_USER_KEY)
        Timber.d("Query is $query")
    }
}