package gui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GameSounds {
	private Clip clip;
	private AudioInputStream inputStream;

	public void backgroundMusic() {
		try {
			// get clip from AudioSystem
			clip = AudioSystem.getClip();
			// get the audio file
			inputStream = AudioSystem.getAudioInputStream(new File("src/resources/background.wav"));
			
			// play the audio file
			clip.open(inputStream);
			
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
				gainControl.setValue(-25.0f); // Reduce volume by 10 decibels.
			
			clip.start();
			
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void mouseClickSound() {
		try {
			// get clip from AudioSystem
			clip = AudioSystem.getClip();
			// get the audio file
			inputStream = AudioSystem.getAudioInputStream(new File("src/resources/cursor_click.wav"));
			// play the audio file
			clip.open(inputStream);
			
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
			
			clip.start();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
