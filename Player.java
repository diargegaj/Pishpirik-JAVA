import java.util.Scanner;

abstract public class Player {

	protected String name;
	protected Hand hand;
	protected Hand wonCards;
	protected int score;
	protected Scanner sc;

	public Player(String name) {
		this.name = name;
		hand = new Hand();
		sc = new Scanner(System.in);
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

	public int cardsInHand() {

		return hand.count();

	}

	abstract public Card play();

	public void cardsToString() {
		System.out.println("Letrat e tua jan:");
		for (int i = 0; i < hand.count(); i++) {
			System.out.println((i + 1)+ ".  " + hand.getCard(i).toString());
		}
	}

public Card throwCard(int cardNumber) {
		
		Card c = hand.getCard(cardNumber - 1);
		hand.removeCard(c);
		
		return c;
		
	}

}
