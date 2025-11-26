package com.example.money_tracker

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton = findViewById<Button>(R.id.btnStart)

        // В StartActivity.kt в onCreate:
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            startActivity(intent)
        }
    }
}

