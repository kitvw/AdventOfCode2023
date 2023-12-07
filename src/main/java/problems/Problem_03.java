package problems;

import common.Utils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import common.PuzzleInput;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Log4j2
public class Problem_03 {
    @Data
    private static class StarIndex {
        private final int row;
        private final int col;
        public StarIndex( int r, int c) {
            row = r;
            col = c;
        }
    }
    public static void main(String[] args) {
        char[][] input = PuzzleInput.getInputMatrix("input_03.txt");
        log.debug("\n{}", Arrays.deepToString(input).replaceAll("], \\[", "],\n ["));
        Map<StarIndex, List<Integer>> possibleGears = new HashMap<>();
        for(int row=0; row < input.length; row++){
            for(int col=0; col < input[0].length; col++){
                if(isStar(input[row][col]))
                    possibleGears.put(new StarIndex(row, col), new ArrayList<>());
            }
        }
        List<Integer> partNumbers = new ArrayList<>();
        for(int row = 0; row < input.length; row++) {
            for(int col = 0; col < input[0].length; col++) {
                // need to check surrounding cells for a number
                // maybe remove a number if it isn't touching a special character??
                char c = input[row][col];
                StringBuilder number = new StringBuilder();
                int numberIndexStart = col;
                while (Utils.isInt(c)) {
                    number.append(c);
                    if(col == input[0].length-1)
                        break;
                    c = input[row][++col];
                }
                int numberIndexEnd = col-1;

                int rowStart = max(0,row-1);
                int rowEnd = min(input.length-1,row+1);
                int colStart = max(0,numberIndexStart-1);
                int colEnd = min(input[0].length-1, numberIndexEnd+1);
                char[][] subMatrix = Utils.subMatrix(input, rowStart, rowEnd, colStart, colEnd);
                if( !number.isEmpty() && containsSpecialChar(subMatrix)) {
                    partNumbers.add(Integer.parseInt(number.toString()));
                    for(StarIndex starIndex: nearbyStars(subMatrix, rowStart, colStart)) {
                        List<Integer> g = possibleGears.get(starIndex);
                        possibleGears.get(starIndex).add(Integer.parseInt(number.toString()));
                    }
                }
            }
        }
        int partTotal = partNumbers.stream().reduce(0, Integer::sum);
        log.info("parts: {}", partTotal);

        int gearRatioTotals = possibleGears.values().stream()
                .filter(l->l.size()==2)
                .map(pair-> pair.get(0) * pair.get(1) )
                .reduce(0,Integer::sum);
        log.info("gears: {}", gearRatioTotals);
    }

    private static boolean containsSpecialChar(char[][] matrix ) {
        for (char[] chars : matrix) {
            for (int c = 0; c < matrix[0].length; c++) {
                if (((int) chars[c] != Utils.ASCII_INT_PERIOD) && !Utils.isInt(chars[c]))
                     return true;
            }
        }
        return false;
    }

    private static List<StarIndex> nearbyStars(char[][] matrix, int rowOffset, int colOffset) {
        List<StarIndex> indices = new ArrayList<>();
        for(int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix[0].length; c++) {
                if( isStar(matrix[r][c]) )
                    indices.add(new StarIndex(r+rowOffset,c+colOffset));
            }
        }
        return indices;
    }

    private static boolean isStar(char c) {
        return (int) c == Utils.ASCII_INT_STAR;
    }
}
