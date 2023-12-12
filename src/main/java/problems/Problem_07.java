package problems;

import common.PuzzleInput;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class Problem_07 {

    public static void main(String[] args) {
        List<Hand> hands = new ArrayList<>(PuzzleInput.getInputLines("input_07.txt").stream().map(Hand::new).toList());
        Collections.sort(hands);
        long winnings = 0;
        for(int i = 0; i < hands.size(); i++) {
            log.info("{}", hands.get(i));
            long rank = i + 1;
            winnings += rank * hands.get(i).getBid();
        }
        log.info("winnings: {}", winnings);
    }

    private enum Card {
        Ace( 'A', 14 ),
        King( 'K', 13 ),
        Queen( 'Q', 12),
        Jack('J', 11),
        Ten('T', 10),
        Nine('9', 9),
        Eight('8', 8),
        Seven('7', 7),
        Six('6', 6),
        Five('5', 5),
        Four('4', 4),
        Three('3', 3),
        Two('2', 2);

        private final char symbol;
        private final int value;
        Card(char s, int v) {
            symbol = s;
            value = v;
        }
        public static Card fromChar( char s) {
            for(Card card : Card.values()) {
                if( card.symbol == s )
                    return card;
            }
            return null;
        }

        public int compare( Card card) {
            return Integer.compare(value, card.value);
        }

        public String toString() {
            return String.valueOf(symbol);
        }
    }

    private enum HandType {
        FiveOfAKind(7),
        FourOfAKind(6),
        FullHouse(5),
        ThreeOfAKind(4),
        TwoPair(3),
        OnePair(2),
        HighCard(1);
        private final int value;
        HandType( int v) {
            value = v;
        }

        public int compare( HandType type ) {
            return Integer.compare(value, type.value);
        }
    }
    @Data
    private static class Hand implements Comparable<Hand>  {
        private final long bid;
        private final List<Card> cards;
        private final HandType type;

        public Hand(String line) {
            String[] parts = line.split(" ");
            bid = Long.parseLong(parts[1]);
            cards = new ArrayList<>();
            for( char c : parts[0].toCharArray())
                cards.add(Card.fromChar(c));
            type = handType(cards);
        }

        public static HandType handType( List<Card> cards) {
            Map<Card, Integer> counts = new HashMap<>();
            for( Card card : cards ) {
                counts.putIfAbsent(card, 0);
                int count = counts.get(card);
                counts.put(card, count+1);
            }
            switch(counts.size()) {
                case 1:
                    return HandType.FiveOfAKind;
                case 2: // four of a kind or a full house
                    if( counts.containsValue(1) )
                        return HandType.FourOfAKind;
                    else
                        return HandType.FullHouse;
                case 3: //three of a kind or two pair
                    if( counts.containsValue(3) )
                        return HandType.ThreeOfAKind;
                    else
                        return HandType.TwoPair;
                case 4:
                    return HandType.OnePair;
                case 5:
                    return HandType.HighCard;
            }
            return null;
        }

        @Override
        public int compareTo(Hand hand) {
            if(!this.type.equals(hand.getType()))
                return this.type.compare(hand.getType());
            for(int i = 0; i < cards.size(); i++) {
                if(this.cards.get(i) != hand.cards.get(i))
                    return this.cards.get(i).compare(hand.cards.get(i));
            }
            return 0;
        }

        @Override
        public String toString() {
            return cards.stream().reduce("", (s,c)->s+c.toString(),String::concat) + " " + bid;
        }
    }
}
