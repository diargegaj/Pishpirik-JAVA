import java.util.Deque;
import java.util.LinkedList;

public class Game {

	Pile table;
	Deck deck;
	Dealer dealer;
	Deque<Player> players;
	private int turn;

	public Game(Deque<Player> player) {
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

						for (Player player : players) {

							if (currentPlayer().equals(player)) {
								continue;
							}

							if (player.getJanarPishpiriks() > 0) {
								int prevJanarPishpiriks = player.getJanarPishpiriks();
								player.setPishpiriks(prevJanarPishpiriks - 1);
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

						for (Player player : players) {

							if (currentPlayer().equals(player)) {
								continue;
							}

							if (player.getPishpiriks() > 0) {
								int prevPishpiriks = player.getPishpiriks();
								player.setPishpiriks(prevPishpiriks - 1);
								checkIfSomeoneHasPishpirik = true;
								table.pastroLetrat();

								System.out.println(currentPlayer() + " ja prishi pishpirikin " + player);
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
			for (int i = 0; i < 4; i++) {
				player.accept(dealer.deal());
			}

		}
	}

	public static void main(String[] args) {

		Deque<Player> pl = new LinkedList<>();

		pl.add(new DummyPlayer("pl 1"));
		pl.add(new HumanPlayer("pl 2"));

		Game g = new Game(pl);

		g.play();

	}

}
