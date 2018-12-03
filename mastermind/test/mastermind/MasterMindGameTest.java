package mastermind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static mastermind.MasterMindGame.Status.INPROGRESS;
import static mastermind.MasterMindGame.Status.LOST;
import static mastermind.MasterMindGame.Status.WON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import static mastermind.MasterMindGame.Result.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MasterMindGameTest {
    private MasterMindGame masterMindGame;

    @BeforeEach
    void init() {
        masterMindGame = new MasterMindGame();
        masterMindGame.setColorSelectionIndices(List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    void canary() {
        assert(true);
    }
      
    @Test
    void playerMakesIncorrectGuess() {
        assertEquals(Map.of(
          INPOSITION, 0,
          MATCH, 0,
          NOMATCH, 6),
          masterMindGame.guess(List.of(0, 0, 0, 0, 0, 0)));
    }

    @Test
    void playerMakesCorrectGuess() {
        assertEquals(Map.of(
          INPOSITION, 6,
          MATCH, 0,
          NOMATCH, 0),
          masterMindGame.guess(List.of(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void oneColorNonPositionMatch() {
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 1,
                NOMATCH, 5),
                masterMindGame.guess(List.of(7, 8, 9, 0, 1, 0)));
    }

    @Test
    void twoColorNonPositionMatch() {
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 2,
                NOMATCH, 4),
                masterMindGame.guess(List.of(7, 8, 9, 0, 1, 2)));
    }

    @Test
    void oneColorPositionOneColorNonPosition() {
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 1,
                NOMATCH, 4),
                masterMindGame.guess(List.of(1, 5, 7, 8, 9, 0)));
    }

    @Test
    void twoSameColorInSelectionOnePositionMatch() {
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 0,
                NOMATCH, 5),
                masterMindGame.guess(List.of(1, 1, 7, 8, 9, 0)));
    }

    @Test
    void twoSampleColorInSelectionFirstInNonPositionSecondInPosition() {
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 0,
                NOMATCH, 5),
                masterMindGame.guess(List.of(2, 2, 0, 0, 0, 0)));
    }

    @Test
    void gameStatusOnStart(){
        assertEquals(INPROGRESS, masterMindGame.getStatus());
    }

    @Test
    void gameStatusAfterAWrongGuess() {
        masterMindGame.guess(List.of(0, 0, 0, 0, 0, 0));
        assertEquals(INPROGRESS, masterMindGame.getStatus());
    }
    @Test
    void gameStatusAfterAWin(){
        masterMindGame.guess(List.of(1, 2, 3, 4, 5, 6));
        assertEquals(WON, masterMindGame.getStatus());
    }

    @Test
    void gameStatusAfterALoss(){
        masterMindGame.turnsTaken = 19;
        masterMindGame.guess(List.of(0, 0, 0, 0, 0, 0));
        assertEquals(LOST, masterMindGame.getStatus());
    }


    @Test
    void gameStatusStayWonOnceWon() {
        masterMindGame.guess(List.of(1, 2, 3, 4, 5, 6));
        masterMindGame.guess(List.of(0, 0, 0, 0, 0, 0));
        assertEquals(WON, masterMindGame.getStatus());
    }

    @Test
    void gameStatusStayLostOnceLost() {
        masterMindGame.turnsTaken = 19;
        masterMindGame.guess(List.of(0, 0, 0, 0, 0, 0));
        masterMindGame.guess(List.of(1, 2, 3, 4, 5, 6));
        assertEquals(LOST, masterMindGame.getStatus());
    }
           
    @Test
    void selectedColorsSixValues() {
        assertEquals(6, masterMindGame.selectRandomDistinctColorIndices(10,6, 1).size());
    }

    @Test
    void selectedColorsDistinct() {

        Set<Integer> distinctIndicies =
         new HashSet<>(masterMindGame.selectRandomDistinctColorIndices(10,6, 1));
        assertEquals(6, distinctIndicies.size());
    }

    
    @Test
    void selectedColorsAreSameForSameRandomizerSeed() {
      assertEquals(
        masterMindGame.selectRandomDistinctColorIndices(10, 6, 1),
        masterMindGame.selectRandomDistinctColorIndices(10, 6, 1)
      );
    }

    @Test
    void selectedColorsAreDifferentForDifferentRandomizerSeed() {
      assertNotEquals(
        masterMindGame.selectRandomDistinctColorIndices(10, 6, 1),
        masterMindGame.selectRandomDistinctColorIndices(10, 6, 2)
      );
    }

    @Test
    void selectRandomDistinctColorIndicesForLargerPoolSize() {
      assertEquals(15, masterMindGame.selectRandomDistinctColorIndices(20, 15, 2).size());
    }
}