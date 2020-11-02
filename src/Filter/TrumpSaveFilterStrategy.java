package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class TrumpSaveFilterStrategy implements FilterStrategy {
    @Override
    /**
     * Filter the Card according to rules
     * Get all the Cards with lead suit, if none, get all the Cards with trump suit.
     * Do no filter if none of those suits exist in the current hand
     *
     * @param hand Current hand
     * @param trump The trump suit of this round of the game
     * @param lead The lead suit of this round of the game
     */
    public ArrayList<Card> filter(Hand hand, Suit trump, Suit lead) {
        //If the NPC takes the lead, no filter
        if (lead == null){
            return hand.getCardList();
        }
        //filter the cards in the lead suit
        if(!hand.getCardsWithSuit(lead).isEmpty()){
            return hand.getCardsWithSuit(lead);
        //if no lead suit, filter the cards in the trump suit.
        }else if(!hand.getCardsWithSuit(trump).isEmpty()){
            return hand.getCardsWithSuit(trump);
        //If the NPC does not have cards in the lead suit nor in the trump suit,no filter
        }else{
            return hand.getCardList();
        }
    }
}
