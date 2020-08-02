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

		while (gameOver(deck)) {

			roundDeal();

			while (roundEnd()) {

				Card c = currentPlayer().play();
				
				int lastTableCard;
				
				if (table.size() > 0) {

					lastTableCard = table.get(table.size() - 1).getValue();
				} else {
					lastTableCard = -1;
				}

				if (c.getValue() == 11) {

					for (int i = 0; i < table.size(); i++) {
						countedCards(currentPlayer(), table.get(i));
					}

					table.removeAll(table);
					
					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

					switchTurn();

					continue;
				}

				if (c.getValue() == lastTableCard) {


					for (int i = 0; i < table.size(); i++) {
						countedCards(currentPlayer(), table.get(i));
					}
					
					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

					table.removeAll(table);
				} else {
					table.add(c);
				}

				switchTurn();

			}
		}
		Player winner = checkWinner();
		System.out.println("Fituesi eshte " + winner.toString());
		System.out.println("Me rezultat " + winner.getScore());

	}

	private boolean roundEnd() {

		return players[1].cardsInHand() != 0;
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

	public boolean gameOver(Deck deck) {
		if (deck.hasMoreCards()) {
			return true;
		} else {
			return false;
		}
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

		Player[] pl = { new DummyPlayer("Human Player"), new DummyPlayer("DummyPlayer") };

		Game g = new Game(pl);

		g.play();
	}

}
