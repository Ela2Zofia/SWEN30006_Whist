package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public class SmartSelectStrategy implements SelectStrategy {
    @Override
    public Card select(ArrayList<Card> hand, Hand played, Suit trump, Suit leadCard) {
        Card result;
        played.sort(Hand.SortType.SUITPRIORITY, false);
        hand.get(0).getHand().setSortType(Hand.SortType.RANKPRIORITY);
        hand.sort(Card::compareTo);
        if (hand.isEmpty()){
            return null;
        }
        if (leadCard == null) {
            // if trump card available, choose the trump card with the highest rank, else play the highest rank
            ArrayList<Card> trumps = new ArrayList<>();
            for (Card c : hand){
                if (c.getSuit() == trump){
                    trumps.add(c);
                }
            }
            if (!trumps.isEmpty()){
                result = trumps.get(0);
            }else{
                result = hand.get(0);
            }
            return result;
        }else {
            ArrayList<Card> leadCards = played.getCardsWithSuit(leadCard);
            ArrayList<Card> handLeads = new ArrayList<>();
            ArrayList<Card> trumpCards = played.getCardsWithSuit(trump);
            ArrayList<Card> handTrumps = new ArrayList<>();
            for (Card c : hand){
                if (c.getSuit() == leadCard){
                    handLeads.add(c);
                } else if (c.getSuit() == trump){
                    handTrumps.add(c);
                }
            }


            // if the player has card of lead suit, return the highest ranked card of lead suit
            // if there is a card with higher rank, else return the lowest ranked card
            if (!handLeads.isEmpty()){
                if (leadCards.get(0).getRankId() > handLeads.get(0).getRankId()){
                    result = handLeads.get(0);
                }else{
                    result = handLeads.get(handLeads.size()-1);
                }
                return result;
            }else{
                // if the player has no card of lead suit, check for cards of trump suit
                // return the highest ranked card of trump suit if there is one with higher rank
                if (!handTrumps.isEmpty()){
                    if (trumpCards.get(0).getRankId() > handTrumps.get(0).getRankId() ){
                        result = handTrumps.get(0);
                        return result;
                    }
                }
            }
            // if none of the requirements is met, return the lowest ranked card
            return hand.get(hand.size()-1);
        }

    }
}
