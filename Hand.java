import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;

	public Hand() {
		cards = new ArrayList<>(4);
	}

	public void clearHand() {
		cards.clear();
	}

	public void addCard(Card l) {
		cards.add(l);
	}

	public void removeCard(Card card) {
		cards.remove(card);
	}

	public Card getCard(int pozita) {
		return cards.get(pozita);// metoda get(pozita) kthen Object
	}

	public int count() {
		return cards.size();
	}
}