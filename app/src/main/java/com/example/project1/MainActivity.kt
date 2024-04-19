//Jūlija Sokolova 221RDB058
//es izmantoju https://www.youtube.com/watch?v=POFvcoRo3Vw&t=275s
package com.example.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.project1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    enum class Turn{
        CIRCLE,
        CROSS
    }
    private var currentPlayer = Turn.CROSS

    private var boardList = mutableListOf<Button>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var firstPlayerName: EditText
    private lateinit var secondPlayerName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_log)

        firstPlayerName = findViewById(R.id.player1Name)
        secondPlayerName = findViewById(R.id.player2Name)
        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startGame(it)
        }

    }
    fun startGame(view: View) {
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTurnLabel()
        Board()

    }
    private fun Board() {
        boardList.add(binding.but1)
        boardList.add(binding.but2)
        boardList.add(binding.but3)
        boardList.add(binding.but4)
        boardList.add(binding.but5)
        boardList.add(binding.but6)
        boardList.add(binding.but7)
        boardList.add(binding.but8)
        boardList.add(binding.but9)
    }


    fun tap1(view: View) {
        if(view !is Button)
            return
        addToBoard(view)
        if(VictoryCheck(CIRCLE)){
            result("${secondPlayerName.text} win!")
        }
        else if(VictoryCheck(CROSS)){
            result("${firstPlayerName.text} win!")
        }

        else if(fullBoard()){
            result("Draw!")
        }
    }

    private fun VictoryCheck(s:String): Boolean {
        //horizontal victory combo
        if(match(binding.but1,s) && match(binding.but2,s) && match(binding.but3,s)){
            return true
        }
        if(match(binding.but4,s) && match(binding.but5,s) && match(binding.but6,s)){
            return true
        }
        if(match(binding.but7,s) && match(binding.but8,s) && match(binding.but9,s)){
            return true
        }
        //vertical victory combo
        if(match(binding.but1,s) && match(binding.but4,s) && match(binding.but7,s)){
            return true
        }
        if(match(binding.but2,s) && match(binding.but5,s) && match(binding.but8,s)){
            return true
        }
        if(match(binding.but3,s) && match(binding.but6,s) && match(binding.but9,s)){
            return true
        }
        //diagonal victory combo
        if(match(binding.but1,s) && match(binding.but5,s) && match(binding.but9,s)){
            return true
        }
        if(match(binding.but3,s) && match(binding.but5,s) && match(binding.but7,s)){
            return true
        }
        return false
    }
    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String) {
        val dialogView = layoutInflater.inflate(R.layout.result_dialog, null)
        dialogView.findViewById<TextView>(R.id.resultTextView).text = title

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            dialog.dismiss()
            resetBoard()
            setContentView(R.layout.activity_main)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Board()
            setTurnLabel()
        }
    }
    private fun resetBoard(){
        for(button in boardList){
            button.text = ""
        }
        if(currentPlayer == Turn.CIRCLE)
            currentPlayer = Turn.CROSS
        else if(currentPlayer == Turn.CROSS)
            currentPlayer = Turn.CIRCLE

        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return
        if(currentPlayer == Turn.CIRCLE){
            button.text= CIRCLE
            currentPlayer = Turn.CROSS
        }
        else {
            button.text= CROSS
            currentPlayer = Turn.CIRCLE
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentPlayer == Turn.CROSS){
            turnText = "It's ${firstPlayerName.text} turn ($CROSS)"
        }
        else{
            turnText = "It's ${secondPlayerName.text} turn ($CIRCLE)"
        }
        binding.playerlabel.text = turnText
    }

    companion object{
        const val CIRCLE = "O"
        const val CROSS = "X"
    }
}