package problems;

import common.PuzzleInput;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class Problem_05 {
    public static void main(String[] args) {
        String[] input = PuzzleInput.fileContents("input_05.txt").split("\r\n\r\n");
        String[] seedInput = input[0].split(" ");
        List<Long> seeds = Arrays.stream(seedInput,1,seedInput.length).map(Long::parseLong).toList();
        List<AlmanacMap> maps = Arrays.stream(input, 1,input.length).map(AlmanacMap::new).toList();
        long minLocation = -1;
        for(int i = 0; i < seeds.size(); i++) {
            long seed = seeds.get(i++);
            long seedLength = seeds.get(i);
            log.info("starting to process {} seeds from: {}", seedLength, seed);
            for(long s = seed; s < seed + seedLength; s++) { // takes 50-60 minutes to run for full input
                long finalDestination = s;
                for (AlmanacMap map : maps) { // assumes the order of the maps in the input file.
                    long temp = finalDestination;
                    finalDestination = map.toDestination(finalDestination);
                    log.debug("({}, {}) - {}", temp, finalDestination, map.type);
                }
                if (minLocation == -1 || finalDestination <= minLocation) // hacks!
                    minLocation = finalDestination;
                log.debug("Seed {} ended up in location {}", s, finalDestination);
            }
        }
        log.info("Minimum Location: {}", minLocation);
    }

    private enum MapType {
        SEED_SOIL("seed-to-soil map"),
        SOIL_FERTILIZER( "soil-to-fertilizer map"),
        FERTILIZER_WATER( "fertilizer-to-water map" ),
        WATER_LIGHT("water-to-light map"),
        LIGHT_TEMPERATURE("light-to-temperature map"),
        TEMPERATURE_HUMIDITY("temperature-to-humidity"),
        HUMIDITY_LOCATION("humidity-to-location");

        private final String mapName;
        MapType(String name) {
            mapName = name;
        }

        public String getName() {
            return mapName;
        }

        public static MapType fromString(String s) {
            for(MapType m : MapType.values()) {
                if( m.getName().equalsIgnoreCase(s))
                    return m;
            }
            return null;
        }
    }

    @Data
    private static class Range {
        private long rangeStart;
        private long rangeEnd;

        public Range(long start, long length) {
            rangeStart = start;
            rangeEnd = start + length - 1;
        }

        public long getRangeLength() {
            return rangeEnd - rangeStart + 1;
        }

        public boolean contains(long x) {
            return x >= rangeStart && x <= rangeEnd;
        }
    }

    @Data
    private static class AlmanacMap {
        private MapType type;
        private Map<Range,Range> rangeLookup;

        public AlmanacMap(String input) {
            log.debug("input: \n{}", input);
            String[] lines = input.split("\r\n");
            type = MapType.fromString(lines[0].replaceAll(":", ""));
            rangeLookup = new HashMap<>();
            for(int i = 1; i < lines.length; i++) {
                List<Long> triplet = Arrays.stream(lines[i].split(" ")).map(Long::parseLong).toList();
                Range source = new Range(triplet.get(1),triplet.get(2));
                Range destination = new Range(triplet.get(0), triplet.get(2));
                rangeLookup.put(source, destination);
            }
        }

        public long toDestination(long previous) {
            for(Range range : rangeLookup.keySet()) {
                if(range.contains(previous))
                    return rangeLookup.get(range).rangeStart + (previous - range.getRangeStart());
            }
            return previous;
        }
    }
}
