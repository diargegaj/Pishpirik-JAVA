import java.util.concurrent.ThreadLocalRandom;

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

	public void deal(Player player, int noCards) {
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
				System.out.println("Janar ne mes" + card);
				deck.removeCard(card);
				int x = ThreadLocalRandom.current().nextInt(4, 51);
				deck.addCard(x, card);
				
				deck.setUsedCards(deck.getUsedCards() - 1);

				i -= 1;
				continue;
			}

			table.addCard(card);

			System.out.println("letrat ne mes jan: " + card.toString());
		}
	}
}
