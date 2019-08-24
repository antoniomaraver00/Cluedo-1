package weapons;

import cards.Card;
import cards.Weapon;

public class Spanner implements Weapon {
	final String name = "Spanner";
	
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
