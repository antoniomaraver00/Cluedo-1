package weapons;

import cards.Card;
import cards.Weapon;

public class CandleStick implements Weapon {
	final String name = "CandleStick";
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
}
