package problems;

import common.PuzzleInput;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Problem_09 {
    public static void main(String[] args) {
        List<List<Integer>> input = PuzzleInput.getInputLinesAsIntList("input_09.txt");
        int nextSum = input.stream().map(Problem_09::nextElement).reduce(0, Integer::sum);
        log.info("sum of next elements: {}",nextSum);

        int prevSum = input.stream().map(Problem_09::prevElement).reduce(0,Integer::sum);
        log.info("sum of prev elements: {}", prevSum);
    }

    private static int nextElement(List<Integer> sequence) {
        int first = sequence.get(0);
        if(sequence.stream().reduce(true,(b,i)->b&&i==first,Boolean::logicalAnd))
            return first;

        List<Integer> lowerSequence = new ArrayList<>();
        for(int i = 1; i < sequence.size(); i++) {
            lowerSequence.add(sequence.get(i) - sequence.get(i-1));
        }
        return sequence.get(sequence.size()-1) + nextElement(lowerSequence);
    }

    private static int prevElement(List<Integer> sequence) {
        int first = sequence.get(0);
        if(sequence.stream().reduce(true,(b,i)->b&&i==first,Boolean::logicalAnd))
            return first;

        List<Integer> lowerSequence = new ArrayList<>();
        for(int i = 1; i < sequence.size(); i++) {
            lowerSequence.add(sequence.get(i) - sequence.get(i-1));
        }
        return first - prevElement(lowerSequence);
    }
}
