package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public class NoFilterStrategy implements FilterStrategy {
    @Override
    /**
     * Filter the Card according to rules
     * No filtering
     *
     * @param hand Current hand
     * @param trump The trump suit of this round of the game
     * @param lead The lead suit of this round of the game
     */
    public ArrayList<Card> filter(Hand hand, Suit trump, Suit lead) {
        return hand.getCardList();
    }
}
