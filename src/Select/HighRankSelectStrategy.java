package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class HighRankSelectStrategy implements SelectStrategy{
    public HighRankSelectStrategy(){ }

    @Override
    public Card select(ArrayList<Card> hand, Hand played, Suit trumps, Suit leadCard){
        Card result;
        hand.get(0).getHand().setSortType(Hand.SortType.RANKPRIORITY);
        hand.sort(Card::compareTo);
        if (hand.isEmpty()){
            return null;
        }
        // lead behaviour
        if (leadCard == null){
            return hand.get(0);
        }
        //get List of highest rank cards from hand
        ArrayList<Card> highestHand = new ArrayList<>();
        for (Card c : hand){
            if (c.getRankId() == hand.get(0).getRankId()){
                highestHand.add(c);
            }
        }

        //if there is only one highest rank card, then return this card
        if(highestHand.size() == 1){
            result = hand.get(0);
            return result;
        }


        //find highest ranked card with lead suit
        ArrayList<Card> leadHand = new ArrayList<>();
        for (Card c : highestHand){
            if (c.getSuit() == leadCard){
                leadHand.add(c);
            }
        }

        //if there are some cards with lead suit，then return the first card
        if(!leadHand.isEmpty()){
            result = leadHand.get(0);
            return result;
        }

        // find highest ranked card with trump suit if no lead suit card is present
        ArrayList<Card> trumpsHand = new ArrayList<>();
        for (Card c : highestHand){
            if (c.getSuit() == trumps){
                trumpsHand.add(c);
            }
        }

        //if there is more than one card has trump suit with same rank, then select the first card
        if(!trumpsHand.isEmpty()){
            result = trumpsHand.get(0);
            return result;
        }


        //if there is no card suit same as trump suit neither, then choose the first highest ranked card
        result = highestHand.get(0);
        return result;
    }
}