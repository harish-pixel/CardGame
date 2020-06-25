package card_game.builders;

import card_game.Card;
import card_game.Player;
import card_game.data.Suit;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerBuilder {

    public static final ArrayList<Card> cardsList = new ArrayList<>();
    static {
        fillCardList();
    }

    public static void fillCardList() {
        cardsList.add(Card.buildCard(1, Suit.CLUBS));
        cardsList.add(Card.buildCard(3,Suit.DIAMONDS));
        cardsList.add(Card.buildCard(2, Suit.SPADES));

        cardsList.add(Card.buildCard(12,Suit.CLUBS));
        cardsList.add(Card.buildCard(6, Suit.DIAMONDS));
        cardsList.add(Card.buildCard(5, Suit.SPADES));

        cardsList.add(Card.buildCard(7, Suit.CLUBS));
        cardsList.add(Card.buildCard(9, Suit.DIAMONDS));
        cardsList.add(Card.buildCard(8, Suit.SPADES));

        cardsList.add(Card.buildCard(10, Suit.CLUBS));
        cardsList.add(Card.buildCard(12, Suit.HEARTS));
        cardsList.add(Card.buildCard(11, Suit.HEARTS));

    }
    public static Player singlePlayerBuilder() {

        Player player = Player.buildPlayer().setName("testPlayer1");
        player.dealCard(Card.buildCard(1, Suit.CLUBS));
        player.dealCard(Card.buildCard(1,Suit.DIAMONDS));
        player.dealCard(Card.buildCard(1, Suit.SPADES));
        return player;
    }

    public static ArrayList<Player> multiplePlayersBuilder() {

        Player player1 = Player.buildPlayer().setName("testPlayer1");
        Player player2 = Player.buildPlayer().setName("testPlayer2");
        Player player3 = Player.buildPlayer().setName("testPlayer3");
        Player player4 = Player.buildPlayer().setName("testPlayer4");

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        int count = 0;
        for(Player player: players) {
            for(int i=0; i<3; i++) {
                player.dealCard(cardsList.get(count));
                count++;
            }
        }
        return players;
    }

}
