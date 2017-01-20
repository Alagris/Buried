package net.swing.src.env;

import net.LunarEngine2DMainClass;
import net.swing.src.obj.Block;
import net.swing.src.obj.Things;

public class RoomFloor extends Matrix
{
	
	/** Creation of room filled with one block */
	public RoomFloor(Block blockToFillRoomWith)
	{
		super(WorldSettings.rows, WorldSettings.columns);
		fillWithNumber(blockToFillRoomWith.ID);
	}
	
	/**
	 * Creates new empty room floor. Everything is set to be air so matrix is
	 * filled with values of zero (unless someone change the ID of air)
	 */
	public RoomFloor()
	{
		this(Things.air);
	}
	
	public RoomFloor(int[] IDsToFillMatrix)
	{
		super(WorldSettings.rows, WorldSettings.columns, IDsToFillMatrix);
	}
	
	public RoomFloor(int[] IDsToFillMatrix, int maxValidID)
	{
		super(WorldSettings.rows, WorldSettings.columns, IDsToFillMatrix, maxValidID);
	}
	
	public void putBlock(Block b, Cell... cell)
	{
		putBlock(b.ID, LunarEngine2DMainClass.getThings().size(), cell);
	}
	
}
