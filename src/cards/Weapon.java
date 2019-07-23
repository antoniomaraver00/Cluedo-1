package cards;

public class Weapon implements Card {
	public Weapon() {
	}

	@Override
	public Card reveal() {
		return this;
	}

}