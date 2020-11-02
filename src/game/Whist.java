package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
public class Whist extends CardGame {
	private final String version = "1.0";

	// read property file
	private PropertyReader reader;


	// ====================================================
	// ====================================================
	// properties
	private final int nbPlayers;
	private final int nbNPC;
	private final int nbStartCards;
	private final int winningScore;
	private final int[] players;
	private final boolean enforceRules;
	private final Random random;
	private NPC[] npcs;
	// end of properties
	// ====================================================
	// ====================================================



	// ====================================================
	// ====================================================
	// UI elements
	final String[] trumpImage = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
	private final int handWidth = 400;
	private final int trickWidth = 40;
  	private final Location[] handLocations = {
		  new Location(350, 625),
		  new Location(75, 350),
		  new Location(350, 75),
		  new Location(625, 350)
  	};
  	private final Location[] scoreLocations = {
		  new Location(575, 675),
		  new Location(25, 575),
		  new Location(575, 25),
		  new Location(650, 575)
  	};
  	private Actor[] scoreActors = {null, null, null, null };
  	private final Location trickLocation = new Location(350, 350);
  	private final Location textLocation = new Location(350, 450);
  	private Location hideLocation = new Location(-500, - 500);
  	private Location trumpsActorLocation = new Location(50, 50);
  	Font bigFont = new Font("Serif", Font.BOLD, 36);
  	// end of UI elements
	// ====================================================
	// ====================================================

	// ====================================================
	// ====================================================
	// gameplay elements
	private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
	private final int thinkingTime = 10;
	private Hand[] hands;
  	private int[] scores;
  	private Card selected;
  	// end of gameplay elements
	// ====================================================
	// ====================================================


	// return random Enum value
	private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}

	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

	private void setStatus(String string) { setStatusText(string); }

	private void initScore() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] = 0;
			scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}

 	private void updateScore(int player) {
		removeActor(scoreActors[player]);
		scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
		addActor(scoreActors[player], scoreLocations[player]);
	}


	private void initRound() throws IOException {
		hands = dealOut(deck,nbPlayers, nbStartCards); // Last element of hands is leftover cards; these are ignored
		for (int i = 0; i < nbPlayers; i++) {
			   hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		}
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card
			    {
			      public void leftDoubleClicked(Card card) {
			      	selected = card; hands[0].setTouchEnabled(false);
			      }
			    };
		hands[0].addCardListener(cardListener);
		 // graphics
	    RowLayout[] layouts = new RowLayout[nbPlayers];
	    for (int i = 0; i < nbPlayers; i++) {
	      layouts[i] = new RowLayout(handLocations[i], handWidth);
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);
	      hands[i].setView(this, layouts[i]);
	      hands[i].setTargetArea(new TargetArea(trickLocation));
	      hands[i].draw();
	    }
	    
	//	    for (int i = 1; i < nbPlayers; i++)  // This code can be used to visually hide the cards in a hand (make them face down)
	//	      hands[i].setVerso(true);
		    // End graphics
	}

	// custom dealing card method to implement seed randomness
	private Hand[] dealOut(Deck deck, int numPlayer, int numCard){
		Hand[] hands = new Hand[numPlayer];
		for (int i = 0; i < numPlayer; i++){
			hands[i] = new Hand(deck);
		}
		Hand pack = deck.toHand(false);
		for (int i=0; i < numPlayer;i++) {
			for (int j = 0; j < numCard; j++) {
				int x = random.nextInt(pack.getNumberOfCards());
				Card dealt = pack.get(x);
				dealt.removeFromHand(false);
				hands[i].insert(dealt, false);
			}
		}
		return hands;
	}


	private String printHand(ArrayList<Card> cards) {
		String out = "";
		for (int i = 0; i < cards.size(); i++) {
			out += cards.get(i).toString();
			if (i < cards.size() - 1) out += ",";
		}
		return (out);
	}

	private Optional<Integer> playRound() throws IOException {  // Returns winner, if any
		// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/" + trumpImage[trumps.ordinal()]);
		addActor(trumpsActor, trumpsActorLocation);
		// End trump suit

		Hand trick;
		int winner;
		Card winningCard;
		Suit lead;
		int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
		for (int i = 0; i < nbStartCards; i++) {
			trick = new Hand(deck);
			selected = null;
			lead = null;

			if (1 == players[nextPlayer]) {  // Select lead depending on player type
				hands[nextPlayer].setTouchEnabled(true);
				setStatus("Player "+ nextPlayer +" double-click on card to lead.");
				while (null == selected) delay(100);
			} else {
				selected = npcSelect(nextPlayer, trick, trumps, lead);
			}

			// Lead with selected card
			trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
			System.out.println("New trick: Lead Player = " + nextPlayer + ", Lead suit = " + selected.getSuit() + ", Trump suit = " + trumps);
			System.out.println("Player " + nextPlayer + " play: " + selected.toString() + " from [" + printHand(hands[nextPlayer].getCardList()) + "]");
			// End Lead


			for (int j = 1; j < nbPlayers; j++) {
				if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
				selected = null;
				if (1 == players[nextPlayer]) {
					hands[nextPlayer].setTouchEnabled(true);
					setStatus("Player "+nextPlayer+" double-click on card to follow.");
					while (null == selected) delay(100);
				} else {
					selected = npcSelect(nextPlayer, trick, trumps, lead);
				}

				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
				if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
					// Rule violation
					String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
					//System.out.println(violation);
					if (enforceRules)
						try {
							throw (new BrokeRuleException(violation));
						} catch (BrokeRuleException e) {
							e.printStackTrace();
							System.out.println("A cheating player spoiled the game!");
							System.exit(0);
						}
				}
				// End Check
				selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				System.out.println("Winning card: " + winningCard.toString());
				System.out.println("Player " + nextPlayer + " play: " + selected.toString() + " from [" + printHand(hands[nextPlayer].getCardList()) + "]");
				if ( // beat current winner with higher card
						(selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
								// trumped when non-trump was winning
								(selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					winner = nextPlayer;
					winningCard = selected;
				}
				// End Follow
			}
			delay(600);
			trick.setView(this, new RowLayout(hideLocation, 0));
			trick.draw();
			nextPlayer = winner;
			System.out.println("Winner: " + winner);
			setStatusText("Player " + nextPlayer + " wins trick.");
			scores[nextPlayer]++;
			updateScore(nextPlayer);
			if (winningScore == scores[nextPlayer]) return Optional.of(nextPlayer);
		}
		removeActor(trumpsActor);
		return Optional.empty();
	}

	// npcs play the game
	private Card npcSelect(int nextPlayer, Hand trick, Suit trumps, Suit lead) throws IOException {
		setStatusText("Player " + nextPlayer + " thinking...");
		delay(thinkingTime);
		return npcs[nextPlayer-(nbPlayers-nbNPC)].play(hands[nextPlayer], trick, trumps, lead);
	}


	public Whist() throws IOException {

		super(700, 700, 30);
		setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");
		reader = PropertyReader.getInstance("smart.properties");
		nbPlayers = reader.getNbPlayers();
		nbNPC = reader.getNbNPC();
		nbStartCards = reader.getNbStartCards();
		winningScore = reader.getWinningScore();
		players = reader.getPlayers();
		enforceRules= reader.getRule();
		random = new Random(reader.getRandomState());
		npcs = reader.getNPCs();
		scores = new int[nbPlayers];
		initScore();
		Optional<Integer> winner;

		do {
			initRound();
			winner = playRound();
		} while (!winner.isPresent());
		addActor(new Actor("sprites/gameover.gif"), textLocation);
		setStatusText("Game over. Winner is player: " + winner.get());
		refresh();
	}

  	public static void main(String[] args) throws IOException {
		new Whist();
  	}

}
