package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public class NoFilter implements FilterStrategy {
    @Override
    public ArrayList<Card> filter(Hand hand, Suit trump, Suit lead) {
        return hand.getCardList();
    }
}
