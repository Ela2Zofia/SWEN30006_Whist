package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class NaiveLegalFilterStrategy implements FilterStrategy {
    @Override
    /**
     * Filter the Card according to rules
     * Get all the possible Cards with trump and lead suits
     * Do no filter if none of those suits exist in the current hand
     *
     * @param hand Current hand
     * @param trump The trump suit of this round of the game
     * @param lead The lead suit of this round of the game
     */
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
