package com.sJeon.bullseye


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.sJeon.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var currentRound = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startNewGame()

//        LISTENER (for button)
        binding.hitMeButton.setOnClickListener {
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
//            currentRound += 1
//            binding.gameRoundTextView?.text = currentRound.toString()
        }

        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

//        LISTENER (for seekbar)
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            Returns integer location of slider. fromUser boolean will return 'true' if action is performed by user and false if change performed by developer action
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun differenceAmount() = abs(targetValue - sliderValue)
//    private fun bonusPointsPerfectScore() = abs(differenceAmount() + 100)
//    private fun bonusPointsCloseScore() = abs(differenceAmount() + 50)

    private fun newTargetValue() = Random.nextInt(1,100)
    private fun pointsForCurrentRound(): Int {

//        immutable
        val maxScore = 100
//        immutable
        val difference = differenceAmount()
        var bonus = 0

        if (difference == 0) {
            bonus = 100
//            return bonusPointsPerfectScore()
        } else if (difference == 1) {
            bonus = 50
        }
//         else if (difference == differenceAmount() + 1) || (difference == differenceAmount() - 1) {
//            bonusPointsCloseScore()
//        }

        return maxScore - difference + bonus
    }

    private fun startNewGame() {
        totalScore = 0
        currentRound = 1
        sliderValue = 50
        targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = currentRound.toString()
        binding.targetTextView.text = targetValue.toString()
        binding.seekBar.progress = sliderValue
    }

    private fun showResult() {
//        Pop up message
//        immutable " "
//        val dialogTitle = getString(R.string.result_dialog_title)
        val dialogTitle = alertTitle()
        val dialogMessage = getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//        val dialogMessage = "The slider's value is $sliderValue"

//        Passes everything in Activity as argument (????? idk)
        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) {dialog, _ ->
            dialog.dismiss()
//            didn't add following code block
            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()

            currentRound += 1
            binding.gameRoundTextView?.text = currentRound.toString()
        }

        builder.create().show()
    }

    private fun alertTitle(): String {
        val difference = differenceAmount()

        val title: String = if (difference == 0) {
            getString(R.string.alert_title_1)
            } else if (difference < 5) {
                getString(R.string.alert_title_2)
        } else if (difference <= 10) {
            getString(R.string.alert_title_3)
        } else {
            getString(R.string.alert_title_4)
        }

        return title
    }
}
