package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class HighRankSelectStrategy implements SelectStrategy{
    public HighRankSelectStrategy(){
        
    }

    public Card select(Hand hand, Suit trumps, Card leadCard){
        if (hand.isEmpty())
            return null;

        //sort cards in hand
        Card highestCard = hand.reverseSort(Hand.SortType.RANKPRIORITY, false);

        //get List of highest rank cards from hand
        Hand highestHand = hand.extractCardsWithRank(highestCard.getRank());

        //if there is only one highest rank card, then return this card
        if(highestHand.getCardList().size() == 1)
            return hand.getFirst();

        Hand trumpsHand = highestHand.extractCardsWithSuit(trumps);

        //if there is more than one card has trump suit with same rank, then select the first card
        if(!trumpsHand.isEmpty())
            return trumpsHand.getFirst();

        //if there is no card suit same with trump suit, seek the card same with lead suit
        Hand leadHand = trumpsHand.extractCardsWithSuit(leadCard.getSuit());

        //if there are some cards same with lead suit，then return the first card
        if(!leadHand.isEmpty())
            return leadHand.getFirst();

        //if there is no card suit same with lead suit, then random choose a card
        int MaxRandom = trumpsHand.getCardList().size() - 1;
        int randomCardID = (int) Math.random() * (MaxRandom + 1);
        return trumpsHand.getCardList().get(randomCardID);
    }
}