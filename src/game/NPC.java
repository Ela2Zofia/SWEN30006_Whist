package game;

import Filter.*;
import Select.*;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;

public class NPC{
    private SelectStrategy selectStrategy;
    private FilterStrategy filterStrategy;

    /**
     * Constructor
     * @param filter Filter type for the NPC
     * @param select Selector type for the NPC
     */
    public NPC(String filter, String select){

        // set filter type according to the properties
        switch (filter) {
            case "none":
                filterStrategy = new NoFilterStrategy();
                break;
            case "naive":
                filterStrategy = new NaiveLegalFilterStrategy();
                break;
            case "trump":
                filterStrategy = new TrumpSaveFilterStrategy();
                break;
            default:
                try {
                    throw (new FilterException("Invalid filter strategy: " + filter));
                } catch (FilterException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
        }

        // set selector type according to the properties
        switch (select) {
            case "random":
                selectStrategy = new RandomSelectStrategy();
                break;
            case "highest":
                selectStrategy = new HighRankSelectStrategy();
                break;
            case "smart":
                selectStrategy = new SmartSelectStrategy();
                break;
            default:
                try {
                    throw (new SelectException("Invalid select strategy: " + select));
                } catch (SelectException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
        }
    }

    /**
     * Do filtering and selection.
     *
     * @param hand Current Hand of the player
     * @param played Hand of the played card in this round, aka trick
     * @param trumps The trump suit of this round of the game
     * @param leadCard The lead suit of this round of the game
     * @return The chosen Card
     * @throws IOException
     */
    public Card play(Hand hand, Hand played, Suit trumps, Suit leadCard) throws IOException {
        return selectStrategy.select(filterStrategy.filter(hand, trumps, leadCard), played, trumps, leadCard);
    }

}
