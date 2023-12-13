package common;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PuzzleInput {

    public static List<List<Integer>> getInputLinesAsIntList(String fileName) {
        return getInputLines(fileName).stream().map(s->Arrays.stream(s.split(" ")).map(Integer::parseInt).toList()).toList();
    }

    @SneakyThrows
    public static List<String> getInputLines(String fileName) {
        return Arrays.stream(fileContents(fileName).split("\r\n")).toList();
    }

    @SneakyThrows
    public static char[][] getInputMatrix(String fileName) {
        String[] lines = fileContents(fileName).split("\r\n");
        int lineLength = lines[0].length();
        char[][] matrix = new char[lines.length][lineLength];
        for(int row = 0; row < lines.length; row++) {
            String line = lines[row];
            char[] lineChars = line.toCharArray();
            if( lineChars.length != lineLength )
                throw new IOException("input file isn't shaped like a matrix.");
            System.arraycopy(lineChars, 0, matrix[row], 0, lineChars.length);
        }
        return matrix;
    }

    @SneakyThrows
    public static String fileContents( String fileName ) {
        File input = new File(PuzzleInput.class.getClassLoader().getResource(fileName).getFile());
        return FileUtils.readFileToString(input, "UTF-8");
    }
}
