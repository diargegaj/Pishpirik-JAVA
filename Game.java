import java.util.ArrayList;

public class Game {

	static ArrayList<Card> table;
	Deck deck;
	Dealer dealer;
	Player[] players;
	private int turn;

	public Game(Player[] player) {
		players = player;
		deck = new Deck();
		dealer = new Dealer(deck);
		turn = 0;
		table = new ArrayList<>();
	}

	public void play() {

		deck.shuffle();
		dealer.first4CardsInTable();

		Player LastPlayerGetCardsFromTable = null;
		
		
		// pasi qe po i implementoj metodat qe kan qen abstrakte te klasa Player 
		//edhe po i heki prej DummyPlayer edhe HumanPlayer
		// te ky loopa while (gameNotOver()) nuk po hyn hiq dhe po shkaktohet Error : NullPointException

		while (gameNotOver()) {

			roundDeal();

			while (roundNotEnd()) {

				Card c = currentPlayer().play();

				int lastTableCard;

				if (table.size() > 0) {

					lastTableCard = table.get(table.size() - 1).getValue();
				} else {
					lastTableCard = -1;
				}

				if (c.getValue() == 11) {

					for (int i = 0; i < table.size(); i++) {
						currentPlayer().wonCards.addCard(table.get(i));
					}

					table.removeAll(table);

					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

					LastPlayerGetCardsFromTable = currentPlayer();

					switchTurn();

					continue;
				}

				if (c.getValue() == lastTableCard) {

					for (int i = 0; i < table.size(); i++) {
						currentPlayer().wonCards.addCard(table.get(i));
					}

					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());
					LastPlayerGetCardsFromTable = currentPlayer();
					table.removeAll(table);
				} else {
					table.add(c);
				}

				switchTurn();

			}
		}

		for (int i = 0; i < table.size(); i++) {
			countedCards(LastPlayerGetCardsFromTable, table.get(i));
		}

		Player winner = checkWinner();
		System.out.println("Fituesi eshte " + winner.toString());
		System.out.println("Me rezultat " + winner.getScore());

	}

	private boolean roundNotEnd() {

		return players[players.length - 1].cardsInHand() != 0;
	}

	private Player checkWinner() {
		int[] playersScore = new int[players.length];

		for (int i = 0; i < players.length; i++) {
			playersScore[i] = players[i].getScore();

		}

		int maxScore = 0;
		Player winner = null;
		for (int i = 0; i < playersScore.length; i++) {
			if (playersScore[i] > maxScore) {
				winner = players[i];
			}
		}

		return winner;
	}

	public void countedCards(Player player, Card c) {

		if (checkScore2(c)) {

			int score = player.getScore() + 2;
			player.setScore(score);

		} else if (checkScore1(c)) {
			int score = player.getScore() + 1;
			player.setScore(score);

		}

	}

	private boolean checkScore2(Card c) {
		return c.getValue() == 10 && c.getSuit() == 2;
	}

	private boolean checkScore1(Card c) {
		return c.getValue() == 1 || c.getValue() == 10 || c.getValue() == 11 || c.getValue() == 12 || c.getValue() == 13
				|| (c.getValue() == 2 && c.getSuit() == 0);
	}

	public boolean gameNotOver() {
		return deck.hasMoreCards();
	}

	public Player currentPlayer() {
		return players[turn];
	}

	public void switchTurn() {
		turn = 1 - turn;
	}

	public void roundDeal() {
		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < 4; j++) {
				players[i].accept(dealer.deal());
			}
		}
	}

	public static void main(String[] args) {

		Player[] pl = { new HumanPlayer("Human Player"), new DummyPlayer("DummyPlayer") };

		Game g = new Game(pl);

		g.play();
	}

}
