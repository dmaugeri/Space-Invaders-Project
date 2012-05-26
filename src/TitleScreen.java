import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

/*
 * Title screen for main Game
 */
public class TitleScreen extends Screen
{
	private volatile boolean madeGame;

	/*
	 * Construct a TitleScreen
	 */
	public TitleScreen(int environmentWidth, int environmentHeight)
	{
		super(environmentWidth, environmentHeight);

		Input input = new Input(this);
		this.madeGame = false;
		SoundEffect.init();	//initailize all sound effects
		SoundEffect.TITLEBGM.loop();
	}

	/*
	 * Set flag for madeGame
	 */
	public void setMadeGame(boolean madeGame)
	{
		this.madeGame = madeGame;
	}

	/*
	 * Get flag for madeGame
	 */
	public boolean getMadeGame()
	{
		return madeGame;
	}

	/*
	 * Stop music
	 */
	public void stopMusic()
	{
		SoundEffect.TITLEBGM.endLoop();
	}

	/*
	 * Draw titleScreen
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		Image imageOverlay = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "title.png");
		g.drawImage(imageOverlay, 0, 0, SpaceInvaderViewer.WIDTH, SpaceInvaderViewer.HEIGHT, this);
	}
}
