package net.swing.ground;

import net.swing.src.data.SavePlayable;
import net.swing.src.env.Room;
import net.swing.src.env.World;

public interface GameViewInterface
{
	
	
	
	/** loadSave() must be called earlier */
	public void loadRoom(String roomName, boolean saveLastRoom);
	
	/**
	 * Don't load new saves while game is in action. Exit to lobby at first.
	 * 
	 * @return
	 */
	public boolean loadSave(String saveName);
	
	/** World with current room and set of blocks */
	public World getWorld();
	
	public Room getRoom();

	public SavePlayable getSavePlayable();

	public void loadMap(String saveName, boolean savePreviousRoom);

	public void loadVocieDialog(String voiceName);
	 
}
