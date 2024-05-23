package org.Math.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class MathQuizProgram extends JFrame {
    private final JLabel questionLabel;
    private final JButton[] answerButtons;
    private final JLabel timerLabel;
    public int correctAnswer;
    public int userScore;
    public int totalQuestions;
    public int correctCount;
    public int incorrectCount;
    public Timer timer;

    public MathQuizProgram() {
        setTitle("Math Quiz Program");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel();
        add(questionLabel);

        timerLabel = new JLabel("Time Left: 30 seconds");
        add(timerLabel);

        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton();
            add(answerButtons[i]);
            final int index = i;
            answerButtons[i].addActionListener(e -> checkAnswer(index));
        }

        timer = new Timer(1000, new ActionListener() {
            int remainingTime = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime >= 0) {
                    timerLabel.setText("Time Left: " + remainingTime + " seconds");
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(MathQuizProgram.this, "Time's up! Showing result.");
                    showResultScreen();
                }

            }
        });

        generateQuestion();
    }

    public void generateQuestion() {
        timer.restart();

        Random random = new Random();
        double num1 = random.nextDouble( 5) + 1;
        double num2 = random.nextInt(5) + 1;
        int operator = random.nextInt(5);

        String operatorStr = "";
        double result = 0;
        switch (operator) {
            case 0:
                operatorStr = "+";
                result = num1 + num2;
                break;
            case 1:
                operatorStr = "-";
                result = num1 - num2;
                break;
            case 2:
                operatorStr = "*";
                result = num1 * num2;
                break;
            case 3:
                operatorStr = "/";
                result = num1 / num2;
                break;
            case 4:
                operatorStr = "!";
                result = factorial((int) num1);
                num2 = 0;
                break;
        }

        correctAnswer = random.nextInt(4);
        questionLabel.setText(String.format("%.2f %s %.2f = ?", num1, operatorStr, num2));

        // Array to keep track of generated answers
        double[] generatedAnswers = new double[4];
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswer) {
                answerButtons[i].setText(String.format("%.2f", result));
            } else {
                double wrongAnswer;
                do {
                    wrongAnswer = result + (random.nextDouble() * 10 - 5);
                } while (Math.abs(wrongAnswer - result) < 0.01 || Arrays.binarySearch(generatedAnswers, wrongAnswer) >= 0);
                generatedAnswers[i] = wrongAnswer;
                answerButtons[i].setText(String.format("%.2f", wrongAnswer));
            }
        }
        totalQuestions++;
    }

    public void checkAnswer(int index) {
        timer.stop(); // Stop timer on user selection

        if (index == correctAnswer) {
            userScore++;
            correctCount++;
            JOptionPane.showMessageDialog(this, "Correct! Your score: " + userScore);
        } else {
            incorrectCount++;
            JOptionPane.showMessageDialog(this, "Incorrect. Try again!");
        }
        generateQuestion();
    }

    private int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return n * factorial(n - 1);
    }

    private void showResultScreen() {
        double percentage = (double) correctCount / totalQuestions * 100;
        String message = String.format("Final Score: %d\nTotal Questions: %d\nCorrect Answers: %d\nIncorrect Answers: %d\nPercentage: %.2f%%",
                userScore, totalQuestions, correctCount, incorrectCount, percentage);
        int choice = JOptionPane.showConfirmDialog(this, message + "\nDo you want to play again?", "Quiz Result", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }


    public void resetGame() {
        userScore = 0;
        totalQuestions = 0;
        correctCount = 0;
        incorrectCount = 0;
        timer.stop();

        timer = new Timer(1000, new ActionListener() {
            int remainingTime = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime >= 0) {
                    timerLabel.setText("Time Left: " + remainingTime + " seconds");
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(MathQuizProgram.this, "Time's up! Showing result.");
                    showResultScreen();
                }

            }
        });

        generateQuestion();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MathQuizProgram().setVisible(true);
            }
        });
    }
}
