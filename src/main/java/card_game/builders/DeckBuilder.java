package card_game.builders;

import card_game.Card;
import card_game.Deck;
import card_game.Player;
import card_game.data.Suit;

import java.util.ArrayList;
import java.util.Random;

public class DeckBuilder {

    static class Deck
    {
        public static final int STANDARD_CARDS_DECK = 52;
        public int totalCardsDealt;
        public Card[] deck;

        public Deck(ArrayList<Card> cards) {
            int cardCount = 0;
            deck = new Card[STANDARD_CARDS_DECK];
            for(Suit suit : Suit.values()) {
                for(int i=1; i<=13; i++) {
                    Card card =  Card.buildCard(i, suit);
                    if(!cards.contains(card)) {
                        deck[cardCount] = card;
                        cardCount++;
                    }
                }
            }
            totalCardsDealt = 0;
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

        public int getTotalCardsDealt() {
            return totalCardsDealt;
        }

        public void setTotalCardsDealt(int totalCardsDealt) {
            this.totalCardsDealt = totalCardsDealt;
        }

        public void displayDeck() {
            for(Card card : deck){
                System.out.println("Card suit : " +card.getSuit()+ ", value: "+ card.getValue());
            }
        }
    }


    public static Deck buildDeck(ArrayList<Player> players) {
        Deck deck = new DeckBuilder.Deck(PlayerBuilder.cardsList);
        deck.setTotalCardsDealt(12);
        deck.shuffleDeck();
        return deck;
    }
}
