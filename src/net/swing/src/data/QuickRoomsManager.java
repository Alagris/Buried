package net.swing.src.data;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import net.swing.src.env.Room;

public class QuickRoomsManager
{
	
	/**
	 * @param originSave
	 *            - contains path to folder (named the same as map/save) with all rooms of the origin save inside
	 * 
	 *            <pre>
	 * Origin saves structure:
	 * 
	 * >>(Parent folder of whole game)
	 * ---->>saves
	 * -------->>(name of save)
	 * ------------>>(room1_name)
	 * ---------------->>(room1_name).col
	 * ---------------->>(room1_name).obj
	 * ---------------->>(room1_name).props
	 * ---------------->>(room1_name).til
	 * ------------>>(room2_name)
	 * ---------------->>(room2_name).col
	 * ---------------->>(room2_name).obj
	 * ---------------->>(room2_name).props
	 * ---------------->>(room2_name).til
	 * ------------>>...
	 * </pre>
	 */
	public static boolean existsRoom(String roomName, File originSave)
	{
		if (roomName == null) return false;
		return new File(originSave.getPath() + "/" + roomName).exists();
	}
	
	/**
	 * Returns new instance of File with path to created room. WARNING: this
	 * room is empty which means that here are no files where data could be
	 * saved. Calling method write() (or saveRoom() ) will cause errors.
	 * 
	 * @param save
	 */
	public static File createEmptyRoom(String roomName, File save)
	{
		File f = new File(save.getPath() + "/" + roomName);
		if (!f.exists())
		{
			f.mkdir();
		}
		return f;
	}
	
	/**
	 * Creates room with 3 empty files.
	 * 
	 * @param save
	 */
	public static void createRoom(String roomName, File save) throws SecurityException
	{
		createEmptyRoom(roomName, save);
		try
		{
			getRoomFile(roomName, Format.COL, save).createNewFile();
			getRoomFile(roomName, Format.OBJ, save).createNewFile();
			getRoomFile(roomName, Format.TIL, save).createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * It will return false if something went wrong
	 * 
	 * @param save
	 */
	public static boolean deleteRoom(String roomName, File save)
	{
		File folder = getRoomFolder(roomName, save);
		if (!folder.exists()) return false;
		for (File f : folder.listFiles())
		{
			System.out.println("Deleting file: " + f.getPath());
			if (!f.delete())
			{
				return false;
			}
		}
		return folder.delete();
	}
	
	/**
	 * Checks if folder named like the room exists
	 * 
	 * @param save
	 */
	public static boolean existsRoom(Room room, File save)
	{
		return existsRoom(room.getName(), save);
	}
	
	/**
	 * Simple method used to filter files that don't exist.
	 * 
	 * @param f
	 *            - file being checked
	 * @return f if file exists. Null otherwise.
	 */
	public static File getIfExists(File f)
	{
		return f.exists() ? f : null;
	}
	
	/**
	 * Returns 4 (should return if exist) files in one File[] array in order: .COL, .OBJ , .TIL , .PROPS .
	 * If any of them doesn't exist it will return array with that element null
	 * 
	 * @param save
	 */
	public static File[] getAllDataFiles(String roomName, File save)
	{
		File[] files = new File[4];
		
		if (existsRoom(roomName, save))
		{
			files[0] = getIfExists(getRoomFile(roomName, Format.COL, save));
			files[0] = getIfExists(getRoomFile(roomName, Format.OBJ, save));
			files[0] = getIfExists(getRoomFile(roomName, Format.TIL, save));
			files[0] = getIfExists(getRoomFile(roomName, Format.PROPS, save));
		}
		return files;
	}
	
	/**
	 * Returns 3 (should return if exist) files in one File[] array in order: .COL, .OBJ , .TIL .
	 * If any of them doesn't exist it will return array with that element null
	 * 
	 * @param save
	 */
	public static File[] getBasicDataFiles(String roomName, File save)
	{
		File[] files = new File[4];
		
		if (existsRoom(roomName, save))
		{
			files[0] = getIfExists(getRoomFile(roomName, Format.COL, save));
			files[0] = getIfExists(getRoomFile(roomName, Format.OBJ, save));
			files[0] = getIfExists(getRoomFile(roomName, Format.TIL, save));
		}
		return files;
	}
	
	
	/**
	 * Returns specific file in folder of room
	 * 
	 * @param save
	 */
	public static File getRoomFile(String roomName, Format fm, File save)
	{
		return new File(save.getPath() + "/" + roomName + "/" + fm.format);
	}
	
	/**
	 * Returns folder with files with data.If such room doesn't exist it will
	 * return File object anyway.
	 * 
	 * @param save
	 */
	public static File getRoomFolder(String roomName, File save)
	{
		return new File(save.getPath() + "/" + roomName);
	}
	
	/**
	 * Returns names of all rooms in save
	 * 
	 * @param save
	 */
	public static String[] getRoomsNames(File save)
	{
		return save.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getPath() + "/" + name).isDirectory();
			}
		});
	}
	
	/**
	 * Returns true if in folder of this room are AT LEAST 3 files: COL, OBJ, TIL .
	 * 
	 * @param room
	 *            - gets name of this room and uses it to find files
	 * @param save
	 */
	public static boolean validate(Room room, File save)
	{
		return validate(room.getName(), save);
	}
	
	/**
	 * Returns true if in folder of room are AT LEAST 3 files: COL, OBJ, TIL .
	 * 
	 * @param save
	 */
	public static boolean validate(String roomName, File save)
	{
		return (getRoomFile(roomName, Format.COL, save).exists() 
				&& getRoomFile(roomName, Format.OBJ, save).exists()
				&& getRoomFile(roomName, Format.TIL, save).exists());
	}
	
	/**
	 * Returns true when saving finished successfully. 
	 * Notice that it edits only files .TIL , .COL an .OBJ .
	 * File .PROPS contains instructions that are not 
	 * edited by engine (engine only reads and executes them). 
	 * 
	 * @param save
	 */
	public static boolean saveRoom(Room room, File save)
	{
		if (!validate(room, save))
		{
			// if room doesn't exist it will create it
			createRoom(room.getName(), save);
		}
		try
		{
			DataCoder.write(getRoomFile(room.getName(), Format.TIL, save), room.getFloorData());
			DataCoder.write(getRoomFile(room.getName(), Format.COL, save), room.getCollData());
			DataCoder.write(getRoomFile(room.getName(), Format.OBJ, save), room.getLivings().packData());
		}
		catch (SecurityException e)
		{
			System.err.println("Saving error!");
			return false;
		}
		return true;
	}
	
	/** Creates room with 3 files filled with default data. */
	@SuppressWarnings("deprecation")
	public static void createDefaultRoom( String roomName,File save) throws SecurityException
	{
		createEmptyRoom(roomName, save);// creates empty room
		saveRoom(new RoomBuilder(roomName), save);// fills with default data
	}
	
}
