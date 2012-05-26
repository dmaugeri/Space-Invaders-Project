import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

/*
 * Projectiles that are 'fired' by Entity objects
 */
public class Projectile extends Entity
{
	private double x;
	private double y;
	private GameBoard game;
	private Image image;

	/*
	 * Construct a Projectile
	 */
	public Projectile(GameBoard game, double xPos, double yPos, double shapeWidth,
			double shapeHeight, double xSpeed, double ySpeed, Image image)
	{
		super(game, xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed);

		this.x = xPos;
		this.y = yPos;
		this.game = game;
		this.image = image;
	}

	/*
	 * Move projectile object
	 * (non-Javadoc)
	 * @see Entity#autoMove(double)
	 */
	@Override
	public void autoMove(double delta)
	{
		y += getySpeed();
		updateBoundingBox(x, y);
	}

	/*
	 * Method that checks for collisions with this class and
	 * other object. If there is a collision, end the game.
	 * (non-Javadoc)
	 * @see Entity#collidesWith(Entity)
	 */
	@Override
	public boolean collidesWith(Entity other)
	{
		return other instanceof Alien && this.intersectsBoundingBox(other.getBoundingBox());
	}

	/*
	 * Draws laser charge.
	 * (non-Javadoc)
	 * @see Entity#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics g)
	{
		g.drawImage(image, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(),game);
	}

	/*
	 * Not used in AI objects
	 */
	@Override
	public void playerMove(double x, double y)
	{
	}

	@Override
	public void collisionResponse(Entity other) {
		// TODO Auto-generated method stub
		
	}
}
