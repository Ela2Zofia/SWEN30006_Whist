package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.io.IOException;
import java.util.ArrayList;

public interface SelectStrategy {
    public Card select(ArrayList<Card> hand, Hand played, Suit trumps, Suit leadCard) throws IOException;
}
