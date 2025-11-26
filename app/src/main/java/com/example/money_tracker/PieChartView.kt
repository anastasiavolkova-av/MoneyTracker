package com.example.money_tracker

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Colors for categories
    private val dreamColor = Color.parseColor("#F8EDE7")
    private val foodColor = Color.parseColor("#EEF8F7")
    private val funColor = Color.parseColor("#ECE9F2")

    private val transportColor = Color.parseColor("#E3F2FD")

    // defoult values
    private var dreamValue = 0f
    private var foodValue = 0f
    private var funValue = 0f
    private var transportValue = 0f


    init {
        // draw setting
        paint.style = Paint.Style.FILL

        // text setting
        textPaint.color = Color.BLACK
        textPaint.textSize = 60f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.DEFAULT_BOLD

        // stroke setting
        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = Color.WHITE
        strokePaint.strokeWidth = 4f

    }

    fun setValues(dream: Float, food: Float, funValue: Float, transport: Float) {
        this.dreamValue = dream
        this.foodValue = food
        this.funValue = funValue
        this.transportValue = transport
        invalidate() // redrawing
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        val centerY = height / 2
        val radius = min(width, height) / 2 - 20f

        // get the salary
        val totalSalary = getTotalSalary()
        val totalDistributed = dreamValue + foodValue + funValue + transportValue
        val remaining = totalSalary - totalDistributed

        if (totalDistributed == 0f && remaining == 0f) {
            // free area
            paint.color = Color.LTGRAY
            canvas.drawCircle(centerX, centerY, radius, paint)
            textPaint.color = Color.DKGRAY
            canvas.drawText("Нет данных", centerX, centerY, textPaint)
            return
        }

        // calculating angeles for categories
        val dreamAngle = (dreamValue / totalSalary) * 360
        val foodAngle = (foodValue / totalSalary) * 360
        val funAngle = (funValue / totalSalary) * 360
        val transportAngle = (transportValue / totalSalary) * 360
        val remainingAngle = (remaining / totalSalary) * 360

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        var startAngle = -90f // begin from the top

        // draw categories
        if (dreamValue > 0) {
            paint.color = dreamColor
            canvas.drawArc(rect, startAngle, dreamAngle, true, paint)
            canvas.drawArc(rect, startAngle, dreamAngle, true, strokePaint)
            startAngle += dreamAngle
        }

        if (foodValue > 0) {
            paint.color = foodColor
            canvas.drawArc(rect, startAngle, foodAngle, true, paint)
            canvas.drawArc(rect, startAngle, foodAngle, true, strokePaint)
            startAngle += foodAngle
        }

        if (funValue > 0) {
            paint.color = funColor
            canvas.drawArc(rect, startAngle, funAngle, true, paint)
            canvas.drawArc(rect, startAngle, funAngle, true, strokePaint)
            startAngle += funAngle
        }

        if (transportValue > 0) {
            paint.color = transportColor
            canvas.drawArc(rect, startAngle, transportAngle, true, paint)
            canvas.drawArc(rect, startAngle, transportAngle, true, strokePaint)
            startAngle += transportAngle
        }

        // draw free area
        if (remaining > 0) {
            paint.color = Color.LTGRAY
            canvas.drawArc(rect, startAngle, remainingAngle, true, paint)
            canvas.drawArc(rect, startAngle, remainingAngle, true, strokePaint)
        }

        // circle outline
        strokePaint.color = Color.WHITE
        strokePaint.strokeWidth = 6f
        canvas.drawCircle(centerX, centerY, radius, strokePaint)

        // percent of distributed money
        val percentage = (totalDistributed / totalSalary * 100).toInt()
        textPaint.color = Color.BLACK
        canvas.drawText("$percentage%", centerX, centerY + 15, textPaint)
    }

    private fun getTotalSalary(): Float {
        // get salary from SharedPreferences
        val sharedPref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPref.getFloat("monthly_salary", 50000f) // значение по умолчанию
    }
}