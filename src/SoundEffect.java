import java.io.*;

import javax.sound.sampled.*;

/*
 * Sounds for the game
 */
public enum SoundEffect {
	ALIEN("res" + File.separator + "music" + File.separator + "alien.wav"),		// alien dies
	ATTACK("res" + File.separator + "music" + File.separator + "ship.wav"),		// ship projectile
	EXPLODE("res" + File.separator + "music" + File.separator + "complete.wav"),	// level complete
	SHIPDIE("res" + File.separator + "music" + File.separator + "alien hit.wav"),
	BGM("res" + File.separator + "music" + File.separator + "bgm.au"),
	TITLEBGM("res" + File.separator + "music" + File.separator + "titlebgm.wav"),
	WIN("res" + File.separator + "music" + File.separator + "winnarisu2.wav");

	/*
	 * nested class for specifying volume
	 */
	public static enum Volume
	{
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.HIGH;
	private Clip clip;	// each sound effect has its own clip, loaded with its own sound file

	/*
	 * construct each element of the enum with its own sound file.
	 */
	SoundEffect(String soundFileName)
	{
		try
		{
			File soundFile = new File(soundFileName);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		}
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Play or Re-play the sound effect from the beginning, by rewinding.
	 */
	public void play()
	{
		if (volume != Volume.MUTE)
		{
			if (clip.isRunning())
				clip.stop();		// stop the player if it is still running
			clip.setFramePosition(0);	// rewind to the beginning
			clip.start();			// Start playing
		}
	}

	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void endLoop()
	{
		if (clip.isRunning())
			clip.stop();
	}

	/*
	 * Optional static method to pre-load all the sound files.
	 */
	public static void init()
	{
		values();	// calls the constructor for all the elements
	}
}
