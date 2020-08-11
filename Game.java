import java.util.ArrayList;

public class Game {

	Pile table;
	Deck deck;
	Dealer dealer;
	Player[] players;
	private int turn;

	public Game(Player[] player) {
		players = player;
		deck = new Deck();
		dealer = new Dealer(deck);
		turn = 0;
		table = new Pile();
	}

	public void play() {

		deck.shuffle();
		dealer.first4CardsInTable(table);

		Player lastPlayerGetCardsFromTable = null;

		while (gameNotOver()) {

			roundDeal();

			while (roundNotEnd()) {

				Card c = currentPlayer().play();
				int lastTableCard;
				
				if (table.ktheLetrenEFundit() != null) {
					lastTableCard = table.ktheLetrenEFundit().getValue();
				}else {
					lastTableCard = -1;
				}

				if (c.getValue() == 11) {

					for (int i = 0; i < table.saLetraJan(); i++) {
						currentPlayer().wonCards.addCard(new Card(1, 1));
					}

					table.pastroLetrat();

					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

					lastPlayerGetCardsFromTable = currentPlayer();

					switchTurn();

					continue;
				}

				if (c.getValue() == lastTableCard) {

					for (int i = 0; i < table.saLetraJan(); i++) {
						currentPlayer().wonCards.addCard(table.ktheLeter(i));
					}

					System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());
					lastPlayerGetCardsFromTable = currentPlayer();
					table.pastroLetrat();;
				} else {
					table.shtoLeter(c);
				}

				switchTurn();

			}
		}

		for (int i = 0; i < table.saLetraJan(); i++) {
			countedCards(lastPlayerGetCardsFromTable, table.ktheLeter(i));
		}

		Player winner = checkWinner();
		System.out.println("Fituesi eshte " + winner.toString());
		System.out.println("Me rezultat " + winner.getScore());

	}

	private boolean roundNotEnd() {

		return players[players.length - 1].cardsInHand() != 0;
	}

	private Player checkWinner() {
		
		int maxScore = 0;
		Player winner = null;
		for (int i = 0; i < players.length; i++) {
			
			if (players[i].getScore() > maxScore) {
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
