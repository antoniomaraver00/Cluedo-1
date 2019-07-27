package cards;

public class Weapon implements Card {
	String name;
	
	public Weapon(String name) {
		this.name = name;
	}

	@Override
	public Card reveal() {
		return this;
	}

	public String getName() {
		return name;
	}
}