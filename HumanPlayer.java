
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
	
	

	public Card play() {

		cardsToString();

		System.out.println("Cilen leter deshiron me hedh: ");
		
		int cardToThrow = sc.nextInt();
		
		return throwCard(cardToThrow);
		

	}
	
	

	

	public String toString() {
		return name;
	}

}
