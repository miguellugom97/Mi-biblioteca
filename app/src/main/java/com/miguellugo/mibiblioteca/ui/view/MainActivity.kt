package com.miguellugo.mibiblioteca.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miguellugo.mibiblioteca.R
import com.miguellugo.mibiblioteca.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}