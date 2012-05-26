import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/*
 * Cluster of Aliens
 * make sure to call setAliens(Alien[][] aliens) before using any of the finding methods
 */
public abstract class AlienCluster
{
	//public Alien rightMostAlien;
	//public Alien leftMostAlien;
	//public Alien bottomMostAlien;
	private Alien[][] aliens;
	private int rows;
	private int columns;

	public AlienCluster(GameBoard game, int rows,int columns, int alienWidth, int alienHeight,
			double xSpeed, double ySpeed,double fireRate, int shotLimit, Image alienImage,
			Image alienLaserImage,double shotSpeed)	// took out xspeed, yspeed, shotPattern
	{
		this.rows = rows;
		this.columns = columns;
		aliens = new Alien[rows][columns];
	}

	/**
	 * @Precondition setAliens(Alien[][] aliens) must be called at minimum once
	 * @return
	 */
	public Alien findLeftMostAlien()
	{
		Alien current = null;

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (current == null && aliens[i][j].isAlive())
					current = aliens[i][j];
				else if (aliens[i][j].isAlive() && aliens[i][j].getXPos() <= current.getXPos())
					current = aliens[i][j];

		return current;
	}

	/**
	 * @Precondition setAliens(Alien[][] aliens) must be called at minimum once
	 * @return
	 */
	public Alien findRightMostAlien()
	{
		Alien current = null;

		for (int i = 0; i < rows; ++i)
			for (int j = columns - 1; j >= 0; --j)
				if (current == null && aliens[i][j].isAlive())
					current = aliens[i][j];
				else if (aliens[i][j].isAlive() && aliens[i][j].getXPos() >= current.getXPos())
					current = aliens[i][j];

		return current;
	}

	/**
	 * @Precondition setAliens(Alien[][] aliens) must be called at minimum once
	 * @return
	 */
	public Alien findBottomMostAlien()
	{
		Alien current = null;

		for (int i = rows-1; i >= 0; --i)
			for (int j = 0; j < columns; ++j)
				if (current == null && aliens[i][j].isAlive())
					current = aliens[i][j];
				else if (aliens[i][j].isAlive() && aliens[i][j].getYPos() >= current.getYPos())
					current = aliens[i][j];

		return current;
	}

	public int getTotalAliens()
	{
		return rows * columns;
	}

	public void setAliens(Alien[][] aliens)
	{
		this.aliens = aliens;
	}

	public abstract void increaseSpeed();
	public abstract void decrementShotCounter();
	public abstract void resetShotCounter();
	public abstract ArrayList<AlienProjectile> shoot();
	public abstract void draw(Graphics g);
	public abstract ArrayList<Alien> getAliveAliens();
	public abstract void decrementAlienCounter();
	public abstract int getAlienCounter();
	public abstract void setAlienCounter(int x);
	public abstract Alien getBottomMostAlien();
	public abstract Alien getRightMostAlien();
	public abstract Alien getLeftMostAlien();
	public abstract void autoMove(double delta);
}
