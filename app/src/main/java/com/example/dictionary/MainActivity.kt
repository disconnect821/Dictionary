package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.adapter.WordMeaningAdapter
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.models.Words
import com.example.dictionary.models.api.RetrofitInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : WordMeaningAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            val word = binding.searchEditText.text.toString()
            getMeaning(word)
        }

        adapter = WordMeaningAdapter(emptyList())
        binding.meaningsRecyclerView.adapter  = adapter
        binding.meaningsRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun getMeaning(word : String) {
        setProgressBar(true)
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.apiInterface.getMeaning(word)
                if(response.body() == null){
                    throw(Exception())
                }
                runOnUiThread{
                    setProgressBar(false)
                    response.body()?.first().let{
                        setUI(it)
                    }
                }
            }catch (e:Exception){
                runOnUiThread{
                    setProgressBar(false)
                    Toast.makeText(applicationContext, "Something went wrong" , Toast.LENGTH_SHORT).show()
                    Log.e("RetrofitFailure", "Error: ${e.message}")
                }
            }
        }
    }

    private fun setUI(response: Words?) {
        binding.searchedWord.text = response!!.word
        this.adapter.updateNewData(response.meanings)
    }

    private fun setProgressBar(inProgress: Boolean) {
        if(inProgress)
        {
            binding.searchButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.searchButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}