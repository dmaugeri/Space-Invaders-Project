import java.awt.Graphics;
import java.awt.Image;

/*
 * An entity which represents an aliens. This class is responsible
 * for creating the alien, setting its movement, and enabling its powers.
 */
public class Alien extends Entity
{
	private Image alienImage;
	private Image alienLaserImage;
	private double x;
	private double y;
	private boolean isAlive;
	private GameBoard game;

	public Alien(GameBoard game, double xPos, double yPos, double shapeWidth, double shapeHeight,
			double xSpeed, double ySpeed, Image alienImage, Image alienLaserImage)
	{
		super(game, xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed);

		this.x = xPos;
		this.y = yPos;
		isAlive = true;
		this.alienImage = alienImage;
		this.alienLaserImage = alienLaserImage;
		this.game = game;
	}

	/*
	 * Moves alien and update its bounding box. Also checks for boundaries.
	 * (non-Javadoc)
	 * @see Entity#autoMove()
	 */
	@Override
	public void autoMove(double delta)
	{
		// we use autoMove2 instead to control speed
		if (isAlive)
		{
			super.checkOvershoot(getXPos(), getYPos());

			double x = getXPos() + getxSpeed() * delta;
			updateBoundingBox((int)x, getYPos());
		}
	}

	/*
	 * Move the alien based on speed * time
	 */
	public void autoMove2(double delta, double xSpeed, double ySpeed)
	{
		if (isAlive)
		{
			x = getXPos() + xSpeed * delta;
			y = getYPos() + ySpeed;
			updateBoundingBox(Math.round(x), (int)y);
		}
	}

	/*
	 * Not used for AI objects
	 */
	@Override
	public void playerMove(double x, double y)
	{
	}

	/*
	 * Check if Alien collides with a ship Projectile
	 * (non-Javadoc)
	 * @see Entity#collidesWith(Entity)
	 */
	@Override
	public boolean collidesWith(Entity other)
	{
		if (other instanceof ShipProjectile && this.intersectsBoundingBox(other.getBoundingBox()))
		{
			isAlive = false;
			return true;
		}
		else
			return false;
	}

	/*
	 * Shoot alien projectiles and check to see if alien already fired a shot.
	 */
	public AlienProjectile shoot(double ySpeed)
	{
		double projHeight = 15;
		double projWidth = 15;

		if (isAlive)
			return new AlienProjectile(getGame(), getXPos() + (getBoundingBoxWidth() / 2),
					getYPos() + projHeight, projWidth, projHeight, 0, ySpeed, alienLaserImage);

		return null;
	}

	/*
	 * Draws the alien character.
	 * (non-Javadoc)
	 * @see Entity#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics g)
	{
		if (isAlive)
			g.drawImage(alienImage, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(), game);
	}

	public boolean isAlive()
	{
		return isAlive;
	}

	public void setLife(boolean life)
	{
		isAlive = life;
	}

	@Override
	public void collisionResponse(Entity other) {
		// TODO Auto-generated method stub
		if(other instanceof Ship) {
			SoundEffect.BGM.endLoop();
			SoundEffect.EXPLODE.play();
			game.isGameOver = true;
		} else if (other instanceof ShipProjectile) {
			this.isAlive = false;
			game.cluster.decrementAlienCounter();
			SoundEffect.ALIEN.play();
			game.setHighScore(100);
			
			if (game.cluster.getAlienCounter() % game.getSpeedDifficulty() == 0)
				game.cluster.increaseSpeed();
			
			if (game.cluster.getAlienCounter() == 0)
			{
				if (game.levelItr.hasNext())
					game.waitingForLevel = true;
				else
				{
					game.playerWon = true;
					game.isGameOver = true;
					SoundEffect.BGM.endLoop();
					SoundEffect.WIN.play();
				}
			}
		}
	}
}
