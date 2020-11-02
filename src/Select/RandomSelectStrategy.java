package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.PropertyReader;
import game.Suit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomSelectStrategy implements SelectStrategy{
    private Random random;

    /**
     * @param list An ArrayList of Cards to be chosen from
     * @return A random Card from the list
     */
    // return random Card from ArrayList
    private Card randomCard(ArrayList<Card> list) {
        int x = random.nextInt(list.size());
        return list.get(x);
    }
    @Override
    /**
     * Select a Card from an ArrayList of Cards.
     * Randomly select a Card from the current hand.
     *
     * @param hand An ArrayList of filtered card
     * @param played Hand of the played card in this round, aka trick
     * @param trump The trump suit of this round of the game
     * @param leadCard The lead suit of this round of the game
     * @return The chosen Card
     */
    public Card select(ArrayList<Card> hand, Hand played, Suit trumps, Suit leadCard) throws IOException {
        random = new Random(PropertyReader.getInstance().getRandomState());
        return randomCard(hand);
    }
}
