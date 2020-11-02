package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public class SmartSelectStrategy implements SelectStrategy {
    @Override
    public Card select(ArrayList<Card> hand, Hand played, Suit trump, Suit leadCard) {
        Card result;
        if (hand.isEmpty()){
            return null;
        }
        if (leadCard == null) {
            // if trump card available, choose the trump card with the highest rank, else play the highest rank
            Hand trumps = hand.extractCardsWithSuit(trump);
            if (!trumps.isEmpty()){
                result = trumps.reverseSort(Hand.SortType.RANKPRIORITY, false);
            }else{
                result = hand.reverseSort(Hand.SortType.RANKPRIORITY, false);
            }
            return hand.getCard(result.getSuit(),result.getRank());
        }else {
            Hand leadCards = played.extractCardsWithSuit(leadCard);
            Hand handLeads = hand.extractCardsWithSuit(leadCard);
            Hand trumpCards = played.extractCardsWithSuit(trump);
            Hand handTrumps = hand.extractCardsWithSuit(trump);

            // if the player has card of lead suit, return the highest ranked card of lead suit
            // if there is a card with higher rank, else return the lowest ranked card
            if (!handLeads.isEmpty()){
                if (leadCards.reverseSort(Hand.SortType.RANKPRIORITY,false).getRankId()
                        > handLeads.reverseSort(Hand.SortType.RANKPRIORITY,false).getRankId()){
                    result = handLeads.reverseSort(Hand.SortType.RANKPRIORITY,false);
                }else{
                    result = handLeads.sort(Hand.SortType.RANKPRIORITY,false);
                }
                return hand.getCard(result.getSuit(),result.getRank());
            }else{
                // if the player has no card of lead suit, check for cards of trump suit
                // return the highest ranked card of trump suit if there is one with higher rank
                if (!handTrumps.isEmpty()){
                    if (trumpCards.reverseSort(Hand.SortType.RANKPRIORITY,false).getRankId()
                            > handTrumps.reverseSort(Hand.SortType.RANKPRIORITY,false).getRankId() ){
                        result = handTrumps.reverseSort(Hand.SortType.RANKPRIORITY,false);
                        return hand.getCard(result.getSuit(),result.getRank());
                    }
                }
            }
            // if none of the requirements is met, return the lowest ranked card
            result = hand.sort(Hand.SortType.RANKPRIORITY, false);
            return hand.getCard(result.getSuit(),result.getRank());
        }

    }
}
