import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

/*
 * Alien Projectile class is responsible for any collision that takes place between
 * - aliens bullet and player
 */
public class AlienProjectile extends Projectile
{
	private Image image;
	private double x;
	private double y;
	private GameBoard game;

	public AlienProjectile(GameBoard game, double xPos, double yPos, double shapeWidth, double shapeHeight,
			double xSpeed, double ySpeed, Image image)
	{
		super(game, xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed, image);
		this.x = xPos;
		this.y = yPos;
		this.image = image;
		this.game = game;
	}

	/*
	 * Method that checks for collisions with this class and other object. If there is a collision, end the game.
	 * (non-Javadoc)
	 * @see Projectile#collidesWith(Entity)
	 */
	public boolean collidesWith(Entity other)
	{
		return other instanceof Ship && this.intersectsBoundingBox(other.getBoundingBox());
	}

	/*
	 * Method that moves the alien projectile and update its bounding box. Method also checks for boundaries.
	 * (non-Javadoc)
	 * @see Projectile#autoMove()
	 */
	public void autoMove(double delta)
	{
		y = y + getySpeed() *delta;
		updateBoundingBox((int)x, (int)y);
	}

	/*
	 * Draws aliens laser charge.
	 * (non-Javadoc)
	 * @see Projectile#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics g)
	{
		g.drawImage(image, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(), game);
	}

	/*
	 * Check if projectile is below game environment
	 */
	public boolean isOutsideBounds()
	{
		return getYPos() > getGame().getEnvironmentHeight() - 100;
	}
}
