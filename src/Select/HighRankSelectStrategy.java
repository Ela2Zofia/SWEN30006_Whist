package Select;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.Suit;

import java.util.ArrayList;

public class HighRankSelectStrategy implements SelectStrategy{
    public HighRankSelectStrategy(){ }

    @Override
    public Card select(ArrayList<Card> hand, Hand played, Suit trumps, Suit leadCard){
        Card result;
        if (hand.isEmpty())
            return null;
        // lead behaviour
        if (leadCard == null){
            result = hand.reverseSort(Hand.SortType.RANKPRIORITY, false);
            hand.sort(Hand.SortType.SUITPRIORITY, true);
            return result;
        }
        //sort cards in hand
        hand.sort(Hand.SortType.RANKPRIORITY, false);

        //get List of highest rank cards from hand
        Hand highestHand = hand.extractCardsWithRank(hand.getFirst().getRank());

        //if there is only one highest rank card, then return this card
        if(highestHand.getCardList().size() == 1){
            result = hand.getFirst();
            hand.sort(Hand.SortType.SUITPRIORITY, true);
            return result;
        }


        //find highest ranked card with lead suit
        Hand leadHand = highestHand.extractCardsWithSuit(leadCard);

        //if there are some cards with lead suit，then return the first card
        if(!leadHand.isEmpty()){
            result = leadHand.getFirst();
            hand.sort(Hand.SortType.SUITPRIORITY, true);
            return result;
        }

        // find highest ranked card with trump suit if no lead suit card is present
        Hand trumpsHand = highestHand.extractCardsWithSuit(trumps);

        //if there is more than one card has trump suit with same rank, then select the first card
        if(!trumpsHand.isEmpty()){
            result = trumpsHand.getFirst();
            hand.sort(Hand.SortType.SUITPRIORITY, true);
            return result;
        }


        //if there is no card suit same as trump suit neither, then choose the first highest ranked card
        result = highestHand.getFirst();
        hand.sort(Hand.SortType.SUITPRIORITY, true);
        return result;
    }
}