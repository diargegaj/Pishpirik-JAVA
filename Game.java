
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
				} else {
					lastTableCard = -1;
				}

				if (table.saLetraJan() == 1) {

					if (lastTableCard == 11 && c.getValue() == 11) {

						boolean checkIfSomeoneHasPishpirik = false;

						for (int i = 0; i < players.length; i++) {

							if (currentPlayer().equals(players[i])) {
								continue;
							}

							if (players[i].getJanarPishpiriks() > 0) {
								int prevJanarPishpiriks = players[i].getJanarPishpiriks();
								players[i].setPishpiriks(prevJanarPishpiriks - 1);
								checkIfSomeoneHasPishpirik = true;
								table.pastroLetrat();
							}
						}

						if (!checkIfSomeoneHasPishpirik) {

							int prevJanarPishpiriks = currentPlayer().getJanarPishpiriks();
							currentPlayer().setJanarPishpiriks(prevJanarPishpiriks + 1);
							table.pastroLetrat();
							
							System.out.println(currentPlayer() + " beri pishpirik me " + c.toString());
						}
						
						lastPlayerGetCardsFromTable = currentPlayer();
						
						switchTurn();

						continue;

					}

					if (lastTableCard == c.getValue()) {

						boolean checkIfSomeoneHasPishpirik = false;

						for (int i = 0; i < players.length; i++) {

							if (currentPlayer().equals(players[i])) {
								continue;
							}

							if (players[i].getPishpiriks() > 0) {
								int prevPishpiriks = players[i].getPishpiriks();
								players[i].setPishpiriks(prevPishpiriks - 1);
								checkIfSomeoneHasPishpirik = true;
								table.pastroLetrat();
								
								System.out.println(currentPlayer() + " ja prishi pishpirikin " + players[i]);
							}
						}

						if (!checkIfSomeoneHasPishpirik) {
							int prevPishpiriks = currentPlayer().getPishpiriks();
							currentPlayer().setPishpiriks(prevPishpiriks + 1);
							table.pastroLetrat();
							
							System.out.println(currentPlayer() + " beri pishpirik me " + c.toString());
						}

						lastPlayerGetCardsFromTable = currentPlayer();
						switchTurn();
						
						continue;

					}
				} else {

					if (c.getValue() == 11) {

						table.shtoLeter(c);

						for (int i = 0; i < table.saLetraJan(); i++) {
							currentPlayer().wonCards.addCard(table.ktheLeter(i));
						}

						table.pastroLetrat();

						System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

						lastPlayerGetCardsFromTable = currentPlayer();

						switchTurn();

						continue;
					}

					if (c.getValue() == lastTableCard) {

						table.shtoLeter(c);

						for (int i = 0; i < table.saLetraJan(); i++) {
							currentPlayer().wonCards.addCard(table.ktheLeter(i));
						}

						System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());
						lastPlayerGetCardsFromTable = currentPlayer();
						table.pastroLetrat();
						
					} else {
						table.shtoLeter(c);
					}
				}

				switchTurn();

			}
		}
		
		System.out.println("Letrat ne pile " + table.saLetraJan());
		for (int i = 0; i < table.saLetraJan(); i++) {
			lastPlayerGetCardsFromTable.wonCards.addCard(table.ktheLeter(i));
		}

		Player winner = checkWinner();
		System.out.println("Fituesi eshte " + winner.toString());
		System.out.println("Me rezultat " + winner.getScore());

		System.out.println("=================================================================");
		
		
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i] + " score=>" + players[i].getScore());
		}
	}

	private boolean roundNotEnd() {

		return players[players.length - 1].cardsInHand() != 0;
	}

	private Player checkWinner() {

		Player winner = players[0];

		for (int i = 0; i < players.length; i++) {

			for (int j = 0; j < players[i].wonCards.count(); j++) {
				Card card = players[i].wonCards.getCard(j);
				countedCards(players[i], card);
			}

			if (players[i].getPishpiriks() > 0) {
				int prevScore = players[i].getScore();
				int pishpiriks = players[i].getPishpiriks();
				players[i].setScore(prevScore + (10 * pishpiriks));
			}

			if (players[i].getJanarPishpiriks() > 0) {
				int prevScore = players[i].getScore();
				int janarPishpiriks = players[i].getJanarPishpiriks();
				players[i].setScore(prevScore + (20 * janarPishpiriks));
			}

			if (winner.getScore() < players[i].getScore()) {
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

		Player[] pl = { new DummyPlayer("pl 1"), new DummyPlayer("pl 2") };

		Game g = new Game(pl);

		g.play();

	}

}
