package game;
import java.util.*;

public class Dice {
	public Dice() {
	}

	public int roll() {
		Random rand = new Random();
		return rand.nextInt(6);//fix, think this can include 0...
		
	}

}