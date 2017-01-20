package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;
import net.swing.src.env.Cell;

public final class CollAreaPutArea extends CollArea
{
	private final int[]		ids;
	private final Cell[][]	locations;
	
	/**
	 * PAY ATTENTION that constructors in classes CollAreaPutArea and
	 * CollAreaPutBlock look exactly the same
	 */
	public CollAreaPutArea(String a)
	{
		super(a);
		
		/*
		 * Parts of instructions containing info about locations of one area.
		 * Here is saved string with id and cells "id cell,cell,cell..."
		 */
		String[] parts = a.split("_");
		
		ids = new int[parts.length];
		locations = new Cell[parts.length][];
		/*
		 * part contains two strings: -first with id -second with cells
		 */
		String[] part;
		/*
		 * second string from variable part split with regular expression ";" to
		 * strings containing single cell
		 */
		String[] cells;
		/*
		 * some variables are initialized here so garbage collector doesn't have
		 * to work so hard
		 */
		int j, i;
		for (i = 0; i < parts.length; i++)
		{
			part = parts[i].split(" ");
			/* first part contains id */
			ids[i] = Integer.parseInt(part[0]);
			/* second part contains cells */
			cells = part[1].split(";");
			/*
			 * array containing all locations where one type parsed of areas is
			 * supposed to be located
			 */
			Cell[] locationsOfSingleID = new Cell[cells.length];
			for (j = 0; j < cells.length; j++)
			{/* parsing cells */
				locationsOfSingleID[j] = Cell.parseCell(cells[j]);
			}
			/* adding array of cells to main array of all locations */
			locations[i] = locationsOfSingleID;
		}
	}
	
	@Override
	public Action getAction()
	{
		return Action.PUT_AREA;
	}
	
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
		if (wasAreaChanged)
		{
			for (int i = 0; i < ids.length; i++)
			{
				LunarEngine2DMainClass.getRoom().getCollArea().putBlock(ids[i], locations[i]);
			}
		}
	}
	
}
