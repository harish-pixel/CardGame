package card_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Play class is used to begin the game after the cards are dealt.
 * The rules are as it was mentioned:
 *  1. A trail (three cards of the same number) is the highest possible combination.
 *  2. The next highest is a sequence (numbers in order, e.g., 4,5,6. A is considered to have a value of 1).
 *  3. The next highest is a pair of cards (e.g.: two Kings or two 10s).
 *  4. If all else fails, the top card (by number value wins).
 *  5. If the top card has the same value, Each of the tied players draws a single card from the deck until a winner is found.
 *  6. Only the newly drawn cards are compared to decide a tie. The top card wins a tie.
 * */


public class Play {

    /**
     * check if a trail (three cards of the same number).
     * A trail (three cards of the same number) is the highest possible combination.
     * */
    public Player checkForTrailCards (ArrayList<Player> players) {
        int trailingCountLimit;
        Player trailPlayer = null;
        int trailPlayerCardValue = 0;
        for(Player currentPlayer : players) {

            trailingCountLimit = currentPlayer.getDealtCards().size();
            boolean trailFound = false;
            for(int i=0; i<(trailingCountLimit-1); i++) {
                int nextCardIndex = i+1;
                if(currentPlayer.getDealtCard(i).getValue() == currentPlayer.getDealtCard(nextCardIndex).getValue()) {
                    if(nextCardIndex == trailingCountLimit-1) {
                        trailFound = true;
                    }
                }else{
                    break;
                }
            }
            int trailValue = currentPlayer.getDealtCard(0).getValue();
            if(trailFound && (trailPlayerCardValue < trailValue)) {

                trailPlayerCardValue = trailValue;
                trailPlayer = currentPlayer;
            }
        }
        return trailPlayer;
    }

    /** Sequence of numbers * */

    public ArrayList<Player> highestSequence(ArrayList<Player> players) {
        ArrayList<Player> playersList = new ArrayList<>();
        int sequenceCountLimit;
        int maxPlayerCardsSum = 0;

        for(Player currentPlayer : players) {

            int currentPlayerCardSum = 0;
            boolean pairFound = false;
            sequenceCountLimit = currentPlayer.getDealtCards().size();

            for(int i=0; i<(sequenceCountLimit-1); i++) {
                int currentCardValue = currentPlayer.getDealtCard(i).getValue();
                int nextCardIndex = i+1;
                int nextCardValue = currentPlayer.getDealtCard(nextCardIndex).getValue();
                if((currentCardValue < nextCardValue) && ((nextCardValue - currentCardValue) == 1)) {

                    currentPlayerCardSum += currentCardValue;
                    if(nextCardIndex == sequenceCountLimit-1) {
                        currentPlayerCardSum += nextCardValue;
                        pairFound = true;
                    }
                }else{
                    pairFound = false;
                    currentPlayerCardSum = 0;
                    break;
                }
            }
            if(pairFound) {
                if (maxPlayerCardsSum == currentPlayerCardSum) {
                    playersList.add(currentPlayer);
                } else if (maxPlayerCardsSum < currentPlayerCardSum) {
                    playersList.clear();
                    playersList.add(currentPlayer);
                    maxPlayerCardsSum = currentPlayerCardSum;
                }
            }
        }
        return playersList;
    }

    /** find pairs of similar cards */
    public ArrayList<Player> findPairs(ArrayList<Player> players) {
        ArrayList<Player> playersList = new ArrayList<>();
        int maxPairValue = 0;

        for(Player player : players) {

            //sorting to get the pair ahead
            Collections.sort(player.getDealtCards());

            int pairValue = 0;
            List<Card> playerCards = player.getDealtCards();
            boolean pairFound = false;

            // keep a note on playerCards.size() or using 3 as constants

            for(int i=1,j=0; i < playerCards.size(); i++) {
                if(playerCards.get(j).getValue() == playerCards.get(i).getValue()) {
                    pairFound = true;
                    pairValue = playerCards.get(i).getValue();
                }else{
                    j++;
                }
            }

            if(pairFound) {
                if(maxPairValue == pairValue) {
                    playersList.add(player);
                }else if(maxPairValue < pairValue) {
                    playersList.clear();
                    playersList.add(player);
                    maxPairValue = pairValue;
                }
            }
        }

        return playersList;
    }

    /** Find the card with max value among all the players and return
     *  single player in a set of multiple if there is a tie
     *  */
    public ArrayList<Player> findMaxCardValue(ArrayList<Player> players) {

        int highestCardValue = Integer.MIN_VALUE;
        ArrayList<Player> tiedPlayers = new ArrayList<>();

        for(Player player : players) {
            Collections.sort(player.getDealtCards());
            ArrayList<Card> playerCards = player.getDealtCards();

            if(playerCards.get(0).getValue() == highestCardValue) {
                tiedPlayers.add(player);
            }else if(playerCards.get(0).getValue() > highestCardValue) {
                tiedPlayers.clear();
                tiedPlayers.add(player);
                highestCardValue = playerCards.get(0).getValue();
            }
        }

        return tiedPlayers;
    }

    /**
     * Each tie player will deal one card from the deck and the highest card wins
     * */
    public Player tieBreaker(ArrayList<Player> tiePlayers, Deck deck) {

        ArrayList<Player> tieBreakerPlayers = new ArrayList<>();
        int tieCardMaxValue = 0;
        int rem = deck.cardsRemaining();
        if(rem < tiePlayers.size()) {
            return null;
        }
        // DealCard to each player.
        for(Player tiePlayer : tiePlayers) {
            Card newCard  = deck.dealCard();
            tiePlayer.dealCard(newCard);
            System.out.println(tiePlayer.getName() +" dealt a new card " + newCard.getValue() +
                    " of Suit: " + newCard.getSuit());
        }

        for(Player tiePlayer : tiePlayers) {

            List<Card> cards = tiePlayer.getDealtCards();
            int tieCardValue = cards.get(cards.size()-1).getValue();

            if(tieCardValue == tieCardMaxValue) {
                tieBreakerPlayers.add(tiePlayer);
            }else if(tieCardValue > tieCardMaxValue) {
                tieBreakerPlayers.clear();
                tieBreakerPlayers.add(tiePlayer);
                tieCardMaxValue = tieCardValue;
            }
        }

        if(tieBreakerPlayers.size() > 1) {
            playersLeft(tieBreakerPlayers);
            return tieBreaker(tieBreakerPlayers, deck);
        }

        return tieBreakerPlayers.get(0);
    }

    public static void playersLeft(ArrayList<Player> players) {
        System.out.println("Players left to play: ");
        for(Player player: players) {
            System.out.println(player.getName());
        }
    }
}
