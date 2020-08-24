import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DummyPlayer extends Player {

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
		
//		try {
//			System.out.println("duke analizuar... ");
//			Thread.sleep(1000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		int numberOfCardToThrow =(cardsInHand() > 1) ? ThreadLocalRandom.current().nextInt(1, cardsInHand()) : 1;
		Card cardPlayed = throwCard(numberOfCardToThrow);

		System.out.println(name + " luajti " + cardPlayed.toString());
		
		
		
		return cardPlayed;
	}
	

	public String toString() {
		return name;
	}

}
