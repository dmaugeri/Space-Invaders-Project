import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.util.LinkedList;

/*
 * Level Generator Class for generating levels
 */
public class LevelGenerator
{
	private Image alienImage;
	private Image shipImage;
	private Image levelImage;
	private LinkedList<Level> levels;
	private GameBoard game;
	private Image shipLaserImage;
	private Image alienLaserImage;

	/*
	 * Constructor for LevelGenerator
	 */
	public LevelGenerator(GameBoard g)
	{
		shipImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "ship.png");
		alienImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "ufo.gif");
		levelImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "space.png");
		shipLaserImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "laser2.png");
		alienLaserImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "sprites" + File.separator + "laser.png");

		try
		{
			// media tracker used for priority of images
			MediaTracker tracker = new MediaTracker(g);
			tracker.addImage(shipImage, 0);
			tracker.addImage(alienImage,1);
			tracker.addImage(levelImage,2);
			tracker.addImage(shipLaserImage, 3);
			tracker.addImage(alienLaserImage, 4);
			tracker.waitForAll();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		game = g;
	}

	/*
	 * Hard level design
	 */
	public LinkedList<Level> GenerateLevelDesign1()
	{
		int shipHeight = 20;

		Ship ship = new Ship(game, 0, game.getEnvironmentHeight() - shipHeight - 90, 45,
				shipHeight,0,0, shipImage, shipLaserImage,3);	// create player ship.

		AlienRectangleCluster cluster1 = new AlienRectangleCluster(game, 4, 6, 30, 30 ,5, 12, 5, 10, alienImage, alienLaserImage, 4);
		AlienCluster cluster2 = new AlienRectangleCluster(game, 4, 7, 30, 30, 5, 12, 6, 10, alienImage, alienLaserImage, 6);
		AlienCluster cluster3 = new AlienRectangleCluster(game, 5, 7, 30, 30, 2, 10, 3, 50, alienImage, alienLaserImage, 2);
		AlienCluster cluster4 = new AlienRectangleCluster(game, 6, 7, 30, 30, 2, 10, 7, 5, alienImage, alienLaserImage, 7);
		AlienCluster cluster5 = new AlienRectangleCluster(game, 6, 7, 30, 30, 6, 20, 1, 2, alienImage, alienLaserImage, 2);
		AlienCluster cluster6 = new AlienRectangleCluster(game, 6, 9, 30, 30, 2, 10, 10, 15, alienImage, alienLaserImage, 8);

		levels = new LinkedList<Level>();
		levels.add(new Level(game, cluster1, ship, 300, 1, levelImage, 0.6));
		levels.add(new Level(game, cluster2, ship, 300, 2, levelImage, 0.5));
		levels.add(new Level(game, cluster3, ship, 300, 3, levelImage, 0.2));
		levels.add(new Level(game, cluster4, ship, 300, 4, levelImage, 0.2));
		levels.add(new Level(game, cluster5, ship, 100, 5, levelImage, 0.4));
		levels.add(new Level(game, cluster6, ship, 100, 6, levelImage, 0.5));

		return levels;
	}

	/*
	 * Easy level design
	 */
	public LinkedList<Level> GenerateEasyLevels()
	{
		int shipHeight = 20;

		Ship ship = new Ship(game, 0, game.getEnvironmentHeight() - shipHeight - 90, 45,
				shipHeight,0,0, shipImage, shipLaserImage,3);	// create player ship.

		AlienCluster cluster1 = new AlienRectangleCluster(game, 3, 6, 30, 30, 2, 10, 2, 8, alienImage, alienLaserImage, 3);
		AlienCluster cluster2 = new AlienRectangleCluster(game, 3, 7, 30, 30, 2, 10, 2, 8, alienImage, alienLaserImage, 4);
		AlienCluster cluster3 = new AlienRectangleCluster(game, 4, 7, 30, 30, 2, 10, 4, 8, alienImage, alienLaserImage, 4);
		AlienCluster cluster4 = new AlienRectangleCluster(game, 4, 7, 30, 30, 2, 10, 4, 8, alienImage, alienLaserImage, 5);
		AlienCluster cluster5 = new AlienRectangleCluster(game, 5, 7, 30, 30, 2, 10, 5, 8, alienImage, alienLaserImage, 5);
		AlienCluster cluster6 = new AlienRectangleCluster(game, 6, 7, 30, 30, 2, 10, 6, 8, alienImage, alienLaserImage, 5);

		levels = new LinkedList<Level>();
		levels.add(new Level(game, cluster1, ship, 300, 1, levelImage, 0.6));
		levels.add(new Level(game, cluster2, ship, 300, 2, levelImage, 0.5));
		levels.add(new Level(game, cluster3, ship, 300, 3, levelImage, 0.4));
		levels.add(new Level(game, cluster4, ship, 200, 4, levelImage, 0.3));
		levels.add(new Level(game, cluster5, ship, 200, 5, levelImage, 0.6));
		levels.add(new Level(game, cluster6, ship, 100, 6, levelImage, 0.5));

		return levels;
	}
}
