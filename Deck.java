import java.util.ArrayList;

public class Deck {

	private Card[] pack;
	private int usedCards;

	public Deck() {
		this(false);
	}

	public Deck(boolean hasJoker) {
		if (hasJoker)
			pack = new Card[54];
		else
			pack = new Card[52];
		int created = 0; // sa karta kemi krijuar deri me tani
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 1; value <= 13; value++) {
				pack[created] = new Card(value, suit);
				created++;
			}
		}
		if (hasJoker) {
			pack[created++] = new Card(1, Card.JOKER);
			pack[created++] = new Card(2, Card.JOKER);
		}
	}

	public void shuffle() {
		for (int lastIndex = pack.length - 1; lastIndex > 0; lastIndex--) {
			int rand = (int) (Math.random() * (lastIndex + 1));

			Card temp = pack[lastIndex];// letra e i-te
			pack[lastIndex] = pack[rand];// letra e i-te eshte letra rastes.
			pack[rand] = temp;// letra e rast. eshte e i-ta
		}
		usedCards = 0;
	}

	public int remainingCards() {
		return pack.length - usedCards;
	}

	public Card nextCard() {
		if (hasMoreCards()) {
			usedCards++;
			return pack[usedCards - 1];
		} else {
			System.out.println("Ska me letra.");
			return null;
		}
	}

	public boolean containsJokers() {
		return pack.length == 54;
	}

	public boolean hasMoreCards() {
		return usedCards != pack.length;
	}
	
	public void addCards(Card c, int i) {
		pack[pack.length - i] = c;
	}

}
