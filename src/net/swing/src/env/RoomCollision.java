package net.swing.src.env;

import net.swing.src.obj.CollAreas;

public class RoomCollision extends Matrix
{
	
	/**
	 * Creation of empty room (everything is air)
	 * 
	 * @param columns
	 * @param rows
	 */
	public RoomCollision()
	{
		super(WorldSettings.cRows, WorldSettings.cColumns);
	}
	
	/** Creation of room filled with blocks from an array */
	public RoomCollision(int[] IDsToFillRoom)
	{
		super(WorldSettings.cRows, WorldSettings.cColumns, IDsToFillRoom, CollAreas.getAreas().length);
	}
	
	@Override
	public void putBlock(int areaID, Cell... cell)
	{
		super.putBlock(areaID, CollAreas.getAreas().length, cell);
	}
	
}
