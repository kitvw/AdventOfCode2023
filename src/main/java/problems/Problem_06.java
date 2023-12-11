package problems;

import common.PuzzleInput;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Problem_06 {
    public static void main(String[] args) {
        List<String> input = PuzzleInput.getInputLines("input_06.txt");
        String[] timeInput = input.get(0).replaceAll("[\s]+", " ").split(" ");
        String[] distanceInput = input.get(1).replaceAll("[\s]+", " ").split(" ");
        List<Race> races = new ArrayList<>();
        for(int i = 1; i < timeInput.length; i++) {
            races.add(new Race(Long.parseLong(timeInput[i]), Long.parseLong(distanceInput[i])));
        }
        long product = races.stream().map(Race::waysToBeatDistance).reduce(1L,(x, y)->x*y);
        log.info("result for multiple races: {}",product);

        Race race = new Race(
                Long.parseLong(input.get(0).replaceAll(" ","").split(":")[1]),
                Long.parseLong(input.get(1).replaceAll(" ","").split(":")[1])
        );
        log.info("as a single race: {}", race.waysToBeatDistance());
    }

    @Data
    private static class Race {
        private long time;
        private long distance;

        public Race(long t, long d) {
            time = t;
            distance = d;
        }

        public long waysToBeatDistance() {
            double determinant = Math.sqrt(time*time - 4*distance);
            long lowerBound = (long) Math.ceil((time - determinant)/2);
            long upperBound = (long) Math.floor((time + determinant)/2);
            if( lowerBound == (time - determinant)/2) { // adjust if ceiling/floor didn't change bound
                lowerBound++;
                upperBound--;
            }
            log.debug("Race({},{}): bounds[{},{}] - {}", time, distance, lowerBound, upperBound, upperBound-lowerBound+1);
            return upperBound - lowerBound + 1;
        }
    }
}
