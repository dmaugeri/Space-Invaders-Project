import java.io.*;

import java.awt.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * Game class is responsible for creating the characters, informed
 * when entities within the game are killed or when power up is added and
 * will take appproiate game actions, and to display High score.
 */
public class GameBoard extends Screen
{
	// Objects to be drawn on screen
	public Ship ship;
	public AlienCluster cluster;
	public Iterator<Level> levelItr;
	public boolean waitingForLevel;	// waiting to go to next level
	public boolean isGameOver;		// game is over (player dead or finished entire game)
	public boolean playerWon; 		// player finished entire game

	private ArrayList<ShipProjectile> shipProjectiles;
	private ArrayList<ShipProjectile> shipProjectilesQueue;
	private ArrayList<AlienProjectile> alienProjectiles;
	private int lastHighScore;

	private int highScore;

	private Level currentLevel;
	private Image lifeImage;

	private Input input;
	private Font dynamicFont;
	private int speedDifficulty;
	private String stringfps;
	private Graphics bufferGraphics;
	private Image imageOverlay, bufferImage, background;

	/*
	 * Construct a Game
	 */
	public GameBoard(int environmentWidth, int environmentHeight)
	{
		super(environmentWidth, environmentHeight);

		stringfps = null;
		speedDifficulty = 0;
		isGameOver = false;
		playerWon = false;
		shipProjectiles = new ArrayList<ShipProjectile>();
		shipProjectilesQueue = new ArrayList<ShipProjectile>();
		alienProjectiles = new ArrayList<AlienProjectile>();
		waitingForLevel = false;

		imageOverlay = Toolkit.getDefaultToolkit().getImage("res"+ File.separator + "sprites"+ File.separator + "bng.png");

		File f = new File ("res"+ File.separator + "fonts"+ File.separator + "ka1.ttf");
		FileInputStream in;
		try
		{
			in = new FileInputStream (f);
			dynamicFont = Font.createFont (Font.TRUETYPE_FONT, in).deriveFont(18f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			dynamicFont = new Font("serif",Font.PLAIN,10);
		}


		LevelGenerator lg = new LevelGenerator(this);
		// change level difficulty here
		//loadAllLevels(lg.GenerateEasyLevels());
		loadAllLevels(lg.GenerateLevelDesign1());

		loadLastHighScore();
	}

	/*
	 * The game loop this is the main loop that is running during game play.
	 * Will update the game and keep track of FPS until game ends
	 */
	public void gameLoop()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e1)
		{
		}

		Runtime rt = Runtime.getRuntime();
		long lastFpsTime = 0;
		int fps = 0;
		long lastLoopTime = System.nanoTime();
		final long OPTIMAL_TIME = 1000000000 / 60;

		while (!isGameOver)
		{
			SoundEffect.BGM.loop();

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);

			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1000000000)
			{
				stringfps ="FPS: " + Integer.toString(fps);
				Util.debugPrint("JVM memory usage: " + (rt.totalMemory() / (2 << 19)) + " MB\r");

				lastFpsTime = 0;
				fps = 0;
			}

			updateGame(delta);
			repaint();

			if (waitingForLevel)
				nextLevel();

			try
			{
				Thread.sleep((System.nanoTime()-lastLoopTime)/1000000 + 10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		this.setCursorVisible();
	}

	/*
	 * Update the game such as moving objects, detecting collision, setting score, and setting flag for game done
	 */
	private synchronized void updateGame(double delta)
	{
		if (waitingForLevel || isGameOver)
			return;

		cluster.autoMove(delta);
		// alien reached bottom?
		if (cluster.findBottomMostAlien().getBoundingBoxMaxY() >= ship.getYPos())
		{
			SoundEffect.BGM.endLoop();
			SoundEffect.EXPLODE.play();
			isGameOver = true;
		}
		alienProjectiles.addAll(cluster.shoot());

		if (!shipProjectilesQueue.isEmpty())
		{
			shipProjectiles.addAll(shipProjectilesQueue);
			shipProjectilesQueue.clear();
		}

		// loops for each projectile
		Iterator<ShipProjectile> projItr = shipProjectiles.iterator();
		while (projItr.hasNext())
		{
			ShipProjectile p = projItr.next();
			p.autoMove(delta);

			// loops for each alien
			ArrayList<Alien> aliens = cluster.getAliveAliens();
			Iterator<Alien> aItr = aliens.iterator();

			while (aItr.hasNext())
			{
				Alien a = aItr.next();
				// check collision between ship projectile and aliens

				if (p.collidesWith(a))
				{
					// remove P, 'kill' a, increase score etc.
					SoundEffect.ALIEN.play();
					a.setLife(false);
					cluster.decrementAlienCounter();
					projItr.remove();
					setHighScore(100);
					if (cluster.getAlienCounter() % speedDifficulty == 0)
						cluster.increaseSpeed();

					// no more aliens?
					if (cluster.getAlienCounter() == 0)
					{
						if (levelItr.hasNext())
							waitingForLevel = true;
						else
						{
							playerWon = true;
							isGameOver = true;
							SoundEffect.BGM.endLoop();
							SoundEffect.WIN.play();
						}
					}
					break;
				}
			}
			if (p.isOutsideBounds())
				projItr.remove();
		}

		// loops for each Alien Projectile
		Iterator<AlienProjectile> apItr = alienProjectiles.iterator();
		boolean shipHit = false;
		while (apItr.hasNext())
		{
			AlienProjectile a = apItr.next();
			a.autoMove(delta);
			if (a.collidesWith(ship))
			{
				// remove projectile, toggle ship hit, end game?
				// ship.setAlive(false);
				SoundEffect.SHIPDIE.play();
				ship.decrementLife();
				shipHit = true;

				if (ship.getLives() <= -1)
				{
					isGameOver = true;
					SoundEffect.BGM.endLoop();
					SoundEffect.EXPLODE.play();
				}

				apItr.remove();
				cluster.resetShotCounter();
				break;
			}
			else if (a.isOutsideBounds())
			{
				apItr.remove();
				cluster.decrementShotCounter();
			}
		}

		if (waitingForLevel || isGameOver || shipHit)
		{
			alienProjectiles.clear();
			shipProjectiles.clear();
			shipProjectilesQueue.clear();
		}

		setHighScore(currentLevel.getLevelNumber());

		if ((isGameOver || playerWon) && (highScore > lastHighScore))
			saveHighScore();
	}

	/*
	 * Draws ship, start, aliens, powerup, and also displays score through out game.
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;

		g2.setFont(dynamicFont);
		g2.setColor(Color.WHITE);
		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphics = bufferImage.getGraphics();
		bufferGraphics.drawImage(background, 0, 0, this);

		if (cluster != null)
			cluster.draw(bufferGraphics);

		if (ship != null)
		{
			ship.draw(bufferGraphics);
			for (int i = 0; i < ship.getLives(); ++i)
				bufferGraphics.drawImage(lifeImage, (int)(i * (ship.getBoundingBoxWidth() + 10) + 6),
						(int)(getEnvironmentHeight() - ship.getBoundingBoxHeight() - 25),
						(int)ship.getBoundingBoxWidth(), (int)ship.getBoundingBoxHeight(), this);
		}

		if (shipProjectiles != null)
			for (ShipProjectile p : shipProjectiles)
				p.draw(bufferGraphics);

		if (alienProjectiles != null)
			for (AlienProjectile p : alienProjectiles)
				p.draw(bufferGraphics);

		g.drawImage(bufferImage, 0, 0, this);
		g.drawImage(imageOverlay,1, 1, 550, 700, this);

		g.drawString(Integer.toString(highScore), (getEnvironmentWidth() / 2) - 15, getEnvironmentHeight() - 32);

		if (stringfps != null)
			g2.drawString(stringfps, 10, 20);
		if (currentLevel != null)
			g.drawString(Integer.toString(currentLevel.getLevelNumber()), getEnvironmentWidth() - 80,
					getEnvironmentHeight() - 32);

		// waiting for level or game is over
		if (waitingForLevel)
		{
			g2.setColor(Color.GREEN);
			g2.drawString("Press 'n' for next Level",
					(int)(getEnvironmentWidth() * 0.25),
					getEnvironmentHeight() / 2);
			g2.drawString("SCORE: " + getHighScore(),
					(int)(getEnvironmentWidth() * 0.35),
					30 + getEnvironmentHeight() / 2);
		}
		else if (isGameOver)
		{
			if (playerWon)
			{
				g2.setColor(Color.GREEN);
				g2.drawString("YOU'RE THE BEST! YOU WIN!",
						(int)(getEnvironmentWidth() * 0.25),
						getEnvironmentHeight() / 2);
				g2.drawString("SCORE: " + getHighScore(),
						(int)(getEnvironmentWidth() * 0.35),
						30 + getEnvironmentHeight() / 2);
			}
			else
			{
				g2.setColor(Color.RED);
				g2.drawString("GAME OVER",
						(int)(getEnvironmentWidth() * 0.25),
						getEnvironmentHeight() / 2);
				g2.drawString("SCORE: " + getHighScore(),
						(int)(getEnvironmentWidth() * 0.35),
						30 + getEnvironmentHeight() / 2);
			}

			g2.setColor(Color.GREEN);

			if (highScore > lastHighScore)
			{
				g2.drawString("Congratulations",
						(int)(getEnvironmentWidth() * 0.45), 60 + getEnvironmentHeight() / 2);
				g2.drawString(" you got the high score!!", (int)(getEnvironmentWidth() * 0.25),
						100 + getEnvironmentHeight()/2);
			}
		}
	}

	/*
	 * Load all the levels
	 */
	public void loadAllLevels(LinkedList<Level> levels)
	{
		//Get all the levels and load the first level
		levelItr = levels.iterator();
		if (levelItr.hasNext())
		{
			currentLevel = levelItr.next();
			input = new Input(this, currentLevel.getShip());
			loadLevel(currentLevel);
		}
	}

	/*
	 * Load the next level
	 */
	public void nextLevel() {
		if (levelItr.hasNext())
		{
			currentLevel = levelItr.next();
			loadLevel(currentLevel);
		}
		else
		{
			SoundEffect.BGM.endLoop();
			playerWon = true;
		}
	}

	/*
	 * Load a level
	 */
	private void loadLevel(Level level)
	{
		this.ship = level.getShip();
		this.cluster = level.getAlienCluster();
		this.background = level.getImage();
		this.lifeImage = this.ship.getImage();
		speedDifficulty = (int)(cluster.getTotalAliens() * level.getDifficulty());
		setShotDelay(level.getShotDelay());

		shipProjectiles.clear();
		alienProjectiles.clear();
		shipProjectilesQueue.clear();
		playerWon = false;
		waitingForLevel = false;
	}

	/*
	 * Load last high score
	 */
	private void loadLastHighScore() {
		File file = new File("highscore.txt");
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			lastHighScore = Integer.parseInt(reader.readLine());
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Save high score
	 */
	private void saveHighScore() {
		File file = new File("highscore.txt");
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(Integer.toString(highScore));
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Add to defender projectile queue
	 */
	public void addToShipProjectiles(ShipProjectile p)
	{
		shipProjectilesQueue.add(p);
		SoundEffect.ATTACK.play();	//sound effect for shooting
		//Util.debugPrintln("Added to bullets");
	}
	/*
	 * Method to set and display the highscore.
	 */
	public void setHighScore(int score)
	{
		highScore += score;
	}

	/*
	 * Get the Highscore
	 */
	public int getHighScore()
	{
		return highScore;
	}

	/*
	 * Get the ship object belonging to the player
	 */
	public int getSpeedDifficulty()
	{
		return speedDifficulty;
	}
	/*
	 * Set shot delay for ship
	 */
	private void setShotDelay(int delay)
	{
		input.setShotDelay(delay);
	}
}
