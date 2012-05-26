import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/*
 * ShipProjectile class is responsible for any collision that takes place between
 * ships(player) laser charge and alien
 */
public class ShipProjectile extends Projectile
{
	private Image image;
	private double x, y;
	private GameBoard game;
	
	public ShipProjectile(GameBoard game, double xPos, double yPos, double shapeWidth, double shapeHeight,
			double xSpeed, double ySpeed, Image image)
	{
		super(game, xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed, image);
		this.image = image;
		this.x = xPos;
		this.y = yPos;
		this.game = game;
	}

	/*
	 * Method that checks for collisions with this class and other object.
	 * (non-Javadoc)
	 * @see Projectile#collidesWith(Entity)
	 */
	public boolean collidesWith(Entity other)
	{
		return other instanceof Alien && this.intersectsBoundingBox(other.getBoundingBox());
	}

	/*
	 * Method that moves the bullet and update its bounding box. Method also checks for boundaries.
	 * (non-Javadoc)
	 * @see Projectile#autoMove()
	 */
	public void autoMove(double delta)
	{
		y -= getySpeed() * delta;
		updateBoundingBox(x, (int)y);
	}
	/*
	 * (non-Javadoc)
	 * @see Projectile#draw(java.awt.Graphics2D)
	 * Draws ships(players) laser charge.
	 */
	public void draw(Graphics g)
	{
		//((Graphics2D) g).draw(super.getBoundingBox());
		g.drawImage(image, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(), game);
	}

	public boolean isOutsideBounds()
	{
		return this.getBoundingBoxMaxY() < 0;
	}
}
