import java.util.Deque;
import java.util.LinkedList;

public class Game {

	Pile table;
	Deck deck;
	Dealer dealer;
	Deque<Player> players;

	public Game(Deque<Player> player) {
		players = player;
		deck = new Deck();
		dealer = new Dealer(deck);
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

				int lastTableCard = (table.getLastCard() != null) ? table.getLastCard().getValue() : -1;

				if (table.pileSize() == 1) {

					if (lastTableCard == Card.JACK && c.getValue() == Card.JACK) {

						boolean checkIfSomeoneHasPishpirik = false;

						table.addCard(c);
						for (Player player : players) {

							if (currentPlayer().equals(player)) {
								continue;
							}

							if (player.getJanarPishpiriks() > 0) {
								int prevJanarPishpiriks = player.getJanarPishpiriks();
								player.setPishpiriks(prevJanarPishpiriks - 1);
								checkIfSomeoneHasPishpirik = true;
								System.out.println(currentPlayer() + " ja prishi Janar Pishpirikun " + player);

							}
						}

						if (!checkIfSomeoneHasPishpirik) {

							int prevJanarPishpiriks = currentPlayer().getJanarPishpiriks();
							currentPlayer().setJanarPishpiriks(prevJanarPishpiriks + 1);

							System.out.println(currentPlayer() + " beri pishpirik me " + c.toString());
						}

						for (int i = 0; i < table.pileSize(); i++) {
							currentPlayer().wonCards.addCard(table.getCard(i));
						}

						table.clearCards();
						lastPlayerGetCardsFromTable = currentPlayer();

						switchTurn();

						continue;

					}

					if (lastTableCard == c.getValue()) {

						boolean checkIfSomeoneHasPishpirik = false;

						table.addCard(c);

						for (Player player : players) {

							if (currentPlayer().equals(player)) {
								continue;
							}

							if (player.getPishpiriks() > 0) {
								int prevPishpiriks = player.getPishpiriks();
								player.setPishpiriks(prevPishpiriks - 1);
								checkIfSomeoneHasPishpirik = true;

								System.out.println(currentPlayer() + " ja prishi pishpirikin " + player);
							}
						}

						if (!checkIfSomeoneHasPishpirik) {
							int prevPishpiriks = currentPlayer().getPishpiriks();
							currentPlayer().setPishpiriks(prevPishpiriks + 1);
							table.clearCards();

							System.out.println(currentPlayer() + " beri pishpirik me " + c.toString());
						}

						for (int i = 0; i < table.pileSize(); i++) {
							currentPlayer().wonCards.addCard(table.getCard(i));
						}

						table.clearCards();
						lastPlayerGetCardsFromTable = currentPlayer();

						switchTurn();

						continue;

					}

					table.addCard(c);
				} else if (table.pileSize() == 0) {

					table.addCard(c);

					if (c.getValue() == 11) {
						System.out.println(currentPlayer() + " ju ka djeg lertra " + c);
					}

					switchTurn();

					continue;

				} else {

					if (c.getValue() == Card.JACK) {

						table.addCard(c);

						for (int i = 0; i < table.pileSize(); i++) {
							currentPlayer().wonCards.addCard(table.getCard(i));
						}

						table.clearCards();

						System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());

						lastPlayerGetCardsFromTable = currentPlayer();

						switchTurn();

						continue;
					}

					if (c.getValue() == lastTableCard) {

						table.addCard(c);

						for (int i = 0; i < table.pileSize(); i++) {
							currentPlayer().wonCards.addCard(table.getCard(i));
						}

						System.out.println(currentPlayer().toString() + " i mori letrat me " + c.toString());
						lastPlayerGetCardsFromTable = currentPlayer();
						table.clearCards();

					} else {
						table.addCard(c);
					}
				}

				switchTurn();

			}
		}

		if (lastPlayerGetCardsFromTable != null) {

			for (int i = 0; i < table.pileSize(); i++) {
				lastPlayerGetCardsFromTable.wonCards.addCard(table.getCard(i));
			}

			Player winner = checkWinner();

			if (winner != null) {
				System.out.println("Fituesi eshte " + winner.toString());
				System.out.println("Me rezultat " + winner.getScore());
			} else {
				System.out.println("Loja barazim scoret e njejta");
			}

		} else {
			System.out.println("Nuk ka fitues");
		}

		System.out.println("=================================================================");

		for (Player player : players) {
			System.out.println(player + " score=> " + player.getScore());
		}
	}

	private boolean roundNotEnd() {

		return currentPlayer().cardsInHand() != 0;
	}

	private Player checkWinner() {

		Player winner = players.peek();

		for (Player player : players) {

			for (int j = 0; j < player.wonCards.count(); j++) {
				Card card = player.wonCards.getCard(j);
				countedCards(player, card);
			}

			if (player.getPishpiriks() > 0) {
				int prevScore = player.getScore();
				int pishpiriks = player.getPishpiriks();
				player.setScore(prevScore + (10 * pishpiriks));
			}

			if (player.getJanarPishpiriks() > 0) {
				int prevScore = player.getScore();
				int janarPishpiriks = player.getJanarPishpiriks();
				player.setScore(prevScore + (20 * janarPishpiriks));
			}

			if (winner.getScore() < player.getScore()) {
				winner = player;
			}
		}

		int maxScore = winner.getScore();

		for (Player player : players) {

			if (player.equals(winner))
				continue;

			int currentPlayerScore = player.getScore();

			if (maxScore == currentPlayerScore) {
				return null;
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
		return players.peek();
	}

	public void switchTurn() {
		Player p = players.remove();
		players.add(p);
	}

	public void roundDeal() {
		for (Player player : players) {

			dealer.deal(player, 4);

		}
	}

	public static void main(String[] args) {

		Deque<Player> pl = new LinkedList<>();

		pl.add(new DummyPlayer("pl 1"));
		pl.add(new DummyPlayer("pl 2"));

		Game g = new Game(pl);

		g.play();

	}

}
