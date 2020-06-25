package card_game;

import card_game.builders.PlayerBuilder;
import card_game.data.Suit;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;



public class PlayTest {

    public ArrayList<Player> players;
    public Player player;
    public Play play = new Play();

    private Deck deck = mock(Deck.class);

    @Before
    public void setup() {
        reset();
    }

    public void reset() {
        /*Setup for setting number trail for a player so that he could win by the first rule*/
        players = PlayerBuilder.multiplePlayersBuilder();
        player = PlayerBuilder.singlePlayerBuilder();
    }

    /** Rule1 test
    * A trail (three cards of the same number) is the highest possible combination
     * */
    @Test
    public void playerShouldWin_Having_TrailCardsTest() {

        players.get(0).getDealtCard(1).setValue(1);
        players.get(0).getDealtCard(2).setValue(1);
        player = players.get(0);
        Player player = play.checkForTrailCards(players);
        assertThat(player, is(notNullValue()));
        assertThat(player.getDealtCards().get(0).getValue(), equalTo(1));
    }

    /**
      Rule2 test
    * The next highest is a sequence (numbers in order, e.g., 4,5,6. A is considered to have a value of 1).
    */
    @Test
    public void playerShouldWin_Having_HighestSequenceNumberTest() {

        players.get(0).getDealtCard(1).setValue(2);
        players.get(0).getDealtCard(2).setValue(3);
        player = players.get(0);

        ArrayList<Player> playerArrayList = new ArrayList<Player>();
        playerArrayList.add(player);

        ArrayList<Player> playerHavingSequence = play.highestSequence(players);

        assertThat(playerHavingSequence.size(), equalTo(1));
        Player player = playerHavingSequence.get(0);
        assertThat(player.getDealtCard(0).getValue(), equalTo(1));
        assertThat(player.getDealtCard(1).getValue(), equalTo(2));
        assertThat(player.getDealtCard(2).getValue(), equalTo(3));
    }

    /**
     * Rule3 test
    The next highest is a pair of cards (e.g.: two Kings or two 10s).
     */
    @Test
    public void playerShouldWin_Having_HighestPairOfSameCardsTest()
    {
        players.get(3).getDealtCard(1).setValue(13);
        players.get(3).getDealtCard(2).setValue(13);
        player = players.get(3);

        ArrayList<Player> playerHavingPair = play.findPairs(players);
        assertThat(playerHavingPair.size(), equalTo(1));
        Player player = playerHavingPair.get(0);
        assertThat(player.getDealtCard(0).getValue(), equalTo(13));
        assertThat(player.getDealtCard(1).getValue(), equalTo(13));
    }

    /**
     * Rule4 test
    If all else fails, the top card (by number value wins).
     */
    @Test
    public void playerShouldWin_Having_HighestValuedCardTest()
    {
        players.get(2).getDealtCard(0).setValue(13);
        player = players.get(0);

        ArrayList<Player> playersHavingMaxCard = play.findMaxCardValue(players);
        assertThat(playersHavingMaxCard.size(), equalTo(1));
        Player player = playersHavingMaxCard.get(0);
        assertThat(player.getDealtCard(0).getValue(), equalTo(13));
    }

    /**
     * Rule5 test
     *   If the top card has the same value,
     *   each of the tied players draws a single card from the deck until a winner is found.
     *   Only the newly drawn cards are compared to decide a tie. The top card wins a tie.
     */
    @Test
    public void playerShouldWin_AfterTie_Having_DealtHighestValuedCard_Test()
    {
        players.remove(0);
        players.remove(1);
        when(deck.cardsRemaining()).thenReturn(40,38);
        when(deck.dealCard()).thenReturn(Card.buildCard(2, Suit.CLUBS),
                Card.buildCard(2, Suit.DIAMONDS),
                Card.buildCard(9, Suit.SPADES),
                Card.buildCard(10, Suit.DIAMONDS));

        Player player = play.tieBreaker(players, deck);
        assertThat(player.getDealtCard(4).getValue(), equalTo(10));
    }

}
