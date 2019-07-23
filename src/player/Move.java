package player;

public class Move {
	private Dice dice;

	public Move() {
		dice = new Dice();
	}

	public Dice getDice() {
		return dice;
	}

	public void apply() {

	}

	public void rollDice() {
		dice.roll();
	}

	public String toString() {
		return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") + "  " + "dice" + "="
				+ (getDice() != null ? !getDice().equals(this) ? getDice().toString().replaceAll("  ", "    ") : "this"
						: "null");
	}
}