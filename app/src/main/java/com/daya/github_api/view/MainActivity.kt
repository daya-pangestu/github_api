package com.daya.github_api.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.github_api.R
import com.daya.github_api.data.Resource
import com.daya.github_api.databinding.ActivityMainBinding
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewmModel by viewModels<MainViewModel>()

    lateinit var skeleton : Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainAdapter = MainAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = mainAdapter

        skeleton = binding.rv.applySkeleton(R.layout.item_user)

        viewmModel.queryFlow.observe(this){
            when (it) {
                is Resource.Loading ->{
                    skeleton.showSkeleton()
                }
                is Resource.Success ->{
                    mainAdapter.submitList(it.data)
                    if (it.data.isNullOrEmpty()) skeleton.showOriginal()

                }
                is Resource.Error ->{
                    skeleton.showOriginal()
                    Toast.makeText(this@MainActivity, it.exceptionMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.search.doOnEnter { _, text ->
            viewmModel.setQuery(text)
            hideKeyboard(binding.search)
        }
    }
}

private fun EditText.doOnEnter(action: (EditText,String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            action(this,this.text.toString())
        }
        true
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}