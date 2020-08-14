import java.util.Scanner;

abstract public class Player {

	protected String name;
	protected Hand hand;
	protected Hand wonCards;
	protected int score;
	protected Scanner sc;
	protected int pishpiriks = 0;
	protected int janarPishpiriks = 0;

	public Player(String name) {
		this.name = name;
		hand = new Hand();
		sc = new Scanner(System.in);
		wonCards = new Hand();
	}

	public void accept(Card card) {
		hand.addCard(card);
	}

	public void throwCard(Card c) {
		hand.removeCard(c);
	}

	public void displayCards() {
		for (int i = 0; i < hand.count(); i++) {
			Card c = hand.getCard(i);
			System.out.println(c.toString());
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPishpiriks() {
		return pishpiriks;
	}

	public void setPishpiriks(int pishpiriks) {
		this.pishpiriks = pishpiriks;
	}

	public int getJanarPishpiriks() {
		return janarPishpiriks;
	}

	public void setJanarPishpiriks(int janarPishpiriks) {
		this.janarPishpiriks = janarPishpiriks;
	}

	public int cardsInHand() {

		return hand.count();

	}

	abstract public Card play();

	public void cardsToString() {
		System.out.println("Letrat e tua jan:");
		for (int i = 0; i < hand.count(); i++) {
			System.out.println((i + 1) + ".  " + hand.getCard(i).toString());
		}
	}

	public Card throwCard(int cardNumber) {

		Card c = hand.getCard(cardNumber - 1);
		hand.removeCard(c);

		return c;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + janarPishpiriks;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pishpiriks;
		result = prime * result + ((sc == null) ? 0 : sc.hashCode());
		result = prime * result + score;
		result = prime * result + ((wonCards == null) ? 0 : wonCards.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return false;
	}
	
	

}
