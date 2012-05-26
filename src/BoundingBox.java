import java.awt.Rectangle;

/*
 * Bounding Box for objects
 */
public interface BoundingBox
{
	public Rectangle getBoundingBox();

	public double getBoundingBoxWidth();

	public double getBoundingBoxHeight();

	public double getBoundingBoxMaxX();

	public double getBoundingBoxMinX();

	public double getBoundingBoxMaxY();

	public double getBoundingBoxMinY();

	public void setBoundingBox(double xPos, double yPos, double width,
			double height);

	public void updateBoundingBox(double xPos, double yPos);

	public boolean intersectsBoundingBox(Rectangle other);
}
