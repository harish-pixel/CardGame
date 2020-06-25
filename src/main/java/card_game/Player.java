package card_game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public ArrayList<Card> dealtCards;
    public String name;

    /**
    * Initially the player will have no cards.
     */
    Player() {
        dealtCards = new ArrayList<>();
    }

    public static Player buildPlayer() {
        return new Player();
    }

    /**
     * The player is given a card to play.
     * This card will be added to already existing cards with the player.
     */
    public void dealCard(Card card) {
        if(card == null) {
            throw new NullPointerException("Card cannot be null.");
        }
        dealtCards.add(card);
    }
    /** Get name of the player.
     * */
    public String getName() {
        return name;
    }

    /** Get name of the player.
     * */
    public Player setName(String name) {
        this.name = name;
        return this;
    }

    /**
     Removes a card from the players hand.
     */
    public void removeCard(Card card) {
        dealtCards.remove(card);
    }

    public void removeCard(int position) {
        if (position < 0 || position >= dealtCards.size())
            throw new IllegalArgumentException("No card found at: " + position);
        dealtCards.remove(position);
    }

    /**
     * Returns the number of cards with the player.
     */
    public int getCardCount() {
        return dealtCards.size();
    }

    public ArrayList<Card> getDealtCards() {
        return dealtCards;
    }

    /**
     Returns a card from the players hand given a position.
     */
    public Card getDealtCard(int position) {
        if(position < 0 || position > 12) {
            throw new IllegalArgumentException("No card found at this position.");
        }
        return dealtCards.get(position);
    }
}
