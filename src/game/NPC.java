package game;


import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;

public class NPC{
    private SelectStrategy selector;
    private FilterStrategy filter;
    public NPC(String filter, String select){

        if (filter.equals("none")){
            selector = new RandomSelectStrategy();
        }else if (select.equals("highest")){
            selector = new HighRankSelectStrategy();
        }else if (select.equals("smart")){
            selector = new SmartSelectStrategy();
        }else{
            try {
                throw (new SelectException("Invalid select strategy: " + select));
            } catch (SelectException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (select.equals("random")){
            selector = new RandomSelectStrategy();
        }else if (select.equals("highest")){
            selector = new HighRankSelectStrategy();
        }else if (select.equals("smart")){
            selector = new SmartSelectStrategy();
        }else{
            try {
                throw (new SelectException("Invalid select strategy: " + select));
            } catch (SelectException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public Card select(Hand hand, Hand played, Suit trumps, Suit leadCard) throws IOException {
        return selector.select(hand, played, trumps, leadCard);
    }

}
