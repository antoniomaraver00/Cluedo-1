package cards;

public class Weapon implements Card {
	String name;
	
	public Weapon(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}