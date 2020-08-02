import java.util.Random;

public class DummyPlayer extends Player {

	Hand hand;
	private int score;
	Random rnd;

	public DummyPlayer(String name) {
		super(name);
		hand = new Hand();
		score = 0;
		rnd = new Random();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int cardsInHand() {
		return hand.count();
	}
	
	public void accept(Card c) {

		hand.addCard(c);

	}

	@Override
	public Card play() {

		int numberOfCardToThrow = rnd.nextInt(cardsInHand());
		
		Card cardPlayed = throwCard(numberOfCardToThrow);

		System.out.println(name + " luajti " + cardPlayed.toString());
		
		try {
			System.out.println("duke analizuar... ");
			Thread.sleep(100);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		
		return cardPlayed;
	}

	@Override
	public void cardsToString() {
		System.out.println("Letrat e tua jan:");
		for (int i = 0; i < hand.count(); i++) {
			System.out.println((i + 1) + ".  " + hand.getCard(i).toString());
		}

	}

	@Override
	public Card throwCard(int cardNumber) {
		Card c = hand.getCard(cardNumber);
		hand.removeCard(c);

		return c;
	}
	
	public String toString() {
		return name;
	}

}
