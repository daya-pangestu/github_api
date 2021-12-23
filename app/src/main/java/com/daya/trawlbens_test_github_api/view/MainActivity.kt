package com.daya.trawlbens_test_github_api.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daya.trawlbens_test_github_api.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}