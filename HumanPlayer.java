
public class HumanPlayer extends Player {

	Hand hand;
	private int score;

	public HumanPlayer(String name) {
		super(name);
		hand = new Hand();
		score = 0;

	}

	public void accept(Card c) {

		hand.addCard(c);

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

	public Card play() {

		cardsToString();

		System.out.println("Cilen leter deshiron me hedh: ");
		
		int cardToThrow = sc.nextInt();
		
		return throwCard(cardToThrow);
		

	}
	
	public Card throwCard(int cardNumber) {
		
		Card c = hand.getCard(cardNumber - 1);
		hand.removeCard(c);
		
		return c;
		
	}

	public void cardsToString() {
		System.out.println("Letrat e tua jan:");
		for (int i = 0; i < hand.count(); i++) {
			System.out.println((i + 1)+ ".  " + hand.getCard(i).toString());
		}
	}

	public String toString() {
		return name;
	}

}
