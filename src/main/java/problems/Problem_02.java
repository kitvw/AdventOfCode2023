package problems;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Problem_02 {


    public static void main( String[] args ) {
        List<String> puzzleInput = PuzzleInput.getInputLines("input_02.txt");
        List<Game> gamesPlayed = new ArrayList<>();
        for( String line : puzzleInput ) {
            gamesPlayed.add(new Game(line));
        }
        int sumOfPossibleGames = gamesPlayed.stream().filter(Game::isPossible).reduce(0,(s,g)->s+g.getId(),Integer::sum);
        log.info("{}", sumOfPossibleGames);
        int sumOfPowers = gamesPlayed.stream().reduce(0,(s,g)->s+g.minimumPower(),Integer::sum);
        log.info("{}", sumOfPowers);
    }

    @Data
    private static class Game {
        @Data
        private static class ColoredCubes {
            private int blue;
            private int red;
            private int green;

            public int power() {
                return blue * red * green;
            }
        }
        private int id;
        private List<ColoredCubes> revelations = new ArrayList<>();
        private static final ColoredCubes actualCubes;
        static {
            actualCubes = new ColoredCubes();
            actualCubes.setRed(12);
            actualCubes.setGreen(13);
            actualCubes.setBlue(14);
        }

        public Game( String line) {
            String[] parts = line.split("[:;]");
            // part 0 has the game number,
            // the rest constitutes the list of revelations (colors separated by ',')
            this.id = Integer.parseInt(parts[0].split(" ")[1]);
            for(int i = 1; i < parts.length; i++ ) {
                ColoredCubes revelation = new ColoredCubes();
                String[] rawRevelations = parts[i].split(",");
                for( String colorValue : rawRevelations ){
                    colorValue = colorValue.trim();
                    String[] values = colorValue.split(" ");
                    switch( values[1] ) {
                        case "blue": revelation.setBlue(Integer.parseInt(values[0])); break;
                        case "red":  revelation.setRed( Integer.parseInt(values[0])); break;
                        case "green": revelation.setGreen(Integer.parseInt((values[0]))); break;
                    }
                }
                revelations.add(revelation);
            }
        }

        public boolean isPossible() {
            boolean possible = true;
            for( ColoredCubes revelation: revelations ){
                possible = possible && revelationPossible(revelation);
            }
            return possible;
        }

        private boolean revelationPossible(ColoredCubes revelation) {
            return revelation.getBlue() <= actualCubes.getBlue()
                    && revelation.getRed() <= actualCubes.getRed()
                    && revelation.getGreen() <= actualCubes.getGreen();
        }

        public int minimumPower() {
            int maxRed=0;
            int maxBlue=0;
            int maxGreen=0;
            for( ColoredCubes revelation : revelations){
                if(revelation.getRed() > maxRed)
                    maxRed = revelation.getRed();
                if(revelation.getBlue() > maxBlue)
                    maxBlue = revelation.getBlue();
                if(revelation.getGreen() > maxGreen)
                    maxGreen = revelation.getGreen();
            }

            log.debug("Game {}: {} *  {} * {} = {}",id,maxRed,maxBlue,maxGreen,maxRed*maxBlue*maxGreen);
            return maxRed * maxBlue * maxGreen;
        }
    }
}
