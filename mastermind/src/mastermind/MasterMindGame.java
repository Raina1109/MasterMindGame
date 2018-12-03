package mastermind;

import java.util.*;
import static java.util.stream.Collectors.toList;

public class MasterMindGame {

    public enum Result{ INPOSITION, MATCH, NOMATCH }
    public enum Status{INPROGRESS, LOST, WON }

    private List<Integer> selectedColorIndices;
    protected int turnsTaken = 0;
    private Status status = Status.INPROGRESS;

    public void setColorSelectionIndices(List<Integer> colorIndicies) {
        selectedColorIndices = colorIndicies;
    }
                                                          
    public Map<Result, Integer> guess(List<Integer> guessIndices) {


        int inPosition = 0;
        int match = 0;

        for(int i = 0; i < selectedColorIndices.size(); i++) {
          if(selectedColorIndices.get(i) == guessIndices.get(i)) {
            inPosition++;
            continue;
          }

          if(guessIndices.contains(selectedColorIndices.get(i))) {
            match++;
          }
        }           

        updateGameStatus(inPosition);

        return Map.of(
          Result.INPOSITION, inPosition,
          Result.MATCH, match,
          Result.NOMATCH, selectedColorIndices.size() - inPosition - match);

    }

    private void updateGameStatus(int inPosition)
    {


        if(status != Status.INPROGRESS) return;

        turnsTaken++;

        if(turnsTaken == 20) {
          status = Status.LOST;
        }

        if(inPosition == 6) {
          status = status.WON;
        }
    }

    public Status getStatus() { return status; }

     public List<Integer> selectRandomDistinctColorIndices(int poolSize, int size, int seed)
      {
          return new Random(seed)
            .ints(0, poolSize)
            .map(value -> value % poolSize)
            .distinct()
            .boxed()
            .limit(size)
            .collect(toList()); 
      }
}