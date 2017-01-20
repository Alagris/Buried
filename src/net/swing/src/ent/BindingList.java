package net.swing.src.ent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import net.swing.engine.WPS;
import net.swing.src.ent.Mind.DefaultMind;
import net.swing.src.ent.mind.AImath;
import net.swing.src.env.Room;

public final class BindingList
{
	
	private Living[]	ents;
	
	/** Creates array of entities. */
	public BindingList(Entity[] ents, Mind[] minds)
	{
		for (int i = 0; i < ents.length; i++)
		{
			this.ents[i] = new Living(ents[i], minds[i]);
		}
	}
	
	public BindingList()
	{
		ents = new Living[0];
	}
	
	/**
	 * Parses data
	 * 
	 * @param roomFile
	 */
	public BindingList(File roomFile)
	{
		try
		{
			Scanner sc = new Scanner(roomFile);
			String[] s;
			ArrayList<Living> lines = new ArrayList<Living>();
			while (sc.hasNextLine())
			{
				s = sc.nextLine().split(":");// Syntax => [name]:[WPS]:[program]
				if (s.length < 2) continue;
				if (s.length == 3)
				{
					try
					{
						lines.add(new Living(s[0], new Mind(s[2])));
					}
					catch (NullPointerException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					try
					{
						lines.add(new Living(s[0], new Mind(DefaultMind.MINDLESS)));
					}
					catch (NullPointerException e)
					{
						e.printStackTrace();
					}
				}
				lines.get(lines.size() - 1).getEnt().index = lines.size() - 1; // adding
																				// index
																				// after
																				// array
																				// expansion
				lines.get(lines.size() - 1).getEnt().set(WPS.parseCoordinates(s[1]));// adding
																						// WPS
																						// info
				System.out.println("Loaded entity " + lines.get(lines.size() - 1).getEnt().name + " [line:" + lines.size() + "] {WPS:" + lines.get(lines.size() - 1).getEnt().toCommand() + "}");
			}
			if (lines.isEmpty())
			{
				ents = new Living[0];
			}
			else
			{
				ents = lines.toArray(new Living[0]);
			}
			System.out.println("Total entities quantity: " + ents.length);
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Syntax => [name]:[WPS]:[program] */
	public String packData()
	{
		String s = "";
		for (Living l : ents)
		{
			if (l.getMind().getLink() < 0)
			{
				s = s + l.getEnt().name + ":" + l.getEnt().toCommand() + "\n";
			}
			else
			{
				s = s + l.getEnt().name + ":" + l.getEnt().toCommand() + ":" + l.getMind().getLink() + "\n";
			}
		}
		return s;
	}
	
	/**
	 * Attaches entity to array and returns its index. If there is no place for
	 * new Entity, value -1 will be returned.
	 */
	public int addThisInstance(Entity ent)
	{
		return add(new Living(ent));
	}
	
	/**
	 * Attaches entity to array and returns its index.
	 */
	public int add(Living l)
	{
		resize(ents.length + 1);
		/* notice that now ent.length has higher value */
		ents[ents.length - 1] = l;
		l.getEnt().index = ents.length - 1;
		return l.getEnt().index;
	}
	
	/**
	 * Sorts entities due to their y position and then calls update()
	 * 
	 * @param areas
	 */
	public void updateOrganized(Room r)
	{
		// TODO: remove or move back to room those entities that pass bounds of
		// room or spawn outside of room area
		Arrays.sort(ents);
		update(r);
	}
	
	public void render()
	{
		for (int i = 0; i < ents.length; i++)
		{
			ents[i].getEnt().render();
		}
	}
	
	public void update(Room r)
	{
		for (int i = 0; i < ents.length; i++)
		{
			ents[i].update(r);
		}
	}
	
	public void removeEntity(int index)
	{
		if (index < 0) return;
		ArrayList<Living> newArray = new ArrayList<Living>(Arrays.asList(ents));
		newArray.remove(index);
		ents = newArray.toArray(new Living[0]);
	}
	
	/** index is the same as line-1 */
	public Living getEntity(int line)
	{
		return ents[line - 1];
	}
	
	/** index is the same as line-1 */
	public Living getEntityAtIndex(int index)
	{
		return ents[index];
	}
	
	/** Returns entity at the last index */
	public Living getLast()
	{
		return ents[ents.length - 1];
	}
	
	public Living[] getCloneOfList()
	{
		return ents.clone();
	}
	
	public void resize(int newSize)
	{
		if (newSize != ents.length)
		{
			ents = Arrays.copyOf(ents, newSize);
		}
	}
	
	/** returns null if index is out of bounds */
	public WPS getEntLocation(int index)
	{
		if (AImath.validateIndex(index, ents.length))
		{
			return ents[index].getEnt();
		}
		else
		{
			return null;
		}
	}
	
	/** returns -1 if index is out of bounds */
	public double getEntB(int index)
	{
		if (AImath.validateIndex(index, ents.length))
		{
			return ents[index].getEnt().b;
		}
		else
		{
			return -1;
		}
	}
	
	/** returns -1 if index is out of bounds */
	public double getEntA(int index)
	{
		if (AImath.validateIndex(index, ents.length))
		{
			return ents[index].getEnt().a;
		}
		else
		{
			return -1;
		}
	}
	
	// public boolean isCollidingWithNearest(WPS wps)
	// {
	// return getEntity(getNearest(wps) + 1).getEnt().isWPSinside(wps);
	// }
	//
	// /**
	// * return index of nearest to this WPS entity . If doesn't collide will
	// * return -1
	// */
	// public int getNearestIfCollide(WPS wps)
	// {
	// WPS vector = new WPS(1, 1);
	// WPS diff = new WPS();
	// int result = 0;
	// for (int i = 0; i < ents.length; i++)
	// {
	// diff.set(wps.getDifference(ents[i].getEnt()));
	// if (vector.isShorter(diff))
	// {
	// vector.set(diff);
	// result = i;
	// }
	// }
	// if (getEntityAtIndex(result).getEnt().isWPSinside(wps))
	// {
	// return result;
	// }
	// return -1;
	// }
	
	/** return index of nearest to this WPS entity */
	public int getNearest(WPS w, int[] indexBlackList)
	{
		WPS vector = new WPS(1, 1);
		int result = 0;
		for (int i = 0; i < ents.length; i++)
		{
			if (vector.isShorter(vector.getDifference(ents[i].getEnt())))
			{
				if (!contains(i, indexBlackList))
				{
					vector.set(vector.getDifference(ents[i].getEnt()));
					result = i;
				}
			}
		}
		return result;
	}
	
	/** return index of nearest to this WPS entity */
	public int getNearest(WPS w)
	{
		WPS vector = new WPS(1, 1);
		int result = 0;
		for (int i = 0; i < ents.length; i++)
		{
			if (vector.isShorter(vector.getDifference(ents[i].getEnt())))
			{
				vector.set(vector.getDifference(ents[i].getEnt()));
				result = i;
			}
		}
		return result;
	}
	
	private boolean contains(int i, int[] indexBlackList)
	{
		for (int ii = 0; ii < indexBlackList.length; ii++)
		{
			if (ii == i)
			{
				return true;
			}
		}
		return false;
	}
	
}
