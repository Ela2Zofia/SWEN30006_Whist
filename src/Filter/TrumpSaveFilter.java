package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class TrumpSaveFilter implements FilterStrategy {
    private Hand result;
    @Override
    public ArrayList<Card> filter(Hand hand, Suit trump, Suit lead) {
        //If the NPC takes the lead, no filter
        if (lead == null){
            return hand.getCardList();
        }
        //filter the cards in the lead suit
        if(!hand.extractCardsWithSuit(lead).isEmpty()){
            return hand.getCardsWithSuit(lead);
        //if no lead suit, filter the cards in the trump suit.
        }else if(!hand.extractCardsWithSuit(trump).isEmpty()){
            return hand.getCardsWithSuit(trump);
        //If the NPC does not have cards in the lead suit nor in the trump suit,no filter
        }else{
            return hand.getCardList();
        }
    }
}
