package com.example.application

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartQuiz2 : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var answersRadioGroup: RadioGroup
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var submitButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var questionNumberTextView: TextView
    private val questions: List<Question> = listOf(
        Question("What is the capital of India?", listOf("New Delhi", "Chennai", "Kolkata", "Chandigarh"), 0),
        Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
        Question("What is 12 + 12?", listOf("20", "22", "23", "24"), 3)
    )
    private var currentQuestionIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 30000 // 30 seconds for each question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz2)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionTextView = findViewById(R.id.questionTextView)
        answersRadioGroup = findViewById(R.id.answersRadioGroup)
        nextButton = findViewById(R.id.nextButton)
        prevButton = findViewById(R.id.prevButton)
        submitButton = findViewById(R.id.submitButton)
        timerTextView = findViewById(R.id.timerTextView)
        questionNumberTextView = findViewById(R.id.questionNumberTextView)

        loadQuestion()

        nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                saveAnswer()
                currentQuestionIndex++
                loadQuestion()
            }
            if (currentQuestionIndex == questions.size - 1) {
                nextButton.isEnabled = false
                submitButton.isEnabled = true
            }
            prevButton.isEnabled = true
        }

        prevButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                saveAnswer()
                currentQuestionIndex--
                loadQuestion()
            }
            if (currentQuestionIndex == 0) {
                prevButton.isEnabled = false
            }
            nextButton.isEnabled = true
        }

        submitButton.setOnClickListener {
            saveAnswer()
            showFinalScore()
        }
    }

    private fun loadQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        questionTextView.text = currentQuestion.text
        questionNumberTextView.text = "Question ${currentQuestionIndex + 1}"
        for (i in 0 until answersRadioGroup.childCount) {
            (answersRadioGroup.getChildAt(i) as RadioButton).text = currentQuestion.answers[i]
        }
        answersRadioGroup.clearCheck()

        startTimer()
    }

    private fun saveAnswer() {
        val selectedRadioButtonId = answersRadioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
            val selectedAnswerIndex = answersRadioGroup.indexOfChild(findViewById(selectedRadioButtonId))
            checkAnswer(selectedAnswerIndex)
        }
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
        }
    }

    private fun showFinalScore() {
        timer?.cancel()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL_QUESTIONS", questions.size)
        startActivity(intent)
        finish()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                updateTimer()
                Toast.makeText(this@StartQuiz2, "Time's up!", Toast.LENGTH_SHORT).show()
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    loadQuestion()
                } else {
                    showFinalScore()
                }
            }
        }.start()
    }

    private fun updateTimer() {
        val secondsLeft = (timeLeftInMillis / 1000).toInt()
        timerTextView.text = "Time left: $secondsLeft"
    }
}

data class Question(val text: String, val answers: List<String>, val correctAnswerIndex: Int)
