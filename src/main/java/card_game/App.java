package card_game;

import card_game.data.Suit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class App {

    public static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        logger.info("Started the game.");
        //create 4 players.
        //create a deck of cards.
        //give each player 3 cards randomly or out of randomly shuffled cards.
        //check for the rules to decide who is the victor

        Player player1 = new Player().setName("player1");
        Player player2 = new Player().setName("player2");
        Player player3 = new Player().setName("player3");
        Player player4 = new Player().setName("player4");
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));

        /** Creates a Deck of 52 cards.
         * */
        Deck deck = new Deck();
        deck.shuffleDeck();
        logger.info("Deck size after shuffling : " + deck.getSize());


        /**
         * As the game starts each player is given 3 cards each, 1 at a time.
         * */
        for(int i=0; i<3; i++) {
            player1.dealCard(deck.dealCard());
            player2.dealCard(deck.dealCard());
            player3.dealCard(deck.dealCard());
            player4.dealCard(deck.dealCard());
        }

        deck.displayDeck();

        logger.info("PLAYERS DETAIL : ");
        for(Player player: players) {
            logger.info("\n" +player.getName() + " has cards : ");
            for(Card card : player.getDealtCards()) {
                logger.info(card.getValue() + " of suit :" + card.getSuit());
            }

        }

        Player player = play(players,deck);

        if(player == null){
            System.out.println(":/:/  Sorry No one could win :/:/:/ ");

        }else{
            logger.info("$$$$$ "+ player.getName() + " WON THE GAME $$$$");
        }
    }

    public static Player play(ArrayList<Player> players, Deck deck) {

        Play playGame = new Play();
        ArrayList<Player> maxCardPlayers = new ArrayList<>();
        Player player = playGame.checkForTrailCards(players);

        if(player == null) {
            ArrayList<Player> sequenceCardPlayers = playGame.highestSequence(players);
            if(sequenceCardPlayers.size() == 1) {
                player = sequenceCardPlayers.get(0);
            }
        }

        if(player == null) {
            ArrayList<Player> pairsCardPlayers = playGame.findPairs(players);
            if(pairsCardPlayers.size() == 1) {
                player = pairsCardPlayers.get(0);
            }
        }
        if(player == null) {
            maxCardPlayers = playGame.findMaxCardValue(players);
            if(maxCardPlayers.size() == 1) {
                player = maxCardPlayers.get(0);
            }
        }

        if(player == null &&  maxCardPlayers.size() > 1) {
           player =  playGame.tieBreaker(maxCardPlayers, deck);
        }

        return player;
    }
}
