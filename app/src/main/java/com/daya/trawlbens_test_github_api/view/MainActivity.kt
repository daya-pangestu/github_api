package com.daya.trawlbens_test_github_api.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.daya.trawlbens_test_github_api.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewmModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmModel.queryLiveData.observe(this){
            it.items.forEach {
                Log.d("tag","${it.login}")
            }
        }

        binding.search.doOnEnter {
            viewmModel.setQuery(it)
        }
    }
}

private fun EditText.doOnEnter(action: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO) {
            action(text.toString())
        }
        true
    }
}