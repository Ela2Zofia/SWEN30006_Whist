package game;

import Filter.*;
import Select.*;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;

public class NPC{
    private SelectStrategy selectStrategy;
    private FilterStrategy filterStrategy;
    public NPC(String filter, String select){

        // set filter type according to the properties
        if (filter.equals("none")){
            filterStrategy = new NoFilter();
        }else if (filter.equals("naive")){
            filterStrategy = new NaiveLegalFilter();
        }else if (filter.equals("trump")){
            filterStrategy = new TrumpSaveFilter();
        }else{
            try {
                throw (new FilterException("Invalid filter strategy: " + filter));
            } catch (FilterException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        // set selector type according to the properties
        if (select.equals("random")){
            selectStrategy = new RandomSelectStrategy();
        }else if (select.equals("highest")){
            selectStrategy = new HighRankSelectStrategy();
        }else if (select.equals("smart")){
            selectStrategy = new SmartSelectStrategy();
        }else{
            try {
                throw (new SelectException("Invalid select strategy: " + select));
            } catch (SelectException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public Card play(Hand hand, Hand played, Suit trumps, Suit leadCard) throws IOException {
        return selectStrategy.select(filterStrategy.filter(hand, trumps, leadCard), played, trumps, leadCard);
    }

}
