package net.swing.src.env;

import net.swing.engine.WPS;
import net.swing.ground.Window;

public final class WorldSettings
{
	
	/** number of rows in one room (number of blocks in one column) */
	public static final int							rows					= 20;
	/** number of columns in one room (number of blocks in one row) */
	public static final int							columns					= 20;
	/**
	 * number of rows in one room (number of collision blocks in one column)
	 * Every row of blocks contains two rows of areas
	 */
	public static final int							cRows					= rows * 2;
	/**
	 * number of columns in one room (number of collision blocks in one row)
	 * Every column of blocks contains two columns of areas
	 */
	public static final int							cColumns				= columns * 2;
	/**
	 * Indicates the speed of everything that happens in game (movement of
	 * entities, animation displaying speed and so on)
	 */
	public static final float						timelapse				= 0.07f;
	/**
	 * defines how high the top inventory should be.
	 */
	public static final float						invHeighRatio			= (float) 1 / (float) 15;
	/**
	 * defines how width the side inventory should be.
	 */
	public static final float						invWidthRatio			= (float) 1 / (float) 5;
	/**
	 * <b>Inventory side</b> : <i>width</i> = Window.getWidth() - roomWidth;
	 * <i>height</i> = Widnow.getHeight() ; <i>x</i> = 0; <i>y</i> = 0.
	 * <b>Inventory top</b>: <i>width</i> = roomWidth ; <i>height</i> =
	 * Window.getHeight() - roomHeight ; <i>x</i> = Window.getWidth() -
	 * roomWidth ; <i>y</i> = roomHeight.
	 */
	public static final float						roomHeight				= Window.getHeight() * (1 - invHeighRatio);
	
	/**
	 * <b>Inventory side</b> : <i>width</i> = Window.getWidth() - roomWidth;
	 * <i>height</i> = Widnow.getHeight() ; <i>x</i> = 0; <i>y</i> = 0.
	 * <b>Inventory top</b>: <i>width</i> = roomWidth ; <i>height</i> =
	 * Window.getHeight() - roomHeight ; <i>x</i> = Window.getWidth() -
	 * roomWidth ; <i>y</i> = roomHeight.
	 */
	public static final float						roomWidth				= Window.getWidth() * (1 - invWidthRatio);
	/**
	 * Indicates width of side inventory. Height is equal to total height of
	 * window.
	 */
	public static final float						invSideWidth			= Window.getWidth() - roomWidth;
	
	/**
	 * Width of single block and of course width of cell that contains blocks.
	 * All cells have the same size.
	 */
	public static final float						blockWidth				= roomWidth / columns;
	/**
	 * Height of single block and of course height of cell that contains blocks.
	 * All cells have the same size.
	 */
	public static final float						blockHeight				= roomHeight / rows;
	
	/**
	 * CellSelector is useful when you need to localize cell in a matrix and all
	 * you know is location of point . PAY ATTENTION: the point doesn't really
	 * have to be inside the matrix. If it's out of matrix border then returned
	 * cell could have coordinates like e.g. (-1,5) or (10,12) in matrix 10x10
	 */
	public static final CellSelector				cSelector				= new CellSelector(collWidth(), collHeight(), invSideWidth, 0);
	/** Useful to convert WPS into Pointf and vice-versa */
	public static final WorldPositionSystemShifted	WPS						= new WorldPositionSystemShifted(invSideWidth, 0, roomWidth, roomHeight);
	/**
	 * Quantity of extra collision areas (there is always default collision area
	 * - air). The value of this variable cannot be higher that 49 (id 50 starts
	 * declarations) and it cannot be lower than 2 (which is number of default
	 * collision areas)
	 */
	public static final int							collAreasQuantity		= 15;
	/**
	 * Range in which entities can spot other entities. This range is a square
	 * area because it makes everything simpler (and probably a bit aster)
	 */
	public static final WPS							entityFocusRange		= new WPS(0.3, 0.3);
	/**
	 * The bigger delay the slower reaction of entities. If it's too small
	 * entities will observer things faster than environment can actually change
	 * and when in one room are percent lots of entities it may cause low
	 * performance. Too observant entities may also be too hard to defeat
	 */
	public static float								entityObservationDelay	= 500f;
	
	/** Width of single collision area cell but in WPS */
	public static final double						WPScollWidth			= WPS.getWorldPositionSystem().getA(collWidth());
	/** Height of single collision area cell but in WPS */
	public static final double						WPScollHeight			= WPS.getWorldPositionSystem().getB(collHeight());
	
	public static final int floorSize()
	{
		return rows * columns;
	}
	
	public static final int collAreaSize()
	{
		return cColumns * cRows;
	}
	
	public static float collHeight()
	{
		return roomHeight / cRows;
	}
	
	public static float collWidth()
	{
		return roomWidth / cColumns;
	}
}
