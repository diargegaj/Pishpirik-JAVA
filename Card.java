
public class Card {

	public final static int SPADES = 0; // Kodet per 4 llojet, plus XHOLi.
	public final static int HEART = 1;
	public final static int DIAMOND = 2; //
	public final static int CLUBS = 3;// TETELINA
	public final static int JOKER = 4; // GJOKERI

	public final static int ACE = 1;// PIKA
	public final static int JACK = 11;
	public final static int QUEEN = 12;
	public final static int KING = 13;

	private int suit;
	private int value;

	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}

	public int getSuit() {

		return suit;

	}

	public int getValue() {

		return value;

	}

	public String getSuitToString() {

		switch (suit) {

		case SPADES:

			return "GJETH";

		case HEART:

			return "ZEMER";

		case DIAMOND:

			return "KOCK";

		case CLUBS:

			return "LULE";

		default:

			return "XHOL";
		}

	}

	public String getValueToString() {
		if (suit == JOKER) {
			return "" + value;
		} else {
			switch (value) {
			case ACE:
				return "Pik";
			case JACK:
				return "Gjanar";
			case QUEEN:
				return "Mbretreshe";
			case KING:
				return "Mbret";
			default:
				return value + "";
			}
		}
	}

	public String toString() {
		if (suit == JOKER) {
			return "XHOL #" + value;
		} else
			return getValueToString() + " " + getSuitToString();
	}
}
