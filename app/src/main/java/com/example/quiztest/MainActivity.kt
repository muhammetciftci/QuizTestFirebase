package com.example.quiztest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiztest.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var myRef:DatabaseReference
    private lateinit var questModelList:ArrayList<QuestionModel>
    private lateinit var currentAnswer:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view:View = binding.root
        setContentView(view)


        getFirebaseData()



    }

    fun getFirebaseData()
    {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference()

        questModelList = arrayListOf<QuestionModel>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dataSnapShot:DataSnapshot in snapshot.children)
                {
                    var modelClass:QuestionModel?=dataSnapShot.getValue(QuestionModel::class.java)
                    if (modelClass != null) {
                        questModelList.add(modelClass)
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Model is empty",Toast.LENGTH_LONG)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }




    fun getNewQuestButton(view:View)
    {
        val random:Random = Random()
        // for Quest
        val randomNumber = random.nextInt(questModelList.size)
        // for Options(Choice)
        val choiceRandomNumber = random.nextInt(4)

        // for get Quest
        binding.questionTextview.text = getQuest(randomNumber)
        // for Unique options (4)
        when(choiceRandomNumber)
        {
            0 -> { binding.choice1.text = getChoice1(randomNumber)
                   binding.choice2.text = getChoice2(randomNumber)
                   binding.choice3.text = getChoice3(randomNumber)
                   binding.choice4.text = getChoice4(randomNumber) }

            1 -> {  binding.choice1.text = getChoice4(randomNumber)
                    binding.choice2.text = getChoice3(randomNumber)
                    binding.choice3.text = getChoice2(randomNumber)
                    binding.choice4.text = getChoice1(randomNumber) }

            2 -> {  binding.choice1.text = getChoice3(randomNumber)
                    binding.choice2.text = getChoice1(randomNumber)
                    binding.choice3.text = getChoice4(randomNumber)
                    binding.choice4.text = getChoice2(randomNumber) }

            3 -> {  binding.choice1.text = getChoice2(randomNumber)
                    binding.choice2.text = getChoice4(randomNumber)
                    binding.choice3.text = getChoice1(randomNumber)
                    binding.choice4.text = getChoice3(randomNumber) }

        }
        currentAnswer = getAnswer(randomNumber)
    }


    fun getQuest(a:Int): String
    {
        return questModelList.get(a).quest.toString()
    }
    fun getChoice1(a:Int):String
    {
        return  questModelList.get(a).c1.toString()
    }
    fun getChoice2(a:Int):String
    {
        return  questModelList.get(a).c2.toString()
    }
    fun getChoice3(a:Int):String
    {
        return  questModelList.get(a).c3.toString()
    }
    fun getChoice4(a:Int):String
    {
        return  questModelList.get(a).c4.toString()
    }
    fun getAnswer(a:Int):String
    {
        return questModelList.get(a).answer.toString()
    }

}


