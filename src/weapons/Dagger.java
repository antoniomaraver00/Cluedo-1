package weapons;

import cards.Card;
import cards.Weapon;

public class Dagger implements Weapon {
	final String name = "Dagger";

	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Card c) {
		if (this.toString().equals(c.toString())) {
			return true;
		}
		return false;
	}
}
