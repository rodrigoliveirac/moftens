package com.rodcollab.moftens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodcollab.moftens.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}