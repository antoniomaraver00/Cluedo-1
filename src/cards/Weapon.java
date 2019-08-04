package cards;

public class Weapon implements Card {
	String name;
	
	public Weapon(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.getName();
	}
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
}