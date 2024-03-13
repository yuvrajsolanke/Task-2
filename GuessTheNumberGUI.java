import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame implements ActionListener {
    private final int MIN_NUMBER = 1;
    private final int MAX_NUMBER = 100;
    private final int MAX_ATTEMPTS = 3;
    private final int MAX_ROUNDS = 3;

    private int randomNumber;
    private int attemptsLeft;
    private int roundsPlayed;
    private int score;

    private JLabel guessLabel, resultLabel, attemptsLabel, scoreLabel;
    private JTextField guessTextField;
    private JButton guessButton, newGameButton;

    public GuessTheNumberGUI() {
        setTitle("Guess the Number Game");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        guessLabel = new JLabel("Enter your guess (between 1 and 100):");
        add(guessLabel);

        guessTextField = new JTextField();
        add(guessTextField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        add(guessButton);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        add(newGameButton);

        resultLabel = new JLabel("");
        add(resultLabel);

        attemptsLabel = new JLabel("");
        add(attemptsLabel);

        scoreLabel = new JLabel("");
        add(scoreLabel);

        startNewGame();
    }

    private void startNewGame() {
        Random random = new Random();
        randomNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        attemptsLeft = MAX_ATTEMPTS;
        roundsPlayed = 0;
        score = 0;

        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        newGameButton.setEnabled(false);

        updateAttemptsLabel();
        updateScoreLabel();
        resultLabel.setText("");
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            int guess = Integer.parseInt(guessTextField.getText());
            attemptsLeft--;

            if (guess == randomNumber) {
                resultLabel.setText("Congratulations! You guessed the number!");
                score += attemptsLeft * 10;
                updateScoreLabel();
                endRound();
            } else if (guess < randomNumber) {
                resultLabel.setText("Too low. Try again.");
            } else {
                resultLabel.setText("Too high. Try again.");
            }

            if (attemptsLeft == 0) {
                resultLabel.setText("Sorry, you've run out of attempts. The correct number was: " + randomNumber);
                endRound();
            }

            updateAttemptsLabel();
            guessTextField.setText("");
        } else if (e.getSource() == newGameButton) {
            startNewGame();
        }
    }

    private void endRound() {
        roundsPlayed++;

        if (roundsPlayed == MAX_ROUNDS) {
            guessTextField.setEnabled(false);
            guessButton.setEnabled(false);
            newGameButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessTheNumberGUI game = new GuessTheNumberGUI();
            game.setVisible(true);
        });
    }
}
