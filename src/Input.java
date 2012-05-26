import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/* Input class is responsible for mouse movement which operates the player(ship) */
public class Input
{
	private Screen screen;
	private Ship ship;
	private Robot robot;
	private int shotDelay;

	public Input(final Screen screen)
	{
		class MouseL implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (screen instanceof TitleScreen)
					((TitleScreen)screen).setMadeGame(true);
				if (screen instanceof EndScreen)
					((EndScreen)screen).setPlayAgain(true);
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		}

		MouseListener listener = new MouseL();
		screen.addMouseListener(listener);
	}

	/*
	 * Input for game screen
	 */
	public Input(final Screen screen, final Ship ship)
	{
		this.ship = ship;
		shotDelay = 100; //default ms

		try
		{
			robot = new Robot();
		}
		catch (Exception e)
		{
			System.out.println("Robot threw exception");
		}

		class MouseL implements MouseListener
		{
			long lastShotTime = 0;

			@Override
			public void mouseClicked(MouseEvent e)
			{
				GameBoard g = (GameBoard) screen;
				if (!g.isGameOver && !g.waitingForLevel)
				{
					long currentTime = System.currentTimeMillis();
					if (currentTime - lastShotTime> shotDelay)
					{
						((GameBoard)screen).addToShipProjectiles(ship.shoot());
						lastShotTime = currentTime;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastShotTime > shotDelay)
				{
					GameBoard g = ((GameBoard)screen);
					if (!g.waitingForLevel && !g.isGameOver)
					{
						((GameBoard)screen).addToShipProjectiles(ship.shoot());
						lastShotTime = currentTime;
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				GameBoard g = (GameBoard) screen;
				if (!g.isGameOver && !g.waitingForLevel)
					setPrevX(e.getX());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		}

		class MouseM implements MouseMotionListener
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				GameBoard g = (GameBoard)screen;
				if (!g.isGameOver && !g.waitingForLevel)
					moveShip(e);
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				GameBoard g = (GameBoard) screen;
				if (!g.isGameOver && !g.waitingForLevel)
					moveShip(e);
			}
		}

		MouseListener mouseListener = new MouseL();
		MouseMotionListener mouseMotion = new MouseM();

		screen.addMouseListener(mouseListener);
		screen.addMouseMotionListener(mouseMotion);
	}

	/*
	 * Set the ships previous position so we can calculate displacement
	 */
	private void setPrevX(double x)
	{
		ship.setPrevMouseX(x);
	}

	/*
	 * Set shot delay of ship
	 */
	public void setShotDelay(int delay)
	{
		shotDelay = delay;
	}

	/*
	 * Move ship based on current mouse position
	 */
	private void moveShip(MouseEvent e)
	{
		double x = (double)e.getX();
		double y = (double)e.getY();

		ship.playerMove(x, y);
	}
}
