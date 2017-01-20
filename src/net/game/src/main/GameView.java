package net.game.src.main;

import java.io.File;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.swing.engine.graph.DisplayBlocksMatrix;
import net.swing.ground.Controls;
import net.swing.ground.GameViewInterface;
import net.swing.ground.Window;
import net.swing.src.data.DataCoder;
import net.swing.src.data.RoomsManager;
import net.swing.src.data.SavePlayabeManager;
import net.swing.src.data.RoomBuilder;
import net.swing.src.data.SavePlayable;
import net.swing.src.env.Room;
import net.swing.src.env.World;
import net.swing.src.env.WorldSettings;

/**
 * Main task of this class it to handle saving and loading
 * rooms and saves and put all loaded data in object World.
 * Object SavePlayable keeps track of currently used save and path to it
 */
public final class GameView implements GameViewInterface
{
	
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	/**
	 * This is the room - place where everything happens. WARNING: it's not
	 * initialized here but it waits for loadMap() method.
	 */
	public World						world	= new World();
	/** Object SavePlayable keeps track of currently used save and path to it */
	public SavePlayable					savePlayable;
	
	private final DisplayBlocksMatrix	blocksMatrix;
	public final SideInventory			inv_side;
	public final TopInventory			inv_top;
	
	// ////////////////////////////////////////////
	// / Constructors
	// ////////////////////////////////////////////
	
	public GameView()
	{
		blocksMatrix = new DisplayBlocksMatrix(WorldSettings.rows, WorldSettings.columns, WorldSettings.invSideWidth, 0);
		inv_side = new SideInventory(0, 0, WorldSettings.invSideWidth, Window.getHeight());
		inv_top = new TopInventory(WorldSettings.invSideWidth, WorldSettings.roomHeight, WorldSettings.roomWidth, Window.getHeight() - WorldSettings.roomHeight);
	}
	
	// ////////////////////////////////////////////
	// / Getters
	// ////////////////////////////////////////////
	
	@Override
	public Room getRoom()
	{
		return world.getRoom();
	}
	
	@Override
	public SavePlayable getSavePlayable()
	{
		return savePlayable;
	}
	
	@Override
	public World getWorld()
	{
		return world;
	}
	
	public boolean isMouseInRoom()
	{
		return isPointInRoom(Controls.mouseX, Controls.mouseY);
	}
	
	public boolean isPointInRoom(float mouseX, float mouseY)
	{
		return mouseX > WorldSettings.invSideWidth && mouseY < WorldSettings.roomHeight;
	}
	
	// ////////////////////////////////////////////
	// / data and saves managing - loading data
	// ////////////////////////////////////////////
	
	/**
	 * imports data from origin to currently used room. Files are not changed.
	 * Data is saved only in room (as a java object). To change data in files
	 * this room has to be saved (or you can simply importDataToFiles)
	 */
	public void importDataToThisRoom()
	{
		
		RoomsManager tempRoomsManager = new RoomsManager(savePlayable.getOriginSave());
		
		// checks if room exists
		if (tempRoomsManager.existsRoom(getWorld().getRoom().getName()))
		{
			RoomBuilder builder = new RoomBuilder(tempRoomsManager.getSave(), getWorld().getRoom().getName(), getWorld().getThings());
			world.setRoom(builder);
			builder.loadProperties();
			inv_side.resetInventory();
		}
	}
	
	/** imports data from origin to currently used room */
	public void refreshProps()
	{
		saveRoom();
		loadRoom(savePlayable.getLastRoom(), false);
	}
	
	/**
	 * creates folder for save files in folder playable. Creates there info.txt
	 * file and start room imported from origin
	 */
	public boolean createSave(String saveName, String referencedOrigin)
	{
		SavePlayable tempSavePlayable = new SavePlayable(saveName);
		if (tempSavePlayable.exists())
		{
			System.err.println("Attempted to create existing save! (action stopped)");
			return true;
		}
		else
		{
			// creates save folder and info.txt file
			tempSavePlayable.createSave(referencedOrigin);
			
			return tempSavePlayable.importDataToFiles(/* imports data from start room */
					SavePlayable.getStartRoom(referencedOrigin));
		}
	}
	
	public boolean createSaveAndLoad(String saveName, String referencedOrigin)
	{
		if (createSave(saveName, referencedOrigin))
		{
			loadSave(saveName);
			loadRoom(savePlayable.getLastRoom(), false);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Loads save and the finds the last used room and loads it.
	 * While loading new room:
	 * 
	 * <pre>
	 * <b>-0-</b> loads save then loads room: 
	 * <b>-1-</b> loads floor 
	 * <b>-2-</b> loads collision areas 
	 * <b>-3-</b> loads entities list 
	 * <b>-4-</b> finds properties file 
	 * <b>-5-</b> sets new room in world
	 * <b>-6-</b> sets room loads properties 
	 * <b>-7-</b> resets inventory
	 * </pre>
	 */
	@Override
	public void loadMap(String saveName, boolean saveLastRoom)
	{
		if (loadSave(saveName))
		{
			loadRoom(savePlayable.getLastRoom(), saveLastRoom);
		}
	}
	
	/**
	 * Only loads this save data. Doesn't load any room.
	 * 
	 * @return true if everything finished successfully
	 */
	@Override
	public boolean loadSave(String saveName)
	{
		savePlayable = new SavePlayable(saveName);
		if (savePlayable.exists())
		{
			File info = savePlayable.getOriginSave().getInfoFile();
			if (DataCoder.readLinesArray(info, ";SRm;").length == 1)
			{
				if (world.loadEntities(info))
				{
					if (world.loadThings(info))
					{
						return true;
					}
					else
					{
						LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Error while loading save. Things cannot be loaded. ");
						return false;
					}
				}
				else
				{
					LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Error while loading save. Entities cannot be loaded. ");
					return false;
				}
			}
			else
			{
				LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Error while loading save. Start room is not specified. ");
				return false;
			}
		}
		LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Error while loading save. Can't find file: " + savePlayable.getPath());
		return false;
	}
	
	/**
	 * Only loads specified room .Be careful and make
	 * sure that this room actually belongs to
	 * currently loaded save.
	 * While loading new room:
	 * 
	 * <pre>
	 * <b>-0-</b> loads save then loads room: 
	 * <b>-1-</b> loads floor 
	 * <b>-2-</b> loads collision areas 
	 * <b>-3-</b> loads entities list 
	 * <b>-4-</b> finds properties file 
	 * <b>-5-</b> sets new room in world
	 * <b>-6-</b> sets room loads properties 
	 * <b>-7-</b> resets inventory
	 * </pre>
	 */
	@Override
	public void loadRoom(String roomName, boolean saveLastRoom)
	{
		// checks if game should save recent room
		if (saveLastRoom)
		{// saves recently used room if there was any
			if (world.getRoom() != null)
			{
				saveRoom();
			}
		}
		RoomBuilder builder = null;
		// checks if room exists
		if (savePlayable.existsRoom(roomName))
		{
			// loads data from playable save
			builder = new RoomBuilder(savePlayable, roomName, getWorld().getThings());
		}
		else
		{
			System.out.println("room " + roomName + " doesn't exist. Attempting to load from origin");
			
			builder = new RoomBuilder(savePlayable.getOriginSave(), roomName, getWorld().getThings());
		}
		world.setRoom(builder);
		builder.loadProperties();
		inv_side.resetInventory();
	}
	
	@Override
	public void loadVocieDialog(String voiceName)
	{
		//TODO: add dialog displayer
	}
	
	// ////////////////////////////////////////////
	// / data and saves managing - saving data
	// ////////////////////////////////////////////
	
	public void saveLastRoomInfo()
	{
		savePlayable.setLastRoom(world.getRoom());
	}
	
	/**
	 * Notice that game never saves whole map at once. It'd rather save
	 * room every time player leaves it. The main benefit of this is that
	 * game is always saved and even if users computer abruptly turns off, the game
	 * will remain almost at the same stage (all rooms saved excluding the one that is currently being used)
	 * 
	 * @return true when saving finished successfully
	 */
	public boolean saveRoom()
	{
		SavePlayabeManager m = new SavePlayabeManager(savePlayable);
		return m.saveRoom(world.getRoom());
	}
	
	/**
	 * Notice that game never saves whole map at once. It'd rather save
	 * room every time player leaves it. The main benefit of this is that
	 * game is always saved and even if users computer abruptly turns off, the game
	 * will remain almost at the same stage (all rooms saved excluding the one that is currently being used).
	 * 
	 * If saving finish with errors the error state will be shown automatically
	 **/
	public void saveRoomOrShowError()
	{
		if (!saveRoom())
		{
			LunarEngine2DMainClass.getScreen().setErrorState("Error occured while saving! Files not accessed!");
		}
	}
	
	// ////////////////////////////////////////////
	// / graphic stuff like rendering and updating view
	// ////////////////////////////////////////////
	
	/** Updates the room */
	public void updateRoom()
	{
		// first - renders blocks
		world.getRoom().render(blocksMatrix);
		// then - renders and updates entities
		world.getRoom().updateEntities();
	}
	
	/** Just renders the room without updating . */
	public void renderRoom()
	{
		world.getRoom().render(blocksMatrix);
		world.getRoom().renderEntities();
	}
	
	public void updateInventory()
	{
		inv_side.update();
		inv_top.update();
	}
	
	public void renderInventory()
	{
		inv_side.render();
		inv_top.render();
	}
	
	/** renders room and inventory without updating */
	public void render()
	{
		renderInventory();
		renderRoom();
	}
	
	/**
	 * updates room and inventory. ATTENTION: in this method mouse is reset so
	 * Controls.isMousepressed/clicked will always return false every time you
	 * use those variables after calling this method
	 */
	public void update()
	{
		if (isMouseInRoom())
		{
			updateRoom();
			Controls.resetMouse();
			updateInventory();
		}
		else
		{
			updateInventory();
			Controls.resetMouse();
			updateRoom();
		}
	}
	
	// ////////////////////////////////////////////
	// / cleaning
	// ////////////////////////////////////////////
	
	@PreDestroy
	/** Removes matrix */
	public void cleanUp()
	{
		blocksMatrix.removeMatrix();
		inv_side.cleanUp();
		inv_top.cleanUp();
	}

}
