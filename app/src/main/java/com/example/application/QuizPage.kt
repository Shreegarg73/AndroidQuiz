package com.example.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val welcomeText=findViewById<TextView>(R.id.text1)
        val btn=findViewById<Button>(R.id.startbtn)
        val name=intent.getStringExtra("USER_NAME")
        welcomeText.text="WELCOME,$name!!"
        btn.setOnClickListener(){
            val intent= Intent(this,StartQuiz2::class.java)
            startActivity(intent)
        }

    }
}