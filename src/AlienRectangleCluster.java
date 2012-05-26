import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/*
 * AlienRectangleCluster manages a rectangle cluster of Aliens
 * Cluster can shoot, move, and return alive Aliens
 */
public class AlienRectangleCluster extends AlienCluster
{
	private final int SPACER = 20;
	private double xSpeed;
	private double ySpeed;
	private double fireRate;
	private double shotSpeed;
	private Alien rightMostAlien;
	private Alien leftMostAlien;
	private Alien bottomMostAlien;
	private int alienCounter;
	private int shotCounter;
	private Alien[][] aliens;
	private GameBoard game;
	private int columns;
	private int rows;
	private int shotLimit;

	public AlienRectangleCluster(GameBoard game, int rows, int columns, int alienWidth, int alienHeight,
			double xSpeed,double ySpeed,double fireRate, int shotLimit, Image alienImage, Image alienLaserImage,double shotSpeed)
	{
		super(game, rows, columns, alienWidth, alienHeight, xSpeed, ySpeed, fireRate, shotLimit, alienImage, alienLaserImage, shotSpeed);

		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.game = game;
		this.columns = columns;
		this.rows = rows;
		this.shotLimit = shotLimit;
		this.fireRate = fireRate;
		this.shotSpeed = shotSpeed;
		this.shotCounter = 0;

		aliens = new Alien[rows][columns];

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				aliens[i][j] = new Alien(game, SPACER + j * (alienWidth + SPACER),
						SPACER + i * (alienHeight + SPACER), alienWidth,
						alienHeight, xSpeed, ySpeed, alienImage, alienLaserImage);

		setAliens(aliens);

		this.rightMostAlien = aliens[0][columns -1];
		this.leftMostAlien = aliens[0][0];
		this.bottomMostAlien = aliens[rows - 1][columns - 1];
		this.alienCounter = rows * columns;
	}

	/*
	 * Move the cluster of aliens
	 * (non-Javadoc)
	 * @see AlienCluster#autoMove(double)
	 */
	public void autoMove(double delta)
	{
		double ySp = 0;
		double xSp = xSpeed;

		if (!rightMostAlien.isAlive())
			rightMostAlien = findRightMostAlien();

		if (!leftMostAlien.isAlive())
			leftMostAlien = findLeftMostAlien();

		if (!leftMostAlien.isAlive())
			leftMostAlien = findBottomMostAlien();

		if (rightMostAlien.getBoundingBoxMaxX() + xSpeed >= game.getEnvironmentWidth())
		{
			xSpeed = -xSpeed;
			xSp = xSpeed;

			if (rightMostAlien.getBoundingBoxMaxX() + xSpeed > game.getEnvironmentWidth())	//alien somehow outside right-border
			{
				xSp = game.getEnvironmentWidth() - rightMostAlien.getBoundingBoxMaxX();	//bring it back to in the border
				if (delta < 1)
					xSp = xSp/delta;
			}

			ySp = this.ySpeed;
		}

		if (leftMostAlien.getXPos() + xSpeed <= 0)
		{
			xSpeed = -xSpeed;
			xSp = xSpeed;

			if (leftMostAlien.getXPos() + xSpeed < 0)	//alien somehow outside left-border
			{
				xSp = -leftMostAlien.getXPos();		//bring it back to in the border
				if (delta < 1)
					xSp = xSp/delta;
			}

			ySp = this.ySpeed;
		}

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				aliens[i][j].autoMove2(delta, xSp, ySp);
	}

	public ArrayList<Alien> getAliveAliens()
	{
		ArrayList<Alien> a = new ArrayList<Alien>();

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (aliens[i][j] != null && aliens[i][j].isAlive())
					a.add(aliens[i][j]);

		return a;
	}

	public void draw(Graphics g)
	{
		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (aliens[i][j] != null && aliens[i][j].isAlive())
					aliens[i][j].draw(g);
	}

	/*
	 * Shoot projectiles
	 * (non-Javadoc)
	 * @see AlienCluster#shoot()
	 */
	public ArrayList<AlienProjectile> shoot()
	{
		ArrayList<AlienProjectile> projectiles = new ArrayList<AlienProjectile>();

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (aliens[i][j] != null && aliens[i][j].isAlive())
				{
					int random = (int)(Math.random() * 500) ;
					if (random < fireRate && shotCounter <= shotLimit)
					{
						projectiles.add(aliens[i][j].shoot(shotSpeed));
						shotCounter++;
					}
				}

		return projectiles;
	}

	@Override
	public void decrementAlienCounter()
	{
		alienCounter--;
	}

	@Override
	public int getAlienCounter()
	{
		return alienCounter;
	}

	@Override
	public void setAlienCounter(int x)
	{
		alienCounter = x;
	}

	@Override
	public Alien getBottomMostAlien()
	{
		return bottomMostAlien;
	}

	@Override
	public Alien getRightMostAlien()
	{
		return rightMostAlien;
	}

	public void increaseSpeed()
	{
		ySpeed++;
		xSpeed += xSpeed > 0 ? 1 : -1;
	}

	@Override
	public Alien getLeftMostAlien()
	{
		return leftMostAlien;
	}

	public void decrementShotCounter()
	{
		shotCounter--;
	}

	@Override
	public void resetShotCounter()
	{
		shotCounter = 0;
	}
}
