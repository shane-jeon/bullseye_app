package com.sJeon.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sJeon.bullseye.databinding.ActivityAboutBinding
import com.sJeon.bullseye.databinding.ActivityMainBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.about_page_title)

            binding.backButton?.setOnClickListener{
                finish()
            }
    }
}