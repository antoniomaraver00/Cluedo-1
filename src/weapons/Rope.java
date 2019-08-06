package weapons;

import cards.Card;
import cards.Weapon;

public class Rope implements Weapon {
	final String name = "Rope";
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
}
