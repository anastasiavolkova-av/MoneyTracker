package com.example.money_tracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileSetupActivity : AppCompatActivity() {

    // UI elements
    private lateinit var nameEditText: EditText
    private lateinit var salaryEditText: EditText
    private lateinit var goalEditText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Find UI elements
        nameEditText = findViewById(R.id.editTextText2)
        salaryEditText = findViewById(R.id.editTextText3)
        goalEditText = findViewById(R.id.editTextText4)
        continueButton = findViewById(R.id.startButton2)

        // Set click listener
        continueButton.setOnClickListener {
            saveUserData()
        }
    }

    // save user data
    private fun saveUserData() {
        val name = nameEditText.text.toString()
        val salaryText = salaryEditText.text.toString()
        val goal = goalEditText.text.toString()

        if (name.isEmpty() || salaryText.isEmpty() || goal.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val salary = salaryText.toFloatOrNull()
        if (salary == null || salary <= 0) {
            Toast.makeText(this, "Введите корректную зарплату", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_name", name)
            putFloat("monthly_salary", salary)
            putString("savings_goal", goal)
            apply()
        }

        // go to MainBudgetActivity:
         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
         finish()
    }
}