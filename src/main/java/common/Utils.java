package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static final int ASCII_INT_START = 48; // starting at 0
    public static final int ASCII_INT_PERIOD = 46;
    public static final int ASCII_INT_STAR = 42;

    public static boolean isInt( char c ) {
        return( (int) c >= ASCII_INT_START && (int) c < ASCII_INT_START + 10 );
    }

    public static char[][] subMatrix(char[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd) {
        char[][] subMatrix = new char[rowEnd-rowStart+1][colEnd-colStart+1];
        for( int r=0; r <subMatrix.length;r++){
            subMatrix[r] = Arrays.copyOfRange(matrix[r+rowStart], colStart, colEnd +1);
        }
        return subMatrix;
    }


    public static long lcm(List<Integer> numbers) {
        Map<Integer, Integer> primes = new HashMap<>();
        for(int number : numbers) {
            Map<Integer,Integer> factorization = primeFactors(number);
            for(int prime: factorization.keySet()) {
                primes.putIfAbsent(prime, 0);
                primes.put(prime, Math.max(primes.get(prime),factorization.get(prime)));
            }
        }

        long lcm = 1;
        for(int prime: primes.keySet()) {
            lcm *= (long) Math.pow(prime,primes.get(prime));
        }
        return lcm;
    }

    private static Map<Integer, Integer> primeFactors(int number) {
        Map<Integer,Integer> primes = new HashMap<>();
        int workingNumber = number;
        for( int prime = 2; prime <= workingNumber; prime++) {
            while(workingNumber%prime == 0) {
                primes.putIfAbsent(prime, 0);
                primes.put(prime, primes.get(prime)+1);
                workingNumber = workingNumber/prime;
            }
        }
        return primes;
    }
}
