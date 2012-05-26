import java.awt.Image;

/*
 * Level holds all objects required for a game (ship, shotDelay, alienCluster, difficulty)
 */
public class Level
{
	private GameBoard game;
	private AlienCluster cluster;
	private Ship ship;
	private int levelNumber;
	private int shotDelay;
	private Image image;
	private double difficulty;

	public Level(GameBoard game, AlienCluster cluster, Ship ship, int shotDelay,
			int levelNumber, Image image, double difficulty)
	{
		this.game = game;
		this.cluster = cluster;
		this.ship = ship;
		this.levelNumber = levelNumber;
		this.shotDelay = shotDelay;
		this.image = image;
		this.difficulty = difficulty;
	}

	public double getDifficulty()
	{
		return difficulty;
	}

	public Image getImage()
	{
		return image;
	}

	public AlienCluster getAlienCluster()
	{
		return cluster;
	}

	public Ship getShip()
	{
		return ship;
	}

	public int getLevelNumber()
	{
		return levelNumber;
	}

	public int getShotDelay()
	{
		return shotDelay;
	}
}
