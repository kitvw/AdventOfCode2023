package common;

import java.util.Arrays;

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
}
