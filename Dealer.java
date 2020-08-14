
public class Dealer {
	private Deck deck;

	public Dealer() {
		deck = new Deck();
	}

	public Dealer(Deck deck) {
		this.deck = deck;
	}

	public Card deal() {
		return deck.nextCard();
	}

	public void deal(HumanPlayer player, int noCards) {
		for (int i = 0; i < noCards; i++) {
			Card c = deal();
			player.accept(c);
		}
	}

	public boolean hasCards() {
		return deck.hasMoreCards();
	}
	
	public void first4CardsInTable(Pile table) {
		for (int i = 0; i < 4; i++) {
			Card card = deal();
			
			if (card.getValue() == Card.JACK) {
				
				deck.addCards(card);
				deck.shuffle();
				i-=1;
				continue;
			}
			
			table.shtoLeter(card);

			System.out.println("letrat ne mes jan: " + card.toString());
		}
	}
}
