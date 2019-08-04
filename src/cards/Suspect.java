package cards;

public class Suspect implements Card {
	private String name;
	
	public Suspect(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}