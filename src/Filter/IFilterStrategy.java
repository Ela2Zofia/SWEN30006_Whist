package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public interface IFilterStrategy {
    public ArrayList<Card> Filter(Hand hand, Suit trump, Suit lead, Card leadCard);
}
