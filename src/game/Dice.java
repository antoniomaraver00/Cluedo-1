package game;
import java.util.*;

public class Dice {
	public Dice() {
	}

	public int roll() {
		Random rand = new Random();
		int num = rand.nextInt(13);
		if (num==0) {return roll();}//quick fix to exclude 0
		return num;
		
	}
	

}