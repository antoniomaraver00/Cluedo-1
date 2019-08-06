package cards;

public interface Weapon extends Card {

	public String toString();
	
	@Override
	public boolean equals(Card c);
}