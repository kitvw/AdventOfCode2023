package problems;

import common.PuzzleInput;
import common.Utils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class Problem_08 {
    private static final Map<String, Node> nodes = new HashMap<>();
    private static final String END = "ZZZ";
    private static final String START = "AAA";
    private static final char START_CHAR = 'A';
    private static final char END_CHAR = 'Z';

    public static void main(String[] args) {
        String input = PuzzleInput.fileContents("input_08.txt");
        String[] parts = input.split("\r\n\r\n");

        List<Direction> directions = parts[0].chars().mapToObj(Direction::fromInt).toList();
        for( String line : parts[1].split("\r\n")){
            Node node = new Node(line);
            nodes.put(node.getId(), node);
        }

        Node currentNode = nodes.get(START);
        int stepsToEnd = 0;
        int i = 0;
        while( true ) {
            currentNode = currentNode.move(directions.get(i++));
            stepsToEnd++;
            if(currentNode.getId().equals(END))
                break;
            if(i == directions.size())
                i=0;
        }
        log.info("steps to end: {}", stepsToEnd);

        List<Node> simultaneousNodes = nodes.keySet().stream().filter(x->x.charAt(2)==START_CHAR).map(nodes::get).toList();
        List<Integer> steps = new ArrayList<>();
        for(Node simNode : simultaneousNodes) {
            currentNode = simNode;
            int stepsForAll = 0;
            i = 0;
            while (true) {
                Direction nextDir = directions.get(i++);
                currentNode = currentNode.move(nextDir);
                stepsForAll++;
                if (currentNode.getId().charAt(2) == END_CHAR)
                    break;
                if (i == directions.size())
                    i = 0;
            }
            log.info("steps for simultaneous node {}: {}", simNode.getId(), stepsForAll);
            steps.add(stepsForAll);
        }
        log.info("LCM for simultaneous nodes: {}", Utils.lcm(steps));
    }

    private enum Direction {
        L('L'),
        R('R');
        private final char symbol;
        Direction( char c) {
            symbol = c;
        }

        public static Direction fromInt(int c) {
            return fromChar((char) c);
        }

        public static Direction fromChar( char c) {
            for( Direction dir : Direction.values()) {
                if( dir.symbol == c )
                    return dir;
            }
            return null;
        }
    }

    @Data
    private static class Node {
        String id;
        String left;
        String right;

        public Node(String line) {
            String[] parts = line.replaceAll("[ ()]", "").split("=");
            id = parts[0];
            String[] directions = parts[1].split(",");
            left = directions[0];
            right = directions[1];
        }


        public Node move( Direction dir) {
            return switch (dir) {
                case L -> nodes.get(left);
                case R -> nodes.get(right);
            };
        }
    }
}
