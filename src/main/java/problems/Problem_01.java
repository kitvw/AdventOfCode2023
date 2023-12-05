package problems;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import utils.PuzzleInput;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Log4j2
public class Problem_01 {
    private static final int ASCII_INT_START = 48; // starting at 0
    private static final Map<String, String> numMap = new HashMap<>();
    static {
        numMap.put( "one", "1" );
        numMap.put( "two", "2" );
        numMap.put( "three", "3" );
        numMap.put( "four", "4" );
        numMap.put( "five", "5" );
        numMap.put( "six", "6" );
        numMap.put( "seven", "7" );
        numMap.put( "eight", "8" );
        numMap.put( "nine", "9" );
    }

    @SneakyThrows
    public static void main(String[] args) {
        List<String> puzzleInput = PuzzleInput.getInputLines("input_01.txt");
        int total = 0;
        for (String line : puzzleInput) {
            total += extractCalibration(line);
        }
        log.info("total: {}", total);
    }

    private static int extractCalibration( String line ) {
        String firstNum = "";
        String lastNum = "";
        char[] lineChars = line.toCharArray();
        for(int i = 0; i < lineChars.length; i++) {
            char c = line.charAt(i);
            if( isInt(c) ) {
                lastNum = Character.toString(c);
                if(firstNum.isEmpty())
                    firstNum = Character.toString(c);
            } else {
                //check if spelled out
                for( String num : numMap.keySet() ) {
                    if( line.startsWith(num, i) ){
                        lastNum = numMap.get( num );
                        if(firstNum.isEmpty())
                            firstNum = numMap.get( num );
                    }
                }
            }
        }
//        for( String num : numMap.keySet() ) {
//            line = line.replaceAll(num, numMap.get(num));
//        }
//        List<String> parts = Arrays.stream(line.split("[a-zA-Z]*"))
//                .filter(x -> !x.isEmpty())
//                .toList();
//        String firstNum = parts.get(0);
//        firstNum = String.valueOf(firstNum.charAt(0));
//        String secondNum = parts.get(parts.size()-1);
//        secondNum = String.valueOf(secondNum.charAt(secondNum.length()-1));
        String number = firstNum + lastNum;
        log.debug("{} - {}", number, line);
        return Integer.parseInt(number);
    }

    private static boolean isInt( char c ) {
        return( (int) c >= ASCII_INT_START && (int) c < ASCII_INT_START + 10 );
    }
}
