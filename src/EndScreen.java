public class EndScreen extends Screen
{
	private boolean playAgain;

	public EndScreen(int environmentWidth, int environmentHeight)
	{
		super(environmentWidth, environmentHeight);
		Input input = new Input(this);
		this.playAgain = false;
	}

	public void setPlayAgain(boolean playAgain)
	{
		this.playAgain = playAgain;
	}

	public boolean getPlayAgain()
	{
		return playAgain;
	}
}
