package cards;

public class Suspect implements Card {

	public Suspect() {
	}

	@Override
	public Card reveal() {
		return this;
	}

}