package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class NaiveLegalFilter implements FilterStrategy {
    @Override
    public ArrayList<Card> filter(Hand hand, Suit trump, Suit lead) {
        ArrayList<Card> result = new ArrayList<>();
        //If the NPC takes the lead, no filter
        if (lead == null){
            return hand.getCardList();
        }
        result.addAll(hand.getCardsWithSuit(trump));
        result.addAll(hand.getCardsWithSuit(lead));
        //filter the cards in the lead suit and the cards in the trump suit.
        if (!result.isEmpty()){
            return result;
        }
        //If the NPC does not have cards in the lead suit nor in the trump suit,no filter
        else{
            return hand.getCardList();
        }
    }
}
