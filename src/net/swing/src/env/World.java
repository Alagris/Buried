package net.swing.src.env;

import java.io.File;

import net.swing.src.data.DataCoder;
import net.swing.src.ent.Entities;
import net.swing.src.obj.Things;

public class World
{
	
	private Room		room;
	
	private Things		things;
	
	private Entities	entities;
	
	public Room getRoom()
	{
		return room;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public Things getThings()
	{
		return things;
	}
	
	public void setThings(Things things)
	{
		this.things = things;
	}
	
	/** loads block sets IF those sets aren't already loaded. */
	public void loadThings(String[] blockSets)
	{
		if (things != null)
		{
			if (things.haveEqualSets(blockSets))
			{
				return;
			}
			things.cleanUp();
		}
		this.things = new Things(blockSets);
	}
	
	/**
	 * Reads from info file sets used in this world and then loads block sets IF
	 * those sets aren't already loaded. Each set should be saved in separate
	 * line.
	 */
	public boolean loadThings(File info)
	{
		String[] sets = DataCoder.readLinesArray(info, ";TSet;");
		
		if (sets.length > 0)
		{
			loadThings(sets);
			return true;
			
		}
		return false;
		
	}
	
	public boolean loadEntities(File info)
	{
		String[] ents = DataCoder.readLinesArray(info, ";Ent;");
		try
		{
			entities = new Entities(ents);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Entities getEntities()
	{
		return entities;
	}
	
	public void setEntities(Entities entities)
	{
		if (entities == null)
		{
			throw new NullPointerException("entities parameter is null");
		}
		else
		{
			this.entities = entities;
		}
		
	}
	
}
