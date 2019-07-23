package cards;

public class Room implements Card {
	public Room() {
	}

	@Override
	public Room reveal() {
		return this;
	}
}