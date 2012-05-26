import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

/*
 * An Entity which represents the player (ship). This class is responsible
 * for creating the ship, setting its movement, and enabling its powers
 */
public class Ship extends Entity
{
	private Image shipImage;
	private Image laserImage;
	private double x;
	private double y;
	private Rectangle2D.Double ship;
	private double prevMouseX;
	public boolean shotFired;
	private int projSpeed;
	private int lives;
	private double xSpeed;
	private GameBoard game;

	/*
	 * Construct a Ship
	 */
	public Ship(GameBoard game, double xPos, double yPos, double shapeWidth,
			double shapeHeight, double xSpeed, double ySpeed, Image shipImage, Image laserImage,int lives)
	{
		super(game, xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed);
		this.shipImage = shipImage;
		this.lives = lives; // Default, add parameter later
		this.ship = new Rectangle2D.Double(xPos, yPos, shapeWidth, shapeHeight);
		projSpeed = 8; // Default projectile speed
		this.x = xPos;
		this.y = yPos;
		this.xSpeed = xSpeed;
		this.game = game;
		this.laserImage = laserImage;
	}

	/*
	 * Method that shoot ship projectile. The method also checks
	 * to see if the ship already fired a shot b/c ship can only shot one laser at a time.
	 */
	public ShipProjectile shoot()
	{
		double projHeight = 10; // Dummy
		double projWidth = 10; // Dummy
		// projSpeed = 5;

		return new ShipProjectile(getGame(), getXPos() + (int)(getBoundingBoxWidth() / 2),
				getYPos() - projHeight, projWidth, projHeight, 0, projSpeed, laserImage);
	}

	// not used in Player Objects
	public void autoMove(double delta)
	{
	}

	/*
	 * Movement of the player (ship).
	 * (non-Javadoc)
	 * @see Entity#playerMove(double, double)
	 */
	public void playerMove(double x, double y)
	{
		this.x = x;

		if (x + this.getBoundingBoxWidth() / 2 >= getGame().getEnvironmentWidth())	// check right bounds
			this.x = getGame().getEnvironmentWidth() - getBoundingBoxWidth();
		if (this.getBoundingBoxMinX() <= 0)
			this.x = 0;

		updateBoundingBox((int)x, this.y);
	}

	public void setPrevMouseX(double x)
	{
		prevMouseX = x;
	}

	/*
	 *  Draws the ship or players character.
	 * (non-Javadoc)
	 * @see Entity#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics g)
	{
		//super.draw(g);
		g.drawImage(shipImage, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(), game);
	}

	/*
	 * Check if ship collides with AlienProjectile
	 * (non-Javadoc)
	 * @see Entity#collidesWith(Entity)
	 */
	@Override
	public boolean collidesWith(Entity other)
	{
		return other instanceof AlienProjectile && this.intersectsBoundingBox(other.getBoundingBox());
	}

	public void decrementLife()
	{
		lives--;
	}

	public int getLives()
	{
		return lives;
	}

	public void setLife(int lives)
	{
		this.lives = lives;
	}

	public Image getImage()
	{
		return shipImage;
	}

	@Override
	public void collisionResponse(Entity other) {
		// TODO Auto-generated method stub
		if(other instanceof Alien) {
			this.lives = 0;
		} else if (other instanceof AlienProjectile) {
			SoundEffect.SHIPDIE.play();
			this.lives--;
			if (this.lives <= -1)
			{
				game.isGameOver = true;
				game.cluster.resetShotCounter();
				SoundEffect.BGM.endLoop();
				SoundEffect.EXPLODE.play();
			}
		}
	}
}
