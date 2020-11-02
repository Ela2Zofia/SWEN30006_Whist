package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class SmartSelectStrategy implements SelectStrategy{
    @Override
    public Card select(Hand hand, Hand played, Suit trump, Suit leadCard) {
        if (hand.isEmpty()){
            return null;
        }
        if (leadCard == null) {
            // if trump card available, choose the trump card with the highest rank
            if (!hand.getCardsWithSuit(trump).isEmpty()){
                return hand.extractCardsWithSuit(trump).reverseSort(Hand.SortType.SUITPRIORITY, false);
            }else{
                return hand.reverseSort(Hand.SortType.SUITPRIORITY, false);
            }
        }else {
            Hand leadCards = played.extractCardsWithSuit(leadCard);



            Hand trumpCards = played.extractCardsWithSuit(trump);

            if (!trumpCards.isEmpty()){
                for(Card card : trumpCards.getCardList()){

                }
            }
        }




        return null;
    }
}
