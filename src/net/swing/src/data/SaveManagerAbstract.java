package net.swing.src.data;

import java.io.File;

import net.swing.src.env.Room;

/** non-static and abstract version of QuickRoomsManager */
public abstract class SaveManagerAbstract
{
	
	private final File	save;
	
	/** non-static and abstract version of QuickRoomsManager */
	public SaveManagerAbstract(File save)
	{
		this.save = save;
	}
	
	/**
	 * Returns new instance of File with path to created room. WARNING: this
	 * room is empty which means that here are no files where data could be
	 * saved. Calling method write() (or saveRoom() ) will cause errors.
	 */
	public File createEmptyRoom(String roomName)
	{
		return QuickRoomsManager.createEmptyRoom(roomName, save);
	}
	
	/** Creates room with 3 empty files. */
	public void createRoom(String roomName) throws SecurityException
	{
		QuickRoomsManager.createRoom(roomName, save);
	}
	
	/** It will return false if something went wrong */
	public boolean deleteRoom(String roomName)
	{
		return QuickRoomsManager.deleteRoom(roomName, save);
	}
	
	/** Checks if folder named like the room exists */
	public boolean existsRoom(Room room)
	{
		return existsRoom(room.getName());
	}
	
	/**
	 * Check if room (its folder) exists in this save
	 * 
	 * @param roomName
	 *            - name of the room
	 */
	public boolean existsRoom(String roomName)
	{
		return QuickRoomsManager.existsRoom(roomName, save);
	}
	
	/**
	 * Returns 4 (should return if exist) files in one File[] array in order: .COL, .OBJ , .TIL , .PROPS .
	 * If any of them doesn't exist it will return array with that element null
	 * 
	 * @param saveOrigin
	 */
	public File[] getAllDataFiles(String roomName)
	{
		return QuickRoomsManager.getAllDataFiles(roomName, save);
	}
	
	/**
	 * Returns 3 (should return if exist) files in one File[] array in order: .COL, .OBJ , .TIL .
	 * If any of them doesn't exist it will return array with that element null
	 * 
	 * @param saveOrigin
	 */
	public File[] getBasicDataFiles(String roomName)
	{
		return QuickRoomsManager.getBasicDataFiles(roomName, save);
	}
	
	/** Returns specific file in folder of room */
	public File getRoomFile(String roomName, Format fm)
	{
		return QuickRoomsManager.getRoomFile(roomName, fm, save);
	}
	
	/**
	 * Returns folder with files with data.If such room doesn't exist it will
	 * return File object anyway.
	 */
	public File getRoomFolder(String roomName)
	{
		return QuickRoomsManager.getRoomFolder(roomName,save);
	}
	
	/** Returns all rooms in save folder */
	public File[] getRooms()
	{
		return save.listFiles();
	}
	
	/** Returns names of all rooms in save */
	public String[] getRoomsNames()
	{
		return QuickRoomsManager.getRoomsNames(save);
	}
	
	/**
	 * Returns true if in folder of this room are 3 files: COL, OBJ, TIL .
	 * 
	 * @param room
	 *            - gets name of this room and uses it to find files
	 */
	public boolean validate(Room room)
	{
		return validate(room.getName());
	}
	
	/**
	 * Returns true if in folder of room are 3 files: COL, OBJ, TIL .
	 */
	public boolean validate(String roomName)
	{
		return QuickRoomsManager.validate(roomName, save);
	}
	
	/** Returns true when saving finished successfully */
	public boolean saveRoom(Room room)
	{
		return QuickRoomsManager.saveRoom(room, save);
	}
	
	/** Creates room with 3 files filled with default data. */
	protected void createDefaultRoom( String roomName) throws SecurityException
	{
		QuickRoomsManager.createDefaultRoom(roomName, save);
	}
	
}
