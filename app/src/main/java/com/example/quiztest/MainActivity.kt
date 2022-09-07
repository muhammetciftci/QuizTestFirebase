package com.example.quiztest

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiztest.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.*
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var questModelList: ArrayList<QuestionModel>
    private lateinit var currentAnswer: String
    var score: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        getFirebaseData()


    }



    fun clickChoice1Button(view: View) {

        if (currentAnswer == binding.choice1Button.text) {
            buttonsClickableFalse()
            binding.choice1Button.setBackgroundColor(Color.GREEN)
            score++
            binding.questionScoreText.text = "Score: " + score
            Toast.makeText(applicationContext, "Correct answer.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "Wrong answer.", Toast.LENGTH_LONG).show()
            binding.choice1Button.setBackgroundColor(Color.RED)
        }

    }

    fun clickChoice2Button(view: View) {

        if (currentAnswer == binding.choice2Button.text) {
            buttonsClickableFalse()
            binding.choice2Button.setBackgroundColor(Color.GREEN)
            score++
            binding.questionScoreText.text = "Score: " + score
            Toast.makeText(applicationContext, "Correct answer.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "Wrong answer.", Toast.LENGTH_LONG).show()
            binding.choice2Button.setBackgroundColor(Color.RED)
        }

    }

    fun clickChoice3Button(view: View) {

        if (currentAnswer == binding.choice3Button.text) {
            buttonsClickableFalse()
            binding.choice3Button.setBackgroundColor(Color.GREEN)
            score++
            binding.questionScoreText.text = "Score: " + score
            Toast.makeText(applicationContext, "Correct answer.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "Wrong answer.", Toast.LENGTH_LONG).show()
            binding.choice3Button.setBackgroundColor(Color.RED)
        }

    }

    fun clickChoice4Button(view: View) {

        if (currentAnswer == binding.choice4Button.text) {
            buttonsClickableFalse()
            binding.choice4Button.setBackgroundColor(Color.GREEN)
            score++
            binding.questionScoreText.text = "Score: " + score
            Toast.makeText(applicationContext, "Correct answer.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "Wrong answer.", Toast.LENGTH_LONG).show()
            binding.choice4Button.setBackgroundColor(Color.RED)
        }

    }

    fun clickNewQuestionButton(view:View){
        getNewQuestion()
        buttonsClickableTrue()
    }

    fun getNewQuestion() {
        val random: Random = Random()

        val randomNumber = random.nextInt(questModelList.size)
        // for Options(Choice)
        val choiceRandomNumber = random.nextInt(4)

        // for get Quest
        binding.questionTextview.text = getQuestion(randomNumber)
        // for Unique options (4)
        when (choiceRandomNumber) {
            0 -> {
                binding.choice1Button.text = getChoice1(randomNumber)
                binding.choice2Button.text = getChoice2(randomNumber)
                binding.choice3Button.text = getChoice3(randomNumber)
                binding.choice4Button.text = getChoice4(randomNumber)
            }

            1 -> {
                binding.choice1Button.text = getChoice4(randomNumber)
                binding.choice2Button.text = getChoice3(randomNumber)
                binding.choice3Button.text = getChoice2(randomNumber)
                binding.choice4Button.text = getChoice1(randomNumber)
            }

            2 -> {
                binding.choice1Button.text = getChoice3(randomNumber)
                binding.choice2Button.text = getChoice1(randomNumber)
                binding.choice3Button.text = getChoice4(randomNumber)
                binding.choice4Button.text = getChoice2(randomNumber)
            }

            3 -> {
                binding.choice1Button.text = getChoice2(randomNumber)
                binding.choice2Button.text = getChoice4(randomNumber)
                binding.choice3Button.text = getChoice1(randomNumber)
                binding.choice4Button.text = getChoice3(randomNumber)
            }

        }
        currentAnswer = getAnswer(randomNumber)
    }


    fun getQuestion(a: Int): String {
        return questModelList.get(a).question.toString()
    }

    fun getChoice1(a: Int): String {
        return questModelList.get(a).c1.toString()
    }

    fun getChoice2(a: Int): String {
        return questModelList.get(a).c2.toString()
    }

    fun getChoice3(a: Int): String {
        return questModelList.get(a).c3.toString()
    }

    fun getChoice4(a: Int): String {
        return questModelList.get(a).c4.toString()
    }

    fun getAnswer(a: Int): String {
        return questModelList.get(a).answer.toString()
    }

    fun getFirebaseData() {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference()

        questModelList = arrayListOf<QuestionModel>()

        var count = 0;

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    count++
                    var modelClass: QuestionModel? =
                        dataSnapShot.getValue(QuestionModel::class.java)

                    if (modelClass != null) {
                        questModelList.add(modelClass)
                        if (count >= snapshot.childrenCount){
                            getNewQuestion()
                        }
                    }
                    else {
                        Toast.makeText(applicationContext, "Model is empty", Toast.LENGTH_LONG)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    fun buttonsClickableFalse(){
        binding.choice1Button.isClickable = false
        binding.choice2Button.isClickable = false
        binding.choice3Button.isClickable = false
        binding.choice4Button.isClickable = false
    }

    fun buttonsClickableTrue(){
        binding.choice1Button.isClickable = true
        binding.choice2Button.isClickable = true
        binding.choice3Button.isClickable = true
        binding.choice4Button.isClickable = true

        binding.choice1Button.setBackgroundColor(Color.BLUE)
        binding.choice2Button.setBackgroundColor(Color.BLUE)
        binding.choice3Button.setBackgroundColor(Color.BLUE)
        binding.choice4Button.setBackgroundColor(Color.BLUE)


    }

}


