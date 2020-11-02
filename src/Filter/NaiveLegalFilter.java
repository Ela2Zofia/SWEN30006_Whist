package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class NaiveLegalFilter implements IFilterStrategy{
    private ArrayList<Card> filteredCard;
    private ArrayList<Card> cards;
    @Override
    public ArrayList<Card> Filter(Hand hand, Suit trump, Suit lead, Card leadCard) {
        cards=hand.getCardList();
        //If the NPC takes the lead, no filter

        //filter the cards in the lead suit and the cards in the trump suit.
        if(cards.contains(trump)||cards.contains(leadCard)){
            cards.forEach(card->{
                if(card.equals(trump)||card.equals(leadCard)){
                    filteredCard.add(card);
                    return;
                }
            });
        //If the NPC does not have cards in the lead suit nor in the trump suit,no filter
        }else{
            filteredCard=cards;
        }
        return filteredCard;
    }
}
