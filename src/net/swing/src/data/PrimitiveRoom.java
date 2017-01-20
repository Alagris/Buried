package net.swing.src.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import net.editor.room.PrimitiveEntity;
import net.swing.engine.WPS;
import net.swing.src.env.Matrix;
import net.swing.src.env.WorldSettings;

/** This class is used only by */
public class PrimitiveRoom
{
	public ImageIcon[]			blockImages;
	public String[]				blockNames;
	public String[]				blockSets;
	public Matrix				blockMatrix;
	public Matrix				areaMatrix;
	/** This variable is used everywhere in RoomsEditor */
	public static SaveOrigin			currentSave;
	public RoomsManager			manager;
	public boolean				wereSetsChanged	= false;
	public PrimitiveEntity[]	entiesList;
	/** This variable is used everywhere in RoomsEditor */
	public static String		currentRoom;
	
	/** Returns true when errors occur */
	public boolean load(SaveOrigin saveOrigin, String room)
	{
		
		/*
		 * If new save is being loaded editor has to check whether block sets
		 * are the same
		 */
		if (saveOrigin != currentSave)
		{
			if (checkSave(saveOrigin))
			{
				return true;
			}
		}
		
		currentRoom = room;
		
		manager = new RoomsManager(saveOrigin);
		
		if (manager.getRoomFolder(currentRoom).exists())
		{
			loadRoomFloor(currentRoom);
			loadRoomAreas(currentRoom);
			loadRoomEntites(currentRoom);
		}
		
		return false;
	}
	
	private void loadRoomEntites(String room)
	{
		File f = manager.getRoomFile(room, Format.OBJ);
		DataCoder.removeEmptyLines(f, 1);
		entiesList = collectEntites(DataCoder.readLinesArray(f));
	}
	
	private PrimitiveEntity[] collectEntites(String[] lines)
	{
		PrimitiveEntity[] ents = new PrimitiveEntity[lines.length];
		int i = 0;
		for (String line : lines)
		{// Syntax => [name]:[WPS]:[program]
			String[] subStrings = line.split(":");
			ents[i] = new PrimitiveEntity();
			ents[i].setName(subStrings[0]);
			ents[i].setLocation(WPS.parseCoordinates(subStrings[1]));
			ents[i].setGroovyClass(new File(Files.ENTITIES_FOLDER + "/" + ents[i].getName() + "/Main.groovy"));
			i++;
		}
		return ents;
	}
	
	private void loadRoomAreas(String room)
	{
		areaMatrix = new Matrix(WorldSettings.cRows, WorldSettings.cColumns, DataCoder.parseData(DataCoder.readAll(manager.getRoomFile(room, Format.COL)), WorldSettings.collAreaSize(), WorldSettings.collAreasQuantity));
	}
	
	private void loadRoomFloor(String room)
	{
		blockMatrix = new Matrix(WorldSettings.rows, WorldSettings.columns, DataCoder.parseData(DataCoder.readAll(manager.getRoomFile(room, Format.TIL)), WorldSettings.floorSize(), blockImages.length + 1));
	}
	
	private boolean checkSave(SaveOrigin saveOrigin)
	{
		
		String[] newBlockSets = saveOrigin.getBlockSets();
		
		if (newBlockSets.length == 0) return true;
		
		wereSetsChanged = !Arrays.equals(newBlockSets, blockSets);
		if (wereSetsChanged)
		{
			try
			{
				checkSets(newBlockSets);
			}
			catch (IOException e)
			{
				return true;
			}
		}
		
		/* Finally new save is set */
		currentSave = saveOrigin;
		
		return false;
	}
	
	private void checkSets(String[] newBlockSets) throws IOException
	{
		// loading images of new blocks
		blockImages = DataCoder.getIconsOfBlocks(newBlockSets);
		
		// loading names of new blocks
		loadNewBlockNames(newBlockSets);
		
		// Finally new array of block sets is set
		setBlockSets(newBlockSets);
	}
	
	private void loadNewBlockNames(String[] newBlockSets)
	{
		ArrayList<String> names = new ArrayList<String>();
		
		for (String texturesSource : newBlockSets)
		{
			names.addAll(DataCoder.readLinesArrayList(new File(Files.BLOCK_TEX_FOLDER.f + "/" + texturesSource + ".txt")));
		}
		blockNames = names.toArray(new String[0]);
	}
	
	private void setBlockSets(String[] newBlockSets)
	{
		blockSets = newBlockSets;
	}
	
	public boolean isNotLoaded()
	{
		return blockMatrix == null;
	}
	
	public void saveAreaMatrix()
	{
		if (manager == null) return;
		DataCoder.write(manager.getRoomFile(currentRoom, Format.COL), DataCoder.codeData(areaMatrix.getBlocksArray(), 'C'));
	}
	
	public void saveBlockMatrix()
	{
		if (manager == null) return;
		DataCoder.write(manager.getRoomFile(currentRoom, Format.TIL), DataCoder.codeData(blockMatrix.getBlocksArray(), 'R'));
	}
	
	public SaveOrigin getCurrentSave()
	{
		return manager.getSave();
	}
	
	public String getCurrentRoom()
	{
		return currentRoom;
	}
	
	public void setCurrentRoom(String currentRoom)
	{
		PrimitiveRoom.currentRoom = currentRoom;
	}
	
	public void saveEntities()
	{
		if (manager == null) return;
		DataCoder.write(manager.getRoomFile(currentRoom, Format.OBJ), packEntitiesData());
	}
	
	private String packEntitiesData()
	{
		String s = "";
		for (PrimitiveEntity primitiveEntity : entiesList)
		{// Syntax => [name]:[WPS]:[program]
			s += getEntityData(primitiveEntity) + "\n";
		}
		s = s.substring(0, s.length() - 2);// this cuts the last \n
		return s;
	}
	
	private String getEntityData(PrimitiveEntity e)
	{
		return e.getName() + ":" + e.getLocation().toCommand();
	}
	
}
