public class SpaceInvaderViewer
{
	public static final int WIDTH = 541;
	public static final int HEIGHT = 691;

	/**
	 * Create a new game which will create a Jframe and the objects the game requires
	 */
	public static void main(String[] args) {
		Util.debug = true;
		TitleScreen screen = new TitleScreen(WIDTH, HEIGHT);

		while (!screen.getMadeGame())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		screen.stopMusic();
		screen.dispose();
		GameBoard g = new GameBoard(WIDTH, HEIGHT);
		g.gameLoop();
	}
}
