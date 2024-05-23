import org.Math.Quiz.MathQuizProgram;
import org.junit.Before;
import org.junit.Test;

import javax.swing.Timer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MathQuizProgramTest {

    private MathQuizProgram mathQuizProgram;

    @Before
    public void setUp() {
        Timer timerMock = mock(Timer.class);
        mathQuizProgram = new MathQuizProgram();
        mathQuizProgram.timer = timerMock;
    }

    @Test
    public void testGenerateQuestion() {
        mathQuizProgram.generateQuestion();
        assertEquals(2, mathQuizProgram.totalQuestions);
    }

    @Test
    public void testCheckAnswerCorrect() {
        mathQuizProgram.correctAnswer = 0;
        mathQuizProgram.checkAnswer(0);
        assertEquals(1, mathQuizProgram.userScore);
        assertEquals(1, mathQuizProgram.correctCount);
    }

    @Test
    public void testCheckAnswerIncorrect() {
        mathQuizProgram.correctAnswer = 0;
        mathQuizProgram.checkAnswer(1);
        assertEquals(1, mathQuizProgram.incorrectCount);
    }

    @Test
    public void testResetGame() {

        mathQuizProgram.totalQuestions = 5;
        mathQuizProgram.userScore = 3;
        mathQuizProgram.correctCount = 2;
        mathQuizProgram.incorrectCount = 1;

        mathQuizProgram.resetGame();

        assertEquals(1, mathQuizProgram.totalQuestions);
        assertEquals(0, mathQuizProgram.userScore);
        assertEquals(0, mathQuizProgram.correctCount);
        assertEquals(0, mathQuizProgram.incorrectCount);

    }
}
