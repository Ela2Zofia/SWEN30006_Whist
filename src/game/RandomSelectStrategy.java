package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomSelectStrategy implements SelectStrategy{
    private Random random;

    // return random Card from Hand
    private Card randomCard(Hand hand) {
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    // return random Card from ArrayList
    private Card randomCard(ArrayList<Card> list) {
        int x = random.nextInt(list.size());
        return list.get(x);
    }
    @Override
    public Card select(Hand hand, Hand played, Suit trumps, Suit leadCard) throws IOException {
        random = new Random(PropertyReader.getInstance().getRandomState());
        return randomCard(hand);
    }
}
