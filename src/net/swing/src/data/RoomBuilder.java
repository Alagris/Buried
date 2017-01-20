package net.swing.src.data;

import net.swing.src.ent.BindingList;
import net.swing.src.env.Room;
import net.swing.src.env.RoomCollision;
import net.swing.src.env.RoomFloor;
import net.swing.src.env.WorldSettings;
import net.swing.src.obj.CollAreas;
import net.swing.src.obj.Things;

public final class RoomBuilder extends Room
{
	
	/** Name of room. It is used to find data in saved files */
	private String			name;
	private RoomFloor		floor;
	private RoomCollision	coll;
	private RoomProperties	props;
	private BindingList		list;
	
	/**
	 * Builds room using data from origin save. If such room exists it will read
	 * data from files automatically. Otherwise it builds room filled with
	 * default values
	 */
	public RoomBuilder(SaveOrigin saveOrigin, String roomName, Things blockSet)
	{
		this.name = roomName;
		System.out.println("loading save: " + saveOrigin + " ,room: " + roomName);
		RoomsManager manager = new RoomsManager(saveOrigin);
		if (manager.existsRoom(roomName))
		{
			floor = new RoomFloor(DataCoder.parseData(DataCoder.read(manager.getRoomFile(roomName, Format.TIL)), WorldSettings.floorSize(), blockSet.size()));
			coll = new RoomCollision(DataCoder.parseData(DataCoder.read(manager.getRoomFile(roomName, Format.COL)), WorldSettings.collAreaSize()));
			list = new BindingList(manager.getRoomFile(roomName, Format.OBJ));
		}
		else
		{
			System.err.println("Save was not found!\n Creating empty room.");
			floor = new RoomFloor();
			coll = new RoomCollision();
			list = new BindingList();
		}
		props = new RoomProperties(saveOrigin, roomName);
		
	}
	
	/**
	 * Builds room filled with default data. Everything is air. Created data is
	 * very universal and can be used either by playable or origin room.
	 * Properties are read from origin save.
	 * 
	 * @param saveOrigin
	 *            - origin save
	 */
	public RoomBuilder(SaveOrigin saveOrigin, String roomName)
	{
		this.name = roomName;
		floor = new RoomFloor(Things.air);
		coll = new RoomCollision();
		list = new BindingList();
		props = new RoomProperties(saveOrigin, roomName);
		
	}
	
	
	/**
	 * Builds room filled with default data. Everything is air. Created data is
	 * very universal and can be used either by playable or origin room.
	 * <pre>
	 * <b>WARNING: Properties stay null and reading them will cause error!
	 * Use this constructor carefully.</b>
	 * </pre>
	 */
	@Deprecated
	public RoomBuilder(String roomName)
	{
		this.name = roomName;
		floor = new RoomFloor(Things.air);
		coll = new RoomCollision();
		list = new BindingList();
		props = null;
		
	}
	
	/**
	 * Builds room using data from playable save. If such room exists it will
	 * read data from files automatically. Otherwise it returns room filled with
	 * default values - everywhere is air!
	 */
	public RoomBuilder(SavePlayable save, String roomName, Things blockSet)
	{
		this.name = roomName;
		SavePlayabeManager manager = new SavePlayabeManager(save);
		if (manager.existsRoom(roomName))
		{
			floor = new RoomFloor(DataCoder.parseData(DataCoder.read(manager.getRoomFile(roomName, Format.TIL)), WorldSettings.floorSize(), blockSet.size()));
			coll = new RoomCollision(DataCoder.parseData(DataCoder.read(manager.getRoomFile(roomName, Format.COL)), WorldSettings.collAreaSize()));
			list = new BindingList(manager.getRoomFile(roomName, Format.OBJ));
		}
		else
		{
			floor = new RoomFloor();
			coll = new RoomCollision();
			list = new BindingList();
		}
		props = new RoomProperties(save.getOriginSave(), roomName);
	}
	
	/**
	 * Loads data from props file into CollAreas an displays the results.
	 */
	public void loadProperties()
	{
		if (props != null && props.checkExistance())
		{
			System.out.println(CollAreas.loadProps(props));
		}
		else
		{
			System.err.println("ERROR! Reading properties in room " + getName() + " failed!");
		}
	}
	
	@Override
	public RoomFloor getFloor()
	{
		return floor;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public RoomCollision getCollArea()
	{
		return coll;
	}
	
	@Override
	public RoomProperties getProps()
	{
		return props;
	}
	
	@Override
	public BindingList getLivings()
	{
		return list;
	}
}
