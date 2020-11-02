package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;

public interface SelectStrategy {
    public Card select(Hand hand, Hand played, Suit trumps, Suit leadCard) throws IOException;
}
