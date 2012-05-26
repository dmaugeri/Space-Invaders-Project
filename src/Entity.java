import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * Entity class is responsible for resolving collision and
 * movement based on a set of properties defined by subclass.
 */
public abstract class Entity extends Shape2D
{
	private double xSpeed;
	private double ySpeed;
	private GameBoard game;

	public Entity(GameBoard game, double xPos, double yPos, double shapeWidth,
			double shapeHeight, double xSpeed, double ySpeed)
	{
		super(xPos, yPos, shapeWidth, shapeHeight);
		setSpeed(xSpeed, ySpeed);
		this.game = game;
	}

	/*
	 *  Move the shape( alien, player or bullet) around the rectangular environment
	 *  Do not allow the shape to go outside of the environment boundaries
	 */
	public void checkOvershoot(double x, double y)
	{
		int environmentWidth = game.getEnvironmentWidth();
		int environmentHeight = game.getEnvironmentHeight();

		// reflect the object if it goes too far
		int overShoot = (int)(x + getBoundingBoxWidth() - environmentWidth);
		if (x <= 0)
		{
			x = 0;
			xSpeed = -xSpeed;
		}
		else if (overShoot > 0)
		{
			x = environmentWidth - getBoundingBoxWidth();
			xSpeed = -xSpeed;
		}

		//  reflect the object if it goes too far
		overShoot = (int)(y + getBoundingBoxHeight() / 2 - environmentHeight);
		if (y <= 0)
		{
			y = 0;
			ySpeed = -ySpeed;
		}
		else if (y >= environmentWidth)
		{
			y = environmentHeight - getBoundingBoxHeight();
			ySpeed = -ySpeed;
		}

		// move the shape to the new position
		updateBoundingBox(x, y);
	}

	public void setSpeed(double xSpeed, double ySpeed)
	{
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public double getySpeed()
	{
		return ySpeed;
	}

	public double getxSpeed()
	{
		return xSpeed;
	}

	/*
	 * Get the 'Game' JComponent the object belongs to
	 */
	public GameBoard getGame()
	{
		return game;
	}

	/*
	 * Draw method
	 * (non-Javadoc)
	 * @see Shape2D#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics g)
	{
		super.draw(g);
	}

	/*
	 * Move an object's coordinates automatically
	 */
	abstract public void autoMove(double delta);

	/*
	 * Move a player controlled object
	 */
	abstract public void playerMove(double x, double y);

	/*
	 * Detect if Entity collided with another.
	 */
	abstract public boolean collidesWith(Entity other);
	
	abstract public void collisionResponse(Entity other);
}
