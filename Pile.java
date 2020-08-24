import java.util.ArrayList;

public class Pile {

	private ArrayList<Card> cards = new ArrayList<>();
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	public Card getCard(int pozita) {
	
		if (pozita < 0 || pozita > cards.size())
			throw new IllegalArgumentException("dergo poziten korrekte");
		else if(cards.get(pozita) != null)
			return cards.get(pozita);
		return new Card(4, 1);
	}
	
	public Card getLastCard() {
		
		if (cards.size() > 0) {
			return cards.get(cards.size() - 1);
		}
		
		return null;
	}
	
	public int pileSize() {
		return cards.size();
	}

	public void clearCards() {
		cards.clear();
	}
	
}
