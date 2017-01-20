package net.swing.src.env;

import net.swing.ground.Pointf;

public class CellSelector
{
	
	private final float	cellWidth, cellHeight;
	/** Indicates location of selection area */
	private float		x, y;
	
	public CellSelector(float cellWidth, float cellHeight, float x, float y)
	{
		this.cellHeight = cellHeight;
		this.cellWidth = cellWidth;
		setLocation(x, y);
	}
	
	/**
	 */
	public Cell getCell(Pointf p)
	{
		return getCell(p.getX(), p.getY());
	}
	
	/**
	 */
	public Cell getCell(float x, float y)
	{
		return getCellShifted(getXonSelectionArea(x), getYonSelectionArea(y));
	}
	
	private Cell getCellShifted(float x, float y)
	{
		return new Cell((int) Math.ceil((double) x / cellWidth), // cell column
				(int) Math.ceil((double) y / cellHeight) // cell row
		);
	}
	
	/**
	 * Returns x coordinates of point on the screen relatively to x coordinates
	 * of location of this selection area
	 * 
	 * @param x
	 *            - x coordinates of point on screen
	 */
	public float getXonSelectionArea(float x)
	{
		return x - getX();
	}
	
	/**
	 * Returns y coordinates of point on the screen relatively to y coordinates
	 * of location of this selection area
	 * 
	 * @param y
	 *            - y coordinates of point on screen
	 */
	public float getYonSelectionArea(float y)
	{
		return y - getY();
	}
	
	public void setLocation(float x, float y)
	{
		setX(x);
		setY(y);
	}
	
	/** Indicates location of selection area */
	public float getX()
	{
		return x;
	}
	
	/** Indicates location of selection area */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/** Indicates location of selection area */
	public float getY()
	{
		return y;
	}
	
	/** Indicates location of selection area */
	public void setY(float y)
	{
		this.y = y;
	}
}
