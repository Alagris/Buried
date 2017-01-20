package net.swing.src.data;

import java.io.File;
import java.io.IOException;

public final class RoomProperties extends File
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/** File must end with .props */
	public RoomProperties(File propsFile)
	{
		super(propsFile.getPath());
		if (!getName().equals(Format.PROPS))
		{
			throw new IllegalArgumentException();
		}
	}
	
	/** Use this when you are not sure if file exists. */
	public RoomProperties(SaveOrigin saveOrigin, String roomName)
	{
		super(find(saveOrigin, roomName).getPath());
	}
	
	private static File find(SaveOrigin saveOrigin, String roomName)
	{
		
		try
		{
			File f = QuickRoomsManager.getRoomFile(roomName, Format.PROPS, saveOrigin);
			f.createNewFile();
			return f;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** reads data from properties */
	public String getData()
	{
		return DataCoder.read(this);
	}
	
	/** Will return true if exists . Otherwise it will create new file. */
	public boolean checkExistance()
	{
		if (!exists())
		{
			System.out.println(getPath() + " Doesn't exists!");
			try
			{
				createNewFile();
			}
			catch (IOException e)
			{
			}
			return false;
		}
		return true;
	}
	
	/** Saves data to properties */
	public void saveProps(String data)
	{
		DataCoder.write(this, data);
	}
	
}
