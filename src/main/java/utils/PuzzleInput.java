package utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PuzzleInput {

    @SneakyThrows
    public static List<String> getInputLines(String fileName) {
        File input = new File(PuzzleInput.class.getClassLoader().getResource(fileName).getFile());
        String data = FileUtils.readFileToString(input, "UTF-8");
        return Arrays.stream(data.split("\r\n")).toList();
    }
}
