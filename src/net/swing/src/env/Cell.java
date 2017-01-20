package net.swing.src.env;

import net.swing.engine.WPS;
import net.swing.ground.Pointf;
import net.swing.src.ent.mind.AImath;

public class Cell
{
	/** x value must be between 1 and value of WorldSettings.columns */
	public int	x	= 1;
	/** y value must be between 1 and value of WorldSettings.rows */
	public int	y	= 1;
	
	public Cell(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public Cell()
	{
	}
	
	public Cell(Cell c)
	{
		set(c);
	}
	
	public void set(int x2, int y2)
	{
		setX(x2);
		setY(y2);
	}
	
	/**
	 * Return true if current x value is possible
	 * 
	 * @param columns
	 */
	public boolean validateX(int columns)
	{
		return x > 0 && x <= columns;
	}
	
	/**
	 * Return true if current y value is possible
	 * 
	 * @param rows
	 */
	public boolean validateY(int rows)
	{
		return y > 0 && y <= rows;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Return true if current x and y values are possible
	 * 
	 * @param rows
	 * @param columns
	 */
	public boolean validateBoth(int rows, int columns)
	{
		return validateX(columns) && validateY(rows);
	}
	
	@Override
	public String toString()
	{
		return "Cell{ X:" + x + "and Y:" + y + "}";
	}
	
	public String toCommand()
	{
		return x + "," + y;
	}
	
	public static Cell parseCell(String s)
	{
		// FAT_METHOD: pull objects out of method
		Cell p = new Cell();
		boolean nowA = true;
		String number = "";
		for (char c : s.toCharArray())
		{
			if (Character.isDigit(c))
			{
				number = number + c;
			}
			else if (nowA)
			{
				nowA = false;
				if (number != null)
				{
					p.setX(Integer.parseInt(number));
				}
				number = "";
			}
			else
			{
				break;
			}
		}
		if (number != null)
		{
			p.setY(Integer.parseInt(number));
		}
		return p;
	}
	
	public boolean equalsCell(Cell lastCell)
	{
		return lastCell != null && lastCell.x == x && lastCell.y == y;
	}
	
	public void set(Cell anotherCell)
	{
		setX(anotherCell.x);
		setY(anotherCell.y);
	}
	
	/** Returns WPS of cell center */
	public WPS getCellCenter(float cellWidth, float cellHeight)
	{
		return WorldSettings.WPS.getWorldPositionSystem().getWPS(getCellCenterDirectLocationX(cellWidth), getCellCenterDirectLocationY(cellHeight));
	}
	
	/** Returns WPS of cell bottom left corner */
	public WPS getCellCorner(float cellWidth, float cellHeight)
	{
		return WorldSettings.WPS.getWorldPositionSystem().getWPS(getCellCornerDirectLocationX(cellWidth), getCellCornerDirectLocationY(cellHeight));
	}
	
	/** Returns WPS of cell center */
	public Pointf getCellCenterDirectLocation(float cellWidth, float cellHeight)
	{
		return new Pointf(getCellCenterDirectLocationX(cellWidth), getCellCenterDirectLocationY(cellHeight));
	}
	
	/** Returns WPS of cell bottom left corner */
	public Pointf getCellCornerDirectLocation(float cellWidth, float cellHeight)
	{
		return new Pointf(getCellCornerDirectLocationX(cellWidth), getCellCornerDirectLocationY(cellHeight));
	}
	
	/** Returns WPS of cell center */
	public float getCellCenterDirectLocationX(float cellWidth)
	{
		return cellWidth * (x - 0.5f);
	}
	
	/** Returns WPS of cell bottom left corner */
	public float getCellCornerDirectLocationX(float cellWidth)
	{
		return cellWidth * (x - 1);
	}
	
	/** Returns WPS of cell center */
	public float getCellCenterDirectLocationY(float cellHeight)
	{
		return cellHeight * (y - 0.5f);
	}
	
	/** Returns WPS of cell bottom left corner */
	public float getCellCornerDirectLocationY(float cellHeight)
	{
		return cellHeight * (y - 1);
	}
	
	public int getDistanceToCell(Cell c)
	{
		return Math.abs(c.x - x) + Math.abs(c.y - y);
	}
	
	public double getStraightDistanceToCell(Cell c)
	{
		return AImath.doPythagoreanTheorem(x - c.x, y - c.y);
	}
	
}
