package Filter;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;
import java.util.ArrayList;

public class TrumpSaveFilter implements IFilterStrategy{
    private ArrayList<Card> filteredCard;
    private ArrayList<Card> cards;
    @Override
    public ArrayList<Card> Filter(Hand hand, Suit trump, Suit lead, Card leadCard) {
        cards=hand.getCardList();
        //If the NPC takes the lead, no filter

        //filter the cards in the lead suit
        if(cards.contains(leadCard)){
            cards.forEach(card->{
                if(card.equals(leadCard)){
                    filteredCard.add(card);
                    return;
                }
            });
        //if no lead suit, filter the cards in the trump suit.
        }else if(cards.contains(trump)){
            cards.forEach(card->{
                if(card.equals(trump)){
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
