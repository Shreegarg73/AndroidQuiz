package com.example.application

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val finishButton = findViewById<Button>(R.id.finishButton)
        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        resultTextView.text =
            "Your Score: $score / $totalQuestions"
        finishButton . setOnClickListener {
                finish()
            }
    }
}