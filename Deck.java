import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> pack;
	private int usedCards = 0;

	public Deck() {
		this(false);
	}

	public Deck(boolean hasJoker) {
		if (hasJoker)
			pack = new ArrayList<>(54);
		else
			pack = new ArrayList<>(52);
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 1; value <= 13; value++) {
				pack.add(new Card(value, suit));
			}
		}
		if (hasJoker) {
			pack.add(new Card(1, Card.JOKER));
			pack.add(new Card(2, Card.JOKER));
		}
	}

	public void shuffle() {
		Collections.shuffle(pack);
	}
	
	public void restartGame() {
		Collections.shuffle(pack);
		usedCards = 0;
	}

	public int remainingCards() {
		return pack.size() - usedCards;
	}

	public Card nextCard() {
		if (hasMoreCards()) {
			usedCards++;
			return pack.get(usedCards -1);
		} else {
			System.out.println("Ska me letra.");
			return null;
		}
	}

	public boolean containsJokers() {
		return pack.size() == 54;
	}

	public boolean hasMoreCards() {
		return usedCards != pack.size();
	}
	
	public void addCard(Card c) {
		pack.add(c);
	}
	
	public void addCard(int index, Card c) {
		pack.add(index, c);
	}
	
	public void removeCard(Card c) {
		pack.remove(c);
	}

	public int getUsedCards() {
		return usedCards;
	}

	public void setUsedCards(int usedCards) {
		this.usedCards = usedCards;
	}
	
	

}
