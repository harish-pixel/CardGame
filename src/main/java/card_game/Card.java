package card_game;

import card_game.data.Suit;

import java.util.Objects;


public class Card implements Comparable<Card> {

    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    private int value;
    private Suit suit;


    Card(int value, Suit suit) {
        if(value < 1 || value > 13) {
            throw new IllegalArgumentException("Illegal card value.");
        }
        if (suit != Suit.HEARTS && suit != Suit.SPADES && suit != Suit.DIAMONDS && suit != Suit.CLUBS) {
            throw new IllegalArgumentException("Illegal card Suit.");
        }
        this.value = value;
        this.suit = suit;
    }

    public static Card buildCard(int value, Suit suit){
        return new Card(value, suit);
    }

    @Override
    public int compareTo(Card otherCard) {

        int currentCardVal = this.getValue();
        int otherCardValue = otherCard.getValue();

        if(currentCardVal == otherCardValue)
            return 0;
        else if(currentCardVal < otherCardValue)
            return 1;
        else
            return -1;
    }

    public int getValue() {
        return value;
    }

    public Card setValue(int value) {
        this.value = value;
        return this;
    }

    public Suit getSuit() {
        return suit;
    }
    
    
    public Card setSuit(Suit suit) {
        this.suit = suit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
}
