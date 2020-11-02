package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public interface FilterStrategy {
    ArrayList<Card> filter(Hand hand, Suit trump, Suit lead);
}
