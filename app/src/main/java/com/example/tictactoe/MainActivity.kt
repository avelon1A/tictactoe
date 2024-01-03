package com.example.tictactoe

import android.app.Dialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onButtonClick(view: View) {
     val btuClicked:Button = view  as Button


        var cellid = 0
        when(btuClicked.id){
            R.id.button00 -> cellid = 1
            R.id.button01 -> cellid = 2
            R.id.button02 -> cellid = 3
            R.id.button10 -> cellid = 4
            R.id.button11 -> cellid = 5
            R.id.button12 -> cellid = 6
            R.id.button20 -> cellid = 7
            R.id.button21 -> cellid = 8
            R.id.button22 -> cellid = 9

        }
//        Log.d("cellid","$cellid")
//        Log.d("button_id", btuClicked.id.toString())
        playgame(cellid,btuClicked)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        } else {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
        
        btuClicked.animate().apply {
            scaleX(1.1f)
            scaleY(1.1f)
            duration = 200 // Duration in milliseconds
        }.withEndAction {
            // Reverse the animation to its original size
            btuClicked.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration = 200 // Duration in milliseconds
            }
        }
    }


    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    var activeplayer =1

    fun playgame(cellId:Int, btuClicked: Button){

        if (activeplayer == 1){
             btuClicked.text = "X"
            btuClicked.setBackgroundResource(R.drawable.rounded_green_button)
            player1.add(cellId)
            activeplayer = 2
         }
        else
         {
             btuClicked.text = "O"
             btuClicked.setBackgroundResource(R.drawable.rounded_red_button)
             player2.add(cellId)
             activeplayer = 1

         }
        btuClicked.isEnabled = false
        checkWinner()


    }
    fun checkWinner() {
        var winner = -1

        val winningCombinations = listOf(
            listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9),
            listOf(1, 4, 7), listOf(2, 5, 8), listOf(3, 6, 9),
            listOf(1, 5, 9), listOf(3, 5, 7)
        )

        for (combination in winningCombinations) {
            if (player1.containsAll(combination)) {
                winner = 1
                break
            } else if (player2.containsAll(combination)) {
                winner = 2
                break
            }
        }

        if (winner != -1) {
            // We have a winner
            val winnerText = if (winner == 1) "Player 1 wins!" else "Player 2 wins!"
            showWinnerDialog(winnerText)
            disableAllButtons()
        } else if (player1.size + player2.size == 9) {

            showWinnerDialog("It's a draw!")
            disableAllButtons()
        }
    }

    fun disableAllButtons() {
        val buttons = arrayOf<Button>(
            findViewById(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02),
            findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12),
            findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22)
        )

        for (button in buttons) {
            button.isEnabled = false
        }
    }
    fun showWinnerDialog(message: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.winner_dialog)

        val winnerMessage = dialog.findViewById<TextView>(R.id.winnerMessage)
        winnerMessage.text = message

        val restartButton = dialog.findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            resetGame()
            dialog.dismiss()
        }

        val exitButton = dialog.findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {
            finish() // Exit the app
        }

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        dialog.show()
    }

    fun resetGame() {
        // Reset game state and UI to start a new game
        player1.clear()
        player2.clear()
        activeplayer = 1
        // Reset buttons, enable them, clear text, and set background
        val buttons = arrayOf<Button>(
            findViewById(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02),
            findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12),
            findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22)
        )
        for (button in buttons) {
            button.isEnabled = true
            button.text = ""
            button.setBackgroundResource(R.drawable.rounded_button)
        }
    }
}

