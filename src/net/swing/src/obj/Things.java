package net.swing.src.obj;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import net.swing.src.data.Files;

public final class Things
{
	
	public static final Block	air	= new Block(0, Domain.NONE, "air", null);
	
	/** contains all loaded blocks from all sets */
	private final Thing[]		things;
	/** contains array of names of all loaded sets */
	private final String[]		blockSets;
	
	/**
	 * WARNING screen.initializeStates() is required here
	 * 
	 * @param sets
	 *            - names of sets with all textures of blocks
	 */
	public Things(String[] blockSets)
	{
		/**
		 * collectedBlocks variable is temporary. All data from here will be
		 * saved in array 'things'
		 */
		ArrayList<Thing> collectedBlocks = new ArrayList<Thing>();
		collectedBlocks.add(air);// air is default
		
		this.blockSets = blockSets;
		/** the last used ID to create a block. ID 0 is for air */
		int id = 1;
		for (String s : blockSets)
		{
			TexturesOfBlocks textBlocks = new TexturesOfBlocks(s);
			
			for (int i = 0; i < textBlocks.getSize(); i++)
			{
				collectedBlocks.add(textBlocks.buildBlock(i, id));
				id++;
			}
			// textBlocks.cleanUp();
			// Attention! Cleaning textures in textBlocks
			// also kills those textures in other variables
			// ( in this case its collectedBlocks)
		}
		// collects loaded and created blocks in final array
		things = collectedBlocks.toArray(new Thing[0]);
		for (int i = 0; i < things.length; i++)
		{
			// displays info of all loaded blocks
			System.out.println("Loaded " + things[i].toString() + " at index" + i);
		}
	}
	
	/** IDs between: 0-1 are block, ... are Items */
	public Thing getThingByID(int ID)
	{
		return things[ID];
	}
	
	public Thing[] getEverything()
	{
		return things;
	}
	
	public Block getBlockByID(int i)
	{
		return (Block) getThingByID(i);
	}
	
	/** quantity of loaded blocks */
	public int size()
	{
		return things.length;
	}
	
	public Thing getBlockByName(String n)
	{
		for (Thing t : things)
		{
			if (t.name.equals(n))
			{
				return t;
			}
		}
		return null;
	}
	
	public String[] getBlockSets()
	{
		return blockSets;
	}
	
	/** kills all textures loaded here */
	public void cleanUp()
	{
		for (int i = 1/* index 0 is for air */; i < things.length; i++)
		{
			things[i].tex.release();
		}
	}
	
	/**
	 * Returns true if this set of things is equal to blockSets2. It doesn't
	 * check all the blocks! It just checks sources their.
	 */
	public boolean haveEqualSets(String[] blockSets2)
	{
		if (blockSets2.length != blockSets.length)
		{
			// No need to check anything else
			return false;
		}
		for (String here : blockSets)
		{
			if (!contains(blockSets2, here))
			{
				return false;
			}
		}
		return true;
		
		// TODO: replace code above with code below
		// return Arrays.equals(blockSets2,blockSets);
		// TODO: and remove contains(String[] set, String value)
	}
	
	private boolean contains(String[] set, String value)
	{
		for (String s : set)
		{
			if (s.equals(value))
			{
				return true;
			}
		}
		return false;
	}
	
	public static String[] getAllBlockSets()
	{
		String[] g = Files.BLOCK_TEX_FOLDER.f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				if (name.endsWith(".png"))
				{
					return new File(dir.getPath() + "/" + name.split("\\.")[0] + ".txt").exists();
				}
				return false;
			}
		});
		for (int i = 0; i < g.length; i++)
		{
			g[i] = g[i].replaceAll(".png", "");
		}
		return g;
	}
}
