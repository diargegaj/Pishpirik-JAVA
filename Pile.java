import java.util.ArrayList;

public class Pile {

	private ArrayList<Card> cards = new ArrayList<>();
	
	public void shtoLeter(Card c) {
		cards.add(c);
	}
	
	public ArrayList<Card> ktheLetrat(){
		return cards;
	}
	
	public Card ktheLeter(int pozita) {
	
		if (pozita < 0 || pozita > cards.size())
			throw new IllegalArgumentException("dergo poziten korrekte");
		else if(cards.get(pozita) != null)
			return cards.get(pozita);
		return new Card(4, 1);
	}
	
	public Card ktheLetrenEFundit() {
		
		if (!(cards.size() < 1)) {
			return cards.get(cards.size() - 1);
		}
		
		return null;
	}
	
	public int saLetraJan() {
		return cards.size();
	}

	public void pastroLetrat() {
		cards.clear();
	}
	
}
