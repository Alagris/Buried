package net.swing.src.data;

import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.swing.src.env.Room;

public class SavePlayable extends File
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/** Returns all folders with saves */
	public static File[] getSaves()
	{
		return Files.PLAYABLE_SAVES_FOLDER.f.listFiles();
	}
	
	/** Returns names of all folders with saves */
	public static String[] getSavesNames()
	{
		return Files.PLAYABLE_SAVES_FOLDER.f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getName() + "/" + name + "/info.txt").exists();
			}
		});
	}
	
	/**
	 * Returns names of folders with saves but only those that are referenced to
	 * a particular origin
	 */
	public static String[] getSavesNames(final String originFilter)
	{
		return Files.PLAYABLE_SAVES_FOLDER.f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return originFilter.equals(new SavePlayable(name).getReferencedSave());
			}
		});
	}
	
	/**
	 * Returns folders with saves but only those that are referenced to
	 * a particular origin
	 */
	public static File[] getSaves(final String originFilter)
	{
		return Files.PLAYABLE_SAVES_FOLDER.f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return originFilter.equals(new SavePlayable(name).getReferencedSave());
			}
		});
	}
	
	/**
	 * <b>Does exactly the same as getSaves but returns array of SavePlayabale instead of File</b>>
	 * Returns an array of abstract pathnames denoting the files and
	 * directories in the directory denoted by this abstract pathname that
	 * satisfy the specified filter. The behavior of this method is the same
	 * as that of the {@link #listFiles()} method, except that the pathnames in
	 * the returned array must satisfy the filter. If the given {@code filter} is {@code null} then all pathnames are accepted. Otherwise, a pathname
	 * satisfies the filter if and only if the value {@code true} results when
	 * the {@link FilenameFilter#accept
	 * FilenameFilter.accept(File,&nbsp;String)} method of the filter is
	 * invoked on this abstract pathname and the name of a file or directory in
	 * the directory that it denotes.
	 *
	 * @param filter
	 *            A filename filter
	 *
	 * @return An array of abstract pathnames denoting the files and
	 *         directories in the directory denoted by this abstract pathname.
	 *         The array will be empty if the directory is empty. Returns {@code null} if this abstract pathname does not denote a
	 *         directory, or if an I/O error occurs.
	 *
	 * @throws SecurityException
	 *             If a security manager exists and its {@link SecurityManager#checkRead(String)} method denies read access to
	 *             the directory
	 *
	 */
	public static SavePlayable[] getSavePlayables(final String originFilter)
	{
		String ss[] = Files.PLAYABLE_SAVES_FOLDER.f.list();
		if (ss == null) return null;
		ArrayList<SavePlayable> files = new ArrayList<>();
		for (String s : ss)
		{
			File infoFile = getInfoFile(s);
			if (infoFile.isFile() && originFilter.equals(getReferencedSave(infoFile))) files.add(new SavePlayable(s));
		}
		return files.toArray(new SavePlayable[files.size()]);
	}
	
	private static String getReferencedSave(File infoFile)
	{
		return DataCoder.read(infoFile, 1);
		
	}
	
	/**
	 * Returns file "info.txt" for playable save. This info file contains data
	 * of what the start room is and what what the name of origin file is
	 */
	public static File getInfoFile(String savePlayableName)
	{
		return new File(Files.PLAYABLE_SAVES_FOLDER.getPath() + "/" + savePlayableName + "/info.txt");
	}
	
	
	/**
	 * Save of room. each save is a folder of folders of rooms, each room folder
	 * contains 3 files named [name] with formats .til for file with blocks ,
	 * .col for file with solid areas and .obj for file with positions of
	 * objects that are independent to matrix
	 */
	public SavePlayable(String saveName)
	{
		super(Files.PLAYABLE_SAVES_FOLDER.f.getPath() + "/" + saveName);
	}
	
	/**
	 * Saves this save (creates folder and info.txt). returns true if everything
	 * finished successfully
	 */
	public boolean createSave(String originName)
	{
		if (this.mkdir())
		{
			try
			{
				File info = getInfoFile();
				
				if (info.createNewFile())
				{
					DataCoder.write(info,
					/* first goes info about referenced origin */
					originName + "\n" + /*
										 * second goes info about last used room
										 * (in this case it's start room)
										 */
					getStartRoom(originName));
					return true;
				}
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return false;
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
	 * Removes save and all rooms inside. Return false if something went wrong
	 * (manual removing may be needed).
	 */
	public boolean deleteWholeSave()
	{
		if (!exists()) return true;
		SavePlayabeManager m = new SavePlayabeManager(this);
		for (String r : m.getRoomsNames())
		{
			System.out.println("deleting room: " + r);
			if (!m.deleteRoom(r))
			{
				return false;
			}
		}
		if (getInfoFile().delete())
		{
			if (this.delete())
			{
				return true;
			}
		}
		else
		{
			System.err.println("Info.txt cannot be removed!");
		}
		
		return false;
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
	
	/**
	 * Check if room exists in this save (in folder playable )
	 * 
	 * @param roomName
	 *            - name of the room
	 */
	public boolean existsRoom(String roomName)
	{
		if (roomName == null) return false;
		return new File(getPath() + "/" + roomName).exists();
	}
	
	/**
	 * Reads from file info.txt the name of last used room (the player should be
	 * there)
	 */
	public String getLastRoom()
	{
		return DataCoder.read(getInfoFile(), 2);
	}
	
	/**
	 * Returns new Save object with referenced origin save (data is taken from
	 * info.txt)
	 */
	public SaveOrigin getOriginSave()
	{
		return new SaveOrigin(getReferencedSave());
	}
	
	/**
	 * Returns file "info.txt" for playable save. This info file contains data
	 * of what the start room is and what what the name of origin file is
	 */
	public File getInfoFile()
	{
		return new File(getPath() + "/info.txt");
	}
	
	/** Returns world name saved in info.txt file **/
	public String getReferencedSave()
	{
		return getReferencedSave(getInfoFile());
	}
	
	/**
	 * Returns start room of any existing origin save. If save doesn't exist it
	 * will return null. Result of this method is the same as creating new Save
	 * and reading info from its info file
	 */
	public static String getStartRoom(String originName)
	{
		SaveOrigin saveOrigin = new SaveOrigin(originName);
		
		return saveOrigin.getStartRoom();
	}
	
	/**
	 * Returns start room of referenced by this save origin. If origin save
	 * doesn't exist (what shouldn't happen) it will return null. Result of this
	 * method is the same as creating new Save and reading info from its info
	 * file
	 */
	public String getStartRoom()
	{
		return getOriginSave().getStartRoom();
	}
	
	/**
	 * imports data from <b>referenced</b> origin to files of room . Returns
	 * true if everything finished successfully. If files don't exist it will
	 * create them.
	 * 
	 * @param room
	 */
	public boolean importDataToFiles(String room)
	{
		return importDataToFiles(getReferencedSave(), room);
	}
	
	/**
	 * imports data from origin to files of room . Returns true if everything
	 * finished successfully. If files don't exist it will create them.
	 */
	public boolean importDataToFiles(SaveOrigin origin, String roomName)
	{
		// creating managers
		SavePlayabeManager managerPlayable = new SavePlayabeManager(this);
		RoomsManager managerOrigin = new RoomsManager(origin);
		
		// checking if room folder exists
		managerPlayable.createEmptyRoom(roomName);
		
		try
		// importing data
		{
			Format fm = Format.COL;
			DataCoder.copyDataFromTo(managerOrigin.getRoomFile(roomName, fm), managerPlayable.getRoomFile(roomName, fm));
			fm = Format.TIL;
			DataCoder.copyDataFromTo(managerOrigin.getRoomFile(roomName, fm), managerPlayable.getRoomFile(roomName, fm));
			fm = Format.OBJ;
			DataCoder.copyDataFromTo(managerOrigin.getRoomFile(roomName, fm), managerPlayable.getRoomFile(roomName, fm));
			return true;
		}
		catch (IOException | NoSuchElementException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * imports data from origin to files of currently used room . Returns true
	 * if everything finished successfully. If files don't exist it will create
	 * them. It doesn't create save folder nor info.txt file
	 * 
	 * @param room
	 */
	public boolean importDataToFiles(String origin, String room)
	{
		return importDataToFiles(new SaveOrigin(origin), room);
	}
	
	/**
	 * Creates all folders needed to save this save. Returns true if everything
	 * finished successfully
	 */
	public boolean repairFolders()
	{
		return this.mkdirs();
	}
	
	/**
	 * Saves info about last used room in playable save so that this room will
	 * be loaded in case user left game and after some time decided to play
	 * again.
	 */
	public void setLastRoom(Room room)
	{
		DataCoder.write(getInfoFile(), getReferencedSave() + "\n" + room.getName());
	}
	
}
