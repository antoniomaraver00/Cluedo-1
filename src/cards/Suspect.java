package cards;

public class Suspect implements Card {
	private String name;
	
	public Suspect(String name) {
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
		if (this.toString().equals(c.toString())) {return true;}
		return false;
	}
}