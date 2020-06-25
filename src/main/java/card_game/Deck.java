package card_game;

import card_game.data.Suit;
import java.util.Random;

public class Deck {

    public static final int STANDARD_CARDS_DECK = 52;
    private int totalCardsDealt;
    private Card[] deck;

    Deck() {
        int cardCount = 0;
        deck = new Card[STANDARD_CARDS_DECK];
        for(Suit suit : Suit.values()) {
            for(int i=1; i<=13; i++) {
                deck[cardCount] = new Card(i, suit);
                cardCount++;
            }
        }
        totalCardsDealt = 0;
    }

    public static Deck buildDeck(){
       return new Deck();
    }

    public void shuffleDeck() {
        Random random = new Random();
        for(int i=0; i<(deck.length - totalCardsDealt); i++) {

            //generate random number wrto positions
            int rand = i + random.nextInt(STANDARD_CARDS_DECK - i);

            Card tempCard = deck[rand];
            deck[rand]= deck[i];
            deck[i] = tempCard;
        }
        totalCardsDealt = 0;
    }

    public Card dealCard() {
        if(totalCardsDealt == deck.length) {
            //No more cards left on the deck.
            throw new IllegalStateException("The Deck is empty.");
        }
        totalCardsDealt++;
        return deck[totalCardsDealt - 1];
    }

    public int cardsRemaining() {
        return deck.length - totalCardsDealt;
    }

    public void displayDeck() {
        for(Card card : deck){
            System.out.println("Card suit : " +card.getSuit()+ ", value: "+ card.getValue());
        }
    }

    public int getSize() {
        return deck.length;
    }
}
