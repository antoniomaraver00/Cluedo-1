package player;
import java.util.*;

public class Dice {
	public Dice() {
	}

	public int roll() {
		Random rand = new Random();
		return rand.nextInt(6);
		
	}

}