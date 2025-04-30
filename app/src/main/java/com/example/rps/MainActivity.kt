package com.example.rps

import android.os.Bundle
import android.view.View
import android.app.Activity
import com.example.rps.databinding.ActivityMainBinding

class MainActivity : Activity() {

    // initialize starting scores
    private var playerScore = 0
    private var computerScore = 0

    // bind
    private lateinit var binding: ActivityMainBinding

    // choices available for cpu player
    private val choices = arrayOf("Rock", "Paper", "Scissors")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set onClickListeners for player buttons
        binding.btnRock.setOnClickListener { playRound("Rock") }
        binding.btnPaper.setOnClickListener { playRound("Paper") }
        binding.btnScissors.setOnClickListener { playRound("Scissors") }
        binding.btnPlayAgain.setOnClickListener { resetGame() }
    }

    private fun playRound(playerChoice: String) {
        // generate random choice for CPU
        val computerChoice = choices.random()

        // update image previews of CPU and Player selection
        updateComputerImage(computerChoice)
        updatePlayerImage(playerChoice)

        // determine winner
        val result = determineWinner(playerChoice, computerChoice)

        // update score based on string returned from determineWinner()
        when (result) {
            "Player" -> {
                playerScore++
                binding.tvStatus.text = "You win this round! $playerChoice beats $computerChoice."
            }
            "Computer" -> {
                computerScore++
                binding.tvStatus.text = "Computer wins! $computerChoice beats $playerChoice."
            }
            else -> {
                binding.tvStatus.text = "It's a tie! You both chose $playerChoice."
            }
        }

        // Update score display
        binding.tvScore.text = "Player: $playerScore  Computer: $computerScore"

        // Check if game over
        if (playerScore == 10 || computerScore == 10) {
            endGame()
        }
    }

    private fun determineWinner(playerChoice: String, computerChoice: String): String {
        if (playerChoice == computerChoice) return "Tie"

        return when (playerChoice) {
            "Rock" -> if (computerChoice == "Scissors") "Player" else "Computer"
            "Paper" -> if (computerChoice == "Rock") "Player" else "Computer"
            "Scissors" -> if (computerChoice == "Paper") "Player" else "Computer"
            else -> "Tie"
        }
    }

    // update ImageView to show computer's choice
    private fun updateComputerImage(choice: String) {
        val drawableId = when (choice) {
            "Rock" -> R.drawable.rock
            "Paper" -> R.drawable.paper
            "Scissors" -> R.drawable.scissors
            else -> R.drawable.placeholder
        }
        binding.ivComputerChoice.setImageResource(drawableId)
    }

    // update ImageView to show player's choice
    private fun updatePlayerImage(choice: String) {
        val drawableId = when (choice) {
            "Rock" -> R.drawable.rock
            "Paper" -> R.drawable.paper
            "Scissors" -> R.drawable.scissors
            else -> R.drawable.placeholder
        }
        binding.ivPlayerChoice.setImageResource(drawableId)
    }

    // end game
    private fun endGame() {
        // disable player buttons
        binding.btnRock.isEnabled = false
        binding.btnPaper.isEnabled = false
        binding.btnScissors.isEnabled = false

        // announce winner
        binding.tvStatus.text = if (playerScore == 10) "You win the game!" else "Computer wins the game!"

        // show play again button
        binding.btnPlayAgain.visibility = View.VISIBLE
    }

    private fun resetGame() {
        // reset scores
        playerScore = 0
        computerScore = 0

        // reset UI elements
        binding.tvScore.text = "Player: 0  Computer: 0"
        binding.tvStatus.text = "Make your choice!"
        binding.ivComputerChoice.setImageResource(R.drawable.placeholder)
        binding.ivPlayerChoice.setImageResource(R.drawable.placeholder)

        // re-enable player buttons
        binding.btnRock.isEnabled = true
        binding.btnPaper.isEnabled = true
        binding.btnScissors.isEnabled = true

        // re-hide play again button
        binding.btnPlayAgain.visibility = View.GONE
    }
}
