package cards;

import player.Player;

public class Room implements Card {
	String name;

	
	
	public Room(String name) {
		this.name = name;
	}

	@Override
	public Room reveal() {
		return this;
	}
	

	public String getName() {
		return name;
	}

	public String toString() {
		String s = "";

		for (int i = 0; i < 3; i++) {
			s += "/";
			if (i == 1) {
				s += name;
			} else {
				for (int j = 0; j <= name.length() + 1; j++) {
					s += "/";
				}
			}

			s += "/\n";
		}

		return s;
	}
}