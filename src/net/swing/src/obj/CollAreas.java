package net.swing.src.obj;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import net.LunarEngine2DMainClass;
import net.swing.src.data.RoomProperties;
import net.swing.src.ent.Action;
import net.swing.src.env.WorldSettings;

public final class CollAreas
{
	
	private static boolean	wasInitialized	= false;
	
	private CollAreas()
	{
	}
	
	/** ID of block that is solid */
	public static final int					solidBlock	= 1;
	
	public static final CollArea			areaAir		= new CollAreaAir(null);
	
	public static final CollArea			areaSolid	= new CollAreaSolid(null);
	
	public static final StackOfCollAreas	stackAir	= new StackOfCollAreas(areaAir);
	// TODO:remove solid area (game will only use indexes but not CollArea
	// objects)
	public static final StackOfCollAreas	stackSolid	= new StackOfCollAreas(areaSolid);
	/***/
	private static final StackOfCollAreas	areas[]		= new StackOfCollAreas[WorldSettings.collAreasQuantity - 2];
	
	public static StackOfCollAreas getByID(int id)
	{
		if (id < areas.length + 2)
		{
			if (id > -1)
			{
				switch (id)
				{
					case 0:
						return stackAir;
					case 1:
						return stackSolid;
					default:
						return areas[id - 2];
				}
			}
		}
		return null;
	}
	
	public static void setOnID(int id, CollArea newInstance)
	{
		if (id < areas.length + 2)
		{
			if (id > 1)
			{
				areas[id - 2].add(newInstance);
			}
		}
	}
	
	/**
	 * Returns new instance of CollArea. That instance is automatically filled
	 * with instruction.
	 */
	public static CollArea getNewInstance(String instructionAndAction)
	{
		if (instructionAndAction.contains(":"))
		{
			String[] s = instructionAndAction.split(":");
			return getNewInstance(Action.getActionByShort(s[1]), s[2]);
		}
		return areaAir;
	}
	
	/**
	 * Returns new instance of CollArea. That instance is automatically filled
	 * with instruction.
	 */
	public static CollArea getNewInstance(Action a, String instruction)
	{
		try
		{
			return a.getType().getConstructor(String.class).newInstance(instruction);
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		return areaAir;
	}
	
	public static void setOnID(int id, Action a, String instruction)
	{
		setOnID(id, getNewInstance(a, instruction));
	}
	
	public static void clearAreas()
	{
		if (wasInitialized)
		{
			for (StackOfCollAreas a : areas)
			{
				a.clear();
			}
		}
		else
		{
			wasInitialized = true;
			for (int i = 0; i < areas.length; i++)
			{
				areas[i] = new StackOfCollAreas();
			}
		}
	}
	
	public static StackOfCollAreas[] getAreas()
	{
		return areas;
	}
	
	// Data syntax example:
	// 1:T:start 0,0,0
	/** Returns comment that says whether anything went wrong */
	public static String loadProps(RoomProperties file)
	{
		int line = 0, id = 0;
		String[] s;
		String lineString;
		try
		{
			Scanner sc = new Scanner(file);
			clearAreas();
			while (sc.hasNextLine())
			{
				// Reading data
				lineString = sc.nextLine();
				if (lineString.length() == 0)
				{// checks if line is not empty
					continue;// notice that value of variable:line doesn't
								// increase
				}
				id = parseID(lineString);
				// determining whether its declaration or action instructions
				if (id < 50)
				{
					// setting instructions
					s = lineString.split(":");
					setOnID(id, Action.getActionByShort(s[1]), s[2]);
					
				}
				else
				{
					lineString = lineString.replaceFirst(id + "&", "");
					LunarEngine2DMainClass.declare(id, lineString);
				}
				line++;
			}
			sc.close();
			return "Loading properties finished successfully. Lines found: " + line;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return "Something went wrong: file error";
	}
	
	/**
	 * quantity of possible areas is a two-digit number (when I write it it's
	 * 15)
	 */
	private static int parseID(String s)
	{
		/*
		 * takes first two chars (they should be digits) merges them and parses
		 * integer If there are any parsing errors it means that someone had
		 * coded instructions incorrectly
		 */
		if (Character.isDigit(s.charAt(1)))
		{
			return Character.getNumericValue(s.charAt(0)) * 10 + Character.getNumericValue(s.charAt(1));
		}
		else
		{
			return Character.getNumericValue(s.charAt(0));
		}
	}
	
}
