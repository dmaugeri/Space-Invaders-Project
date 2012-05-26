import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Screen class is responsible for creating the main window that houses the game.
 * - sets background to a certain color
 * - sets screen title
 * - creates stars on back ground
 * - while playing the game cursor becomes invisible
 */
public class Screen extends JComponent
{
	private int environmentWidth;
	private int environmentHeight;
	private JFrame frame;

	/*
	 * Construct a Screen
	 */
	public Screen(int environmentWidth, int environmentHeight)
	{
		frame = new JFrame();
		frame.setTitle("Space Invaders");

		frame.setBounds(0, 0, environmentWidth * 2, environmentHeight * 2);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(this);

		frame.setVisible(true);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(environmentWidth,
				environmentHeight));
		panel.setBackground(Color.BLACK);
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);	// no resize

		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");

		frame.getContentPane().setCursor(blankCursor);
		this.environmentWidth = environmentWidth;
		this.environmentHeight = environmentHeight;
		this.setFocusable(true);
	}

	/*
	 * Dispose of frame
	 */
	public void dispose()
	{
		frame.dispose();
	}

	/*
	 * Return width of the content panel
	 */
	public int getEnvironmentWidth()
	{
		return environmentWidth;
	}

	/*
	 * Return height of the content panel
	 */
	public int getEnvironmentHeight()
	{
		return environmentHeight;
	}

	/*
	 * Set the cursor visible
	 */
	public void setCursorVisible()
	{
		frame.getContentPane().setCursor(Cursor.getDefaultCursor());
	}
}
