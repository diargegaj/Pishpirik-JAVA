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

	
	
	public void accept(Card c) {

		hand.addCard(c);

	}

	@Override
	public Card play() {
		
		try {
			System.out.println("duke analizuar... ");
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int numberOfCardToThrow = rnd.nextInt(cardsInHand());
		
		Card cardPlayed = throwCard(numberOfCardToThrow);

		System.out.println(name + " luajti " + cardPlayed.toString());
		
		
		
		return cardPlayed;
	}
	
	public String toString() {
		return name;
	}

}
