import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * This method is responsible for creating boundingbox for each 2D shape displayed during game
 */
public abstract class Shape2D implements BoundingBox
{
	private double xPos;
	private double yPos;
	private Rectangle boundingBox;
	public static final double DEFAULT_WIDTH = 2;
	public static final double DEFAULT_HEIGHT = 2;

	/*
	 * Construct a Shape2D with defaults
	 */
	public Shape2D()
	{
		xPos = 0;
		yPos = 0;
		boundingBox = new Rectangle(0, 0, 0, 0);
	}

	/*
	 * Construct a Shape2D with x,y
	 */
	public Shape2D(double x, double y)
	{
		xPos = x;
		yPos = y;

		setBoundingBox(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/*
	 * Construct a Shape2D
	 */
	public Shape2D(double x, double y, double width, double height)
	{
		xPos = x;
		yPos = y;

		boundingBox = new Rectangle(0, 0, 0, 0);
		setBoundingBox(x, y, width, height);
	}

	/*
	 * Get X coordinate of Shape
	 */
	public final double getXPos()
	{
		return xPos;
	}

	/*
	 * Get Y coordinate of Shape
	 */
	public final double getYPos()
	{
		return yPos;
	}

	/*
	 * Get the Bounding Box of Shape
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBox()
	 */
	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}

	/*
	 * Get Bounding Box Width
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxWidth()
	 */
	public double getBoundingBoxWidth()
	{
		return boundingBox.getWidth();
	}

	/*
	 * Get bounding box Height
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxHeight()
	 */
	public double getBoundingBoxHeight()
	{
		return boundingBox.getHeight();
	}

	/*
	 * Get Bounding Box Max X coordinate
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxMaxX()
	 */
	public double getBoundingBoxMaxX()
	{
		return boundingBox.getMaxX();
	}

	/*
	 * Get bounding box min x coordinate
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxMinX()
	 */
	public double getBoundingBoxMinX()
	{
		return boundingBox.getMinX();
	}

	/*
	 * Get bounding box max Y coordinate
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxMaxY()
	 */
	public double getBoundingBoxMaxY()
	{
		return boundingBox.getMaxY();
	}

	/*
	 * Get bounding box min Y coordinate
	 * (non-Javadoc)
	 * @see BoundingBox#getBoundingBoxMinY()
	 */
	public double getBoundingBoxMinY()
	{
		return boundingBox.getMinY();
	}

	/*
	 * Set the bounding boxs x,y,width,height
	 * (non-Javadoc)
	 * @see BoundingBox#setBoundingBox(double, double, double, double)
	 */
	public void setBoundingBox(double xPos, double yPos, double width,
			double height)
	{
		boundingBox.setRect(xPos, yPos, width, height);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/*
	 * Set bounding box x and y
	 * (non-Javadoc)
	 * @see BoundingBox#updateBoundingBox(double, double)
	 */
	public void updateBoundingBox(double xPos, double yPos)
	{
		boundingBox.setRect(xPos, yPos, getBoundingBoxWidth(),
				getBoundingBoxHeight());
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/*
	 * Check if bounding box intersects another box
	 * (non-Javadoc)
	 * @see BoundingBox#intersectsBoundingBox(java.awt.Rectangle)
	 */
	public boolean intersectsBoundingBox(Rectangle other)
	{
		return boundingBox.intersects(other);
	}

	/*
	 * Draw the bounding box
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		((Graphics2D) g).draw(boundingBox);
	}
}
