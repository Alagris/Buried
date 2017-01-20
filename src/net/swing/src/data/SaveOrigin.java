package net.swing.src.data;

import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class SaveOrigin extends File
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Save of room. each save is a folder of folders of rooms, each room folder
	 * contains 3 files named [name] with formats .til for file with blocks ,
	 * .col for file with solid areas and .obj for file with positions of
	 * objects that are independent to matrix
	 */
	/** Sets save to default: origin */
	public SaveOrigin()
	{
		super(Files.SAVES_FOLDER.getPath() + "/origin");
	}
	
	public SaveOrigin(String saveName)
	{
		super(Files.SAVES_FOLDER.getPath() + "/" + saveName);
	}
	
	/**
	 * Saves this save (only folder and info file). returns true if everything
	 * finished successfully
	 */
	public boolean createSave()
	{
		try
		{
			return this.mkdir() && getInfoFile().createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Removes save and all rooms inside. Return false if something went wrong
	 * (manual removing may be needed).
	 */
	public boolean deleteWholeSave()
	{
		if (!exists()) return true;
		getInfoFile().delete();
		RoomsManager m = new RoomsManager(this);
		for (String r : getRoomsNames())
		{
			if (!m.deleteRoom(r))
			{
				System.err.println("deleting failed on room " + r);
				return false;
			}
		}
		return delete();
	}
	
	/**
	 * Removes save , all rooms inside, and everything else that is inside save
	 * folder. Return false if something went wrong (manual removing may be
	 * needed).
	 */
	public boolean deleteWholeSaveHard()
	{
		return DataCoder.eraseFolder(this);
	}
	
	/** Returns names of all rooms in save */
	public String[] getRoomsNames()
	{
		return list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getPath() + "/" + name).isDirectory();
			}
		});
	}
	
	/**
	 * Opens system window showing this file. Use when manual removing is
	 * needed.
	 */
	public void debug()
	{
		try
		{
			Desktop.getDesktop().open(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns file "info.txt" for origin save. All origin saves (simply saves)
	 * are located in folder saves/ .Data in this info file tells game which
	 * block sets should it load.
	 */
	public File getInfoFile()
	{
		return new File(getPath() + "/info.txt");
	}
	
	/** Returns all folders with saves */
	public static File[] getSaves()
	{
		return Files.SAVES_FOLDER.f.listFiles();
	}
	
	/** Returns names of all folders with saves */
	public static String[] getSavesNames()
	{
		return Files.SAVES_FOLDER.f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getName() + "/" + name + "/info.txt").exists();
			}
		});
	}
	
	/**
	 * returns folder containing all room files. If that room doesn't exist it
	 * will return null
	 */
	public File getRoomFolder(String room)
	{
		File f = new File(getPath() + "/" + room);
		return f.exists() ? f : null;
	}
	
	/** Returns start room saved in origin's info.txt file */
	public String getStartRoom()
	{
		File info = getInfoFile();
		
		if (info.exists())
		{
			return DataCoder.readFirstLine_CutPrefix(info, ";SRm;");
		}
		else
		{
			return null;
		}
	}
	
	public String[] getBlockSets()
	{
		return DataCoder.readLinesArray(getInfoFile(), ";TSet;");
	}
	
}
