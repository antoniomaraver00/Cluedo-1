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
	public String getRoom(Player p) {
		
		//players x,y boundaries determines room player is in, if player is in a room at all
		if ((p.getPositon().getX()>=3 && p.getPositon().getX()<=9)&&(p.getPositon().getY()>=1 && p.getPositon().getY()<=4)) {return"Study";}
		if ((p.getPositon().getX()>=21 && p.getPositon().getX()<=27)&&(p.getPositon().getY()>=1 && p.getPositon().getY()<=4)) {return"Hall";}
		if ((p.getPositon().getX()>=39 && p.getPositon().getX()<=45)&&(p.getPositon().getY()>=1 && p.getPositon().getY()<=4)) {return"Lounge";}
		if ((p.getPositon().getX()>=39 && p.getPositon().getX()<=45)&&(p.getPositon().getY()>=8 && p.getPositon().getY()<=10)) {return"Dining Room";}
		if ((p.getPositon().getX()>=39 && p.getPositon().getX()<=45)&&(p.getPositon().getY()>=14 && p.getPositon().getY()<=16)) {return"Kitchen";}
		if ((p.getPositon().getX()>=39 && p.getPositon().getX()<=45)&&(p.getPositon().getY()>=20 && p.getPositon().getY()<=23)) {return"Ballroom";}
		if ((p.getPositon().getX()>=21 && p.getPositon().getX()<=27)&&(p.getPositon().getY()>=20 && p.getPositon().getY()<=23)) {return"Conservatory";}
		if ((p.getPositon().getX()>=3 && p.getPositon().getX()<=9)&&(p.getPositon().getY()>=20 && p.getPositon().getY()<=23)) {return"Billiard Room";}
		if ((p.getPositon().getX()>=3 && p.getPositon().getX()<=9)&&(p.getPositon().getY()>=11 && p.getPositon().getY()<=14)) {return"Library";}
		return"NOT IN ROOM";
		
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