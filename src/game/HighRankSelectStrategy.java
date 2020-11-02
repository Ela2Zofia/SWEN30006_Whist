package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class HighRankSelectStrategy implements SelectStrategy{
    public HighRankSelectStrategy(){ }

    @Override
    public Card select(Hand hand, Hand played, Suit trumps, Suit leadCard){
        if (hand.isEmpty())
            return null;
        // lead behaviour
        if (leadCard == null){
            return hand.reverseSort(Hand.SortType.RANKPRIORITY, false);
        }
        //sort cards in hand
        hand.sort(Hand.SortType.RANKPRIORITY, false);

        //get List of highest rank cards from hand
        Hand highestHand = hand.extractCardsWithRank(hand.getFirst().getRank());

        //if there is only one highest rank card, then return this card
        if(highestHand.getCardList().size() == 1)
            return hand.getFirst();

        //find highest ranked card with lead suit
        Hand leadHand = highestHand.extractCardsWithSuit(leadCard);

        //if there are some cards with lead suit，then return the first card
        if(!leadHand.isEmpty())
            return leadHand.getFirst();

        // find highest ranked card with trump suit if no lead suit card is present
        Hand trumpsHand = highestHand.extractCardsWithSuit(trumps);

        //if there is more than one card has trump suit with same rank, then select the first card
        if(!trumpsHand.isEmpty())
            return trumpsHand.getFirst();

        //if there is no card suit same as trump suit neither, then choose the first highest ranked card
        return highestHand.getFirst();
    }
}