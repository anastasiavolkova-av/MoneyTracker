package com.example.money_tracker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Объявляем переменные для всех View
    private lateinit var layoutChart: LinearLayout
    private lateinit var layoutCategories: ScrollView
    private lateinit var btnTabChart: ImageButton
    private lateinit var btnTabCategories: ImageButton
    private lateinit var btnSave: Button
    private lateinit var etDream: EditText
    private lateinit var etFood: EditText
    private lateinit var etFun: EditText
    private lateinit var tvDreamValue: TextView
    private lateinit var tvFoodValue: TextView
    private lateinit var tvFunValue: TextView
    private lateinit var pieChart: PieChartView

    private lateinit var etTransport: EditText

    private lateinit var tvTransportValue: TextView
    //category values
    private var transportValue = 0f
    private var dreamValue = 0f
    private var foodValue = 0f
    private var funValue = 0f

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        setupClickListeners()
        showChart()
    }

    // layout elements to variables
    private fun findViews() {
        layoutChart = findViewById(R.id.layoutChart)
        layoutCategories = findViewById(R.id.layoutCategories)
        btnTabChart = findViewById(R.id.btnTabChart)
        btnTabCategories = findViewById(R.id.btnTabCategories)
        btnSave = findViewById(R.id.btnSave)
        etDream = findViewById(R.id.etDream)
        etFood = findViewById(R.id.etFood)
        etFun = findViewById(R.id.etFun)
        tvDreamValue = findViewById(R.id.tvDreamValue)
        tvFoodValue = findViewById(R.id.tvFoodValue)
        tvFunValue = findViewById(R.id.tvFunValue)
        pieChart = findViewById(R.id.pieChart)
        etTransport = findViewById(R.id.etTransport)
        tvTransportValue = findViewById(R.id.tvTransportValue)
    }

    //assign actions to buttons
    private fun setupClickListeners() {
        btnTabChart.setOnClickListener { showChart() }
        btnTabCategories.setOnClickListener { showCategories() }
        btnSave.setOnClickListener { saveValues() }
    }

    private fun showChart() {
        layoutChart.visibility = View.VISIBLE
        layoutCategories.visibility = View.GONE
    }

    private fun showCategories() {
        layoutChart.visibility = View.GONE
        layoutCategories.visibility = View.VISIBLE
    }

    // save input values
    private fun saveValues() {
        dreamValue = etDream.text.toString().toFloatOrNull() ?: 0f
        foodValue = etFood.text.toString().toFloatOrNull() ?: 0f
        funValue = etFun.text.toString().toFloatOrNull() ?: 0f
        transportValue = etTransport.text.toString().toFloatOrNull() ?: 0f

        // Get the salary
        val totalSalary = getTotalSalary()
        val totalDistributed = dreamValue + foodValue + funValue + transportValue

        // Check that expanses < salary
        if (totalDistributed > totalSalary) {
            Toast.makeText(this, "Сумма по категориям (${totalDistributed.toInt()} руб) превышает зарплату ($totalSalary руб)!", Toast.LENGTH_LONG).show()
            return
        }

        // Update UI
        tvDreamValue.text = "${dreamValue.toInt()} руб"
        tvFoodValue.text = "${foodValue.toInt()} руб"
        tvFunValue.text = "${funValue.toInt()} руб"
        tvTransportValue.text = "${transportValue.toInt()} руб"

        // Update diagram
        pieChart.setValues(dreamValue, foodValue, funValue, transportValue)
        showChart()
    }

    // user salary from SharedPreferences
    private fun getTotalSalary(): Float { //
        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPref.getFloat("monthly_salary", 50000f)
    }
}